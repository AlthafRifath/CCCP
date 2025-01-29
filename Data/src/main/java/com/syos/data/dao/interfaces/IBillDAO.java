package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Bill;

public interface IBillDAO {
    int save(Bill bill);
    Bill findByBillID(int billID);
    Bill findBySerialNumber(String serialNumber);
}
