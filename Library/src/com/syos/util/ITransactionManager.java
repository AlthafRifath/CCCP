package com.syos.util;

import org.hibernate.Session;

@FunctionalInterface
public interface ITransactionManager<T> {
    T perform(Session session);
}
