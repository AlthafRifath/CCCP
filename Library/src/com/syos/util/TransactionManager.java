package com.syos.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

public class TransactionManager {

    /**
     * Execute a task within a Hibernate transaction.
     *
     * @param task A function that performs database operations and returns a result.
     * @param <T>  The return type of the task.
     * @return The result of the task.
     */
    public static <T> T execute(Function<Session, T> task) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T result = task.apply(session);
            transaction.commit();
            return result;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed", ex);
        }
    }
}
