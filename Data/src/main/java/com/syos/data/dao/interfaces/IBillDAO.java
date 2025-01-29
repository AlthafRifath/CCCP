package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Bill;
import main.java.com.syos.data.model.BillItem;

import java.util.List;

public interface IBillDAO {
    int save(Bill bill);
    Bill findByBillID(int billID);
    Bill findBySerialNumber(String serialNumber);
    List<BillItem> getSalesForToday();
    List<Bill> getAllBills();
}
