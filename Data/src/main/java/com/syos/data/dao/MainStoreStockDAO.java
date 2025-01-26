package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;
import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public class MainStoreStockDAO implements IMainStoreStockDAO {
    @Override
    public void save(MainStoreStock mainStoreStock) {
        TransactionManager.execute(session -> {
            session.save(mainStoreStock);
            return null;
        });
    }

    @Override
    public Optional<MainStoreStock> findByStoreIdAndItemCodeAndBatchCode(int storeId, String itemCode, String batchCode) {
        return TransactionManager.execute(session -> {
            String hql = "FROM MainStoreStock m " +
                    "WHERE m.storeID = :storeId AND m.itemCode = :itemCode " +
                    "AND m.batchCode = :batchCode AND m.isDeleted = false";

            return session.createQuery(hql, MainStoreStock.class)
                    .setParameter("storeId", storeId)
                    .setParameter("itemCode", itemCode)
                    .setParameter("batchCode", batchCode)
                    .uniqueResultOptional();
        });
    }

    @Override
    public void adjustStock(int storeId, String itemCode, String batchCode, int adjustmentQuantity, IStockAdjustmentStrategy strategy) {
        TransactionManager.execute(session -> {
            MainStoreStock stock = session.createQuery(
                            "FROM MainStoreStock WHERE storeID = :storeId AND itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                            MainStoreStock.class)
                    .setParameter("storeId", storeId)
                    .setParameter("itemCode", itemCode)
                    .setParameter("batchCode", batchCode)
                    .uniqueResult();

            if (stock == null) {
                throw new IllegalArgumentException("Stock not found for the provided StoreId, ItemCode, and BatchCode.");
            }

            // Apply the adjustment strategy
            int updatedStock = strategy.adjustStock(stock.getCurrentStock(), adjustmentQuantity);

            if (updatedStock < 0) {
                throw new IllegalStateException("Stock cannot be negative.");
            }

            // Update the stock
            stock.setCurrentStock(updatedStock);
            stock.setUpdatedDateTime(LocalDateTime.now());
            session.update(stock);

            System.out.println("Stock updated successfully for StoreId: " + storeId + ", ItemCode: " + itemCode + ", BatchCode: " + batchCode);
            return null;
        });
    }

    @Override
    public void softDelete(MainStoreStock stock) {
        TransactionManager.execute(session -> {
            stock.setDeleted(true);
            session.update(stock);
            System.out.println("MainStoreStock soft-deleted successfully for StoreId: " + stock.getStoreID()
                    + ", ItemCode: " + stock.getItemCode() + ", BatchCode: " + stock.getBatchCode());
            return null;
        });
    }
}
