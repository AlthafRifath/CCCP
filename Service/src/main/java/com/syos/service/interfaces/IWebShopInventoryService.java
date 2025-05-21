package main.java.com.syos.service.interfaces;

import main.java.com.syos.dto.WebShopInventoryDTO;
import main.java.com.syos.request.InsertWebShopItemRequest;
import main.java.com.syos.request.UpdateWebShopItemRequest;

import java.util.List;

public interface IWebShopInventoryService {
    void insertItem(InsertWebShopItemRequest request);
    void updateItem(UpdateWebShopItemRequest request);
    void deleteItem(int webShopId, String itemCode, String batchCode);
    List<WebShopInventoryDTO> getAllItems();
}
