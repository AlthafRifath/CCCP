package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.MainStoreStock;

public class MainStoreStockDAO implements IMainStoreStockDAO {
    @Override
    public void save(MainStoreStock mainStoreStock) {
        TransactionManager.execute(session -> {
            session.save(mainStoreStock);
            return null;
        });
    }
}
