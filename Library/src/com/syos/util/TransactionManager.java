package com.syos.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionManager {
    private static final SessionPool sessionPool = new SessionPool(HibernateUtil.getSessionFactory());

    /**
     * Execute a task within a Hibernate transaction.
     *
     * @param action A function that performs database operations and returns a result.
     * @param <T>  The return type of the task.
     * @return The result of the task.
     */
    public static <T> T execute(ITransactionManager<T> action) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionPool.borrowSession();
            transaction = session.beginTransaction();
            T result = action.perform(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.clear(); // Clear state before returning
                sessionPool.returnSession(session);
            }
        }
    }
}
