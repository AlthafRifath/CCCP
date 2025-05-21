package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.WebShopInventory;

import java.util.List;
import java.util.Optional;

public interface IWebShopInventoryDAO {
    void save(WebShopInventory item);
    Optional<WebShopInventory> findById(int webShopId, String itemCode, String batchCode);
    List<WebShopInventory> findAll();
    void update(WebShopInventory item);
    void delete(int webShopId, String itemCode, String batchCode);
}
