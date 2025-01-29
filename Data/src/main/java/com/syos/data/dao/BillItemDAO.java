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

    @Override
    public List<Object[]> findBillItemsWithItemNames(int billID) {
        return TransactionManager.execute(session -> session.createQuery(
                        "SELECT bi.itemCode, bi.batchCode, bi.quantity, bi.pricePerItem, bi.totalItemPrice, " +
                                "bi.discountID, bi.updatedDateTime, i.itemName " +
                                "FROM BillItem bi JOIN Item i " +
                                "ON bi.itemCode = i.itemCode AND bi.batchCode = i.batchCode " +
                                "WHERE bi.bill.billID = :billID", Object[].class)
                .setParameter("billID", billID)
                .list());
    }
}
