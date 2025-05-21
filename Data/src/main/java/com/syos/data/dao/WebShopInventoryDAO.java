package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IWebShopInventoryDAO;
import main.java.com.syos.data.model.WebShopInventory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class WebShopInventoryDAO implements IWebShopInventoryDAO {

    @Override
    public void save(WebShopInventory item) {
        TransactionManager.execute(session -> {
            session.save(item);
            return null;
        });
    }

    @Override
    public Optional<WebShopInventory> findById(int webShopId, String itemCode, String batchCode) {
        return TransactionManager.execute(session -> {
            Query<WebShopInventory> query = session.createQuery(
                    "FROM WebShopInventory WHERE webShopID = :id AND itemCode = :code AND batchCode = :batch AND isDeleted = false",
                    WebShopInventory.class
            );
            query.setParameter("id", webShopId);
            query.setParameter("code", itemCode);
            query.setParameter("batch", batchCode);
            return query.uniqueResultOptional();
        });
    }

    @Override
    public List<WebShopInventory> findAll() {
        return TransactionManager.execute(session ->
                session.createQuery("FROM WebShopInventory WHERE isDeleted = false", WebShopInventory.class).list()
        );
    }

    @Override
    public void update(WebShopInventory item) {
        TransactionManager.execute(session -> {
            session.update(item);
            return null;
        });
    }

    @Override
    public void delete(int webShopId, String itemCode, String batchCode) {
        TransactionManager.execute(session -> {
            Query<?> query = session.createQuery(
                    "UPDATE WebShopInventory SET isDeleted = true WHERE webShopID = :id AND itemCode = :code AND batchCode = :batch"
            );
            query.setParameter("id", webShopId);
            query.setParameter("code", itemCode);
            query.setParameter("batch", batchCode);
            query.executeUpdate();
            return null;
        });
    }
}
