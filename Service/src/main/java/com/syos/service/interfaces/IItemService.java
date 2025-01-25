package main.java.com.syos.service.interfaces;

import main.java.com.syos.data.model.Item;
import main.java.com.syos.request.InsertItemRequest;

import java.util.List;

public interface IItemService {

    void InsertItem(InsertItemRequest request);

    Item getItem(String itemCode, String batchCode);

    List<Item> getAllItems();
}
