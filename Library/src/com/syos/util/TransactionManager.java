package com.syos.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TransactionManager {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    /**
     * Execute a task within a Hibernate transaction.
     *
     * @param action A function that performs database operations and returns a result.
     * @param <T>  The return type of the task.
     * @return The result of the task.
     */
    public static <T> T execute(TransactionAction<T> action) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T result = action.perform(session); // Execute the action
            transaction.commit(); // Commit transaction
            return result;
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // Rollback if the transaction is active
            }
            throw new RuntimeException("Transaction failed: " + ex.getMessage(), ex);
        }
    }

    @FunctionalInterface
    public interface TransactionAction<T> {
        T perform(Session session);
    }
}
