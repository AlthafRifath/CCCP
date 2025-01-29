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

    @Override
    public Transaction getTransactionByBillID(int billID) {
        return TransactionManager.execute(session -> session.createQuery(
                        "FROM Transaction WHERE billID = :billID", Transaction.class)
                .setParameter("billID", billID)
                .uniqueResult());
    }
}
