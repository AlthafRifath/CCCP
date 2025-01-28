package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.model.BillItem;

public class BillItemDAO implements IBillItemDAO {
    @Override
    public void save(BillItem billItem) {
        TransactionManager.execute(session -> {
            session.save(billItem);
            return null;
        });
    }
}
