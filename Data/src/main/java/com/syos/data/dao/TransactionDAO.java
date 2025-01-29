package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.ITransactionDAO;
import main.java.com.syos.data.model.Transaction;

public class TransactionDAO implements ITransactionDAO {
    @Override
    public void save(Transaction transaction) {
        TransactionManager.execute(session -> {
            session.save(transaction);
            return null;
        });
    }
}
