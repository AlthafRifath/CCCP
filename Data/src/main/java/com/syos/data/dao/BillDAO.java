package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IBillDAO;
import main.java.com.syos.data.model.Bill;

public class BillDAO implements IBillDAO {
    @Override
    public void save(Bill bill) {
        TransactionManager.execute(session -> {
            session.save(bill);
            return null;
        });
    }
}
