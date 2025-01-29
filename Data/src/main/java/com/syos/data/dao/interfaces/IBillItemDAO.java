package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.BillItem;

import java.util.List;

public interface IBillItemDAO {
    void save(BillItem billItem);
    List<BillItem> findByBillID(int billID);
    List<Object[]> findBillItemsWithItemNames(int billID);
}
