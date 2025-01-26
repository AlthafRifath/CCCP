package main.java.com.syos.service.interfaces;

import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;
import main.java.com.syos.request.DeleteMainStoreStockItemRequest;
import main.java.com.syos.request.InsertMainStoreStockRequest;

import java.util.Optional;

public interface IMainStoreStockService {
    void insertMainStoreStock(InsertMainStoreStockRequest request);
    Optional<GetMainStoreStockDetailsDTO> getMainStoreStockDetails(int storeId, String itemCode, String batchCode);
    void deleteMainStoreStockItem(DeleteMainStoreStockItemRequest request);
}
