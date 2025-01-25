package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Item;

import java.util.List;
import java.util.Optional;

public interface IItemDAO {

    void save(Item item);
    Optional<Item> findByItemCodeAndBatchCode(String itemCode, String batchCode);
    List<Item> findAll();
}
