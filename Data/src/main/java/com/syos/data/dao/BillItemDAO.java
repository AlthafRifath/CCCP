package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.model.BillItem;

import java.util.List;

public class BillItemDAO implements IBillItemDAO {
    @Override
    public void save(BillItem billItem) {
        TransactionManager.execute(session -> {
            session.save(billItem);
            return null;
        });
    }

    @Override
    public List<BillItem> findByBillID(int billID) {
        return TransactionManager.execute(session -> session.createQuery(
                        "FROM BillItem WHERE bill.billID = :billID AND isDeleted = false", BillItem.class)
                .setParameter("billID", billID)
                .list());
    }
}
