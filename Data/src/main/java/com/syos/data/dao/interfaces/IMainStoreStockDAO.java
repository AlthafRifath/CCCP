package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;
import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;

import java.util.Optional;

public interface IMainStoreStockDAO {
    void save(MainStoreStock mainStoreStock);
    Optional<MainStoreStock> findByStoreIdAndItemCodeAndBatchCode(int storeId, String itemCode, String batchCode);
    void adjustStock(int storeId, String itemCode, String batchCode, int adjustmentQuantity, IStockAdjustmentStrategy strategy);
    void softDelete(MainStoreStock mainStoreStock);
}
