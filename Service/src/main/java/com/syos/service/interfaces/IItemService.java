package main.java.com.syos.service.interfaces;

import main.java.com.syos.data.model.Item;
import main.java.com.syos.dto.GetItemDTO;
import main.java.com.syos.request.DeleteItemRequest;
import main.java.com.syos.request.InsertItemRequest;
import main.java.com.syos.request.UpdateItemRequest;

import java.util.List;

public interface IItemService {

    void InsertItem(InsertItemRequest request);
    GetItemDTO getItemByItemCodeAndBatchCode(String itemCode, String batchCode);
    void updateItem(UpdateItemRequest request);
    void deleteItem(DeleteItemRequest request);
}
