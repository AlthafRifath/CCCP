package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;

import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;

public class ItemDAO implements IItemDAO {

    @Override
    public void save(Item item) {
        TransactionManager.execute(session -> {
            session.save(item);
            return null;
        });
    }

    @Override
    public Optional<Item> findByItemCodeAndBatchCode(String itemCode, String batchCode) {
        return TransactionManager.execute(session -> {
            Query<Item> query = session.createQuery(
                    "FROM Item WHERE itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                    Item.class
            );
            query.setParameter("itemCode", itemCode);
            query.setParameter("batchCode", batchCode);
            return query.uniqueResultOptional();
        });
    }

    @Override
    public void update(Item item) {
        TransactionManager.execute(session -> {
            session.update(item);
            return null;
        });
    }
}
