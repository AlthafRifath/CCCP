package com.syos.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SessionPool {

    private final BlockingQueue<Session> pool;
    private final SessionFactory sessionFactory;
    private final int MAX_POOL_SIZE = 10;

    public SessionPool(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.pool = new ArrayBlockingQueue<>(MAX_POOL_SIZE);

        // Pre-create sessions and add to the pool
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            pool.offer(sessionFactory.openSession());
        }
    }

    public Session borrowSession() throws InterruptedException {
        return pool.take();
    }

    public void returnSession(Session session) {
        if (session != null && session.isOpen()) {
            pool.offer(session);
        }
    }

    public void shutdown() {
        for (Session session : pool) {
            session.close();
        }
    }

}
