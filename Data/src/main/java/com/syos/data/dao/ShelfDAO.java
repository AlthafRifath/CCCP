package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Shelf;

public class ShelfDAO implements IShelfDAO {
    @Override
    public void save(Shelf shelf) {
        TransactionManager.execute(session -> {
            session.save(shelf);
            return null;
        });
    }
}
