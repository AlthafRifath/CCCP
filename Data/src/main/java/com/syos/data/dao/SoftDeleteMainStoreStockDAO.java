package main.java.com.syos.data.dao;

import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;
import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public class SoftDeleteMainStoreStockDAO implements IMainStoreStockDAO {
    private final IMainStoreStockDAO decoratedDAO;

    public SoftDeleteMainStoreStockDAO(IMainStoreStockDAO decoratedDAO) {
        this.decoratedDAO = decoratedDAO;
    }

    @Override
    public void save(MainStoreStock mainStoreStock) {
        decoratedDAO.save(mainStoreStock);
    }

    @Override
    public Optional<MainStoreStock> findByStoreIdAndItemCodeAndBatchCode(int storeId, String itemCode, String batchCode) {
        return decoratedDAO.findByStoreIdAndItemCodeAndBatchCode(storeId, itemCode, batchCode);
    }

    @Override
    public void adjustStock(int storeId, String itemCode, String batchCode, int adjustmentQuantity, IStockAdjustmentStrategy strategy) {
        decoratedDAO.adjustStock(storeId, itemCode, batchCode, adjustmentQuantity, strategy);
    }

    @Override
    public void softDelete(MainStoreStock stock) {
        stock.setDeleted(true);
        stock.setUpdatedDateTime(LocalDateTime.now());
        decoratedDAO.softDelete(stock);
    }
}
