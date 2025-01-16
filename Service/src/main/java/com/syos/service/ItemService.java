package main.java.com.syos.service;

import main.java.com.syos.data.dao.ItemDAO;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.service.interfaces.IItemService;

import java.util.List;

public class ItemService implements IItemService {
    private final IItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    @Override
    public void saveItem(Item item) {
        if (item.getPrice() <= 0) {
            throw new IllegalArgumentException("Item price must be greater than 0.");
        }

        if (item.getInitialQuantity() < 0 || item.getCurrentQuantity() < 0) {
            throw new IllegalArgumentException("Quantities cannot be negative.");
        }

        itemDAO.save(item);
    }

    @Override
    public Item getItem(String itemCode, String batchCode) {
        return itemDAO.findById(itemCode, batchCode);
    }

    @Override
    public List<Item> getAllItems() {
        return itemDAO.findAll();
    }
}
