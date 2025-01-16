package main.java.com.syos.service.interfaces;

import main.java.com.syos.data.model.Item;

import java.util.List;

public interface IItemService {

    void saveItem(Item item);

    Item getItem(String itemCode, String batchCode);

    List<Item> getAllItems();
}
