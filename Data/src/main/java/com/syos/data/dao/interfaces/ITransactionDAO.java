package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Transaction;

public interface ITransactionDAO {
    void save(Transaction transaction);
}
