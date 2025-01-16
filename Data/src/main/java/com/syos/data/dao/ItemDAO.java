package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;

import java.util.List;

public class ItemDAO implements IItemDAO {

    @Override
    public void save(Item item) {
        TransactionManager.execute(session -> {
            session.save(item);
            return null;
        });
    }

    @Override
    public Item findById(String itemCode, String batchCode) {
        return TransactionManager.execute(session ->
                session.get(Item.class, new Item(itemCode, batchCode))
        );
    }

    @Override
    public List<Item> findAll() {
        return TransactionManager.execute(session ->
                session.createQuery("from Item", Item.class).list()
        );
    }
}
