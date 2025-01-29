package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Shelf;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Shelf> findByCompositeKey(int storeId, int shelfId, String itemCode, String batchCode) {
        return TransactionManager.execute(session -> {
            String hql = "FROM Shelf s WHERE s.store.storeID = :storeId AND s.shelfID = :shelfId " +
                    "AND s.itemCode = :itemCode AND s.batchCode = :batchCode AND s.isDeleted = false";
            return session.createQuery(hql, Shelf.class)
                    .setParameter("storeId", storeId)
                    .setParameter("shelfId", shelfId)
                    .setParameter("itemCode", itemCode)
                    .setParameter("batchCode", batchCode)
                    .uniqueResultOptional();
        });
    }

    @Override
    public void update(Shelf shelf) {
        TransactionManager.execute(session -> {
            session.update(shelf);
            return null;
        });
    }

    @Override
    public Shelf findByItemAndBatch(String itemCode, String batchCode) {
        return TransactionManager.execute(session -> {
            return session.createQuery(
                            "FROM Shelf WHERE itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                            Shelf.class)
                    .setParameter("itemCode", itemCode)
                    .setParameter("batchCode", batchCode)
                    .uniqueResult();
        });
    }

    @Override
    public List<Shelf> getCurrentStock() {
        return TransactionManager.execute(session -> session.createQuery(
                "FROM Shelf WHERE isDeleted = false", Shelf.class).list());
    }

    @Override
    public List<Shelf> getItemsBelowThreshold(int threshold) {
        return TransactionManager.execute(session -> session.createQuery(
                        "FROM Shelf WHERE quantityOnShelf < :threshold AND isDeleted = false", Shelf.class)
                .setParameter("threshold", threshold)
                .list());
    }
}
