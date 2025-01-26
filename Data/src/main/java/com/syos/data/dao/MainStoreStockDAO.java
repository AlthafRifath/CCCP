package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;

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
    public Optional<GetMainStoreStockDetailsDTO> findByStoreIdAndItemCodeAndBatchCode(int storeId, String itemCode, String batchCode) {
        return TransactionManager.execute(session -> {
            String hql = "SELECT new main.java.com.syos.dto.GetMainStoreStockDetailsDTO(" +
                    "m.storeID, m.itemCode, m.batchCode, m.initialStock, m.currentStock, m.lastRestockedDate) " +
                    "FROM MainStoreStock m WHERE m.storeID = :storeId AND m.itemCode = :itemCode " +
                    "AND m.batchCode = :batchCode AND m.isDeleted = false";

            return session.createQuery(hql, GetMainStoreStockDetailsDTO.class)
                    .setParameter("storeId", storeId)
                    .setParameter("itemCode", itemCode)
                    .setParameter("batchCode", batchCode)
                    .uniqueResultOptional();
        });
    }
}
