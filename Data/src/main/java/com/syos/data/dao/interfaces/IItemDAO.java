package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Item;

import java.util.List;

public interface IItemDAO {

    void save(Item item);

    Item findById(String itemCode, String batchCode);

    List<Item> findAll();
}
