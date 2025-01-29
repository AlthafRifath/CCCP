package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IBillDAO;
import main.java.com.syos.data.model.Bill;

public class BillDAO implements IBillDAO {
    @Override
    public int save(Bill bill) {
        return TransactionManager.execute(session -> {
            session.save(bill);
            session.flush();
            return bill.getBillID();
        });
    }

    @Override
    public Bill findByBillID(int billID) {
        return TransactionManager.execute(session -> session.createQuery(
                        "FROM Bill WHERE billID = :billID AND isDeleted = false", Bill.class)
                .setParameter("billID", billID)
                .uniqueResult());
    }

    @Override
    public Bill findBySerialNumber(String serialNumber) {
        return TransactionManager.execute(session -> session.createQuery(
                        "FROM Bill WHERE serialNumber = :serialNumber AND isDeleted = false", Bill.class)
                .setParameter("serialNumber", serialNumber)
                .uniqueResult());
    }
}
