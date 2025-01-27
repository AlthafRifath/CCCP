package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Shelf;

import java.util.List;

public class ShelfDAO implements IShelfDAO {
    @Override
    public void save(Shelf shelf) {
        TransactionManager.execute(session -> {
            session.save(shelf);
            return null;
        });
    }

    @Override
    public List<Shelf> findByShelfIdAndStoreId(int shelfId, int storeId) {
        return TransactionManager.execute(session -> {
            String hql = "FROM Shelf s WHERE s.shelfID = :shelfId AND s.store.storeID = :storeId AND s.isDeleted = false";
            return session.createQuery(hql, Shelf.class)
                    .setParameter("shelfId", shelfId)
                    .setParameter("storeId", storeId)
                    .list();
        });
    }
}
