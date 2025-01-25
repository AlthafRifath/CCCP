package main.java.com.syos.service;

import main.java.com.syos.data.dao.ItemDAO;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.request.InsertItemRequest;
import main.java.com.syos.service.interfaces.IItemService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ItemService implements IItemService {
    private final IItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    @Override
    public void InsertItem(InsertItemRequest request) {
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (request.getInitialQuantity() <= 0) {
            throw new IllegalArgumentException("Initial quantity must be greater than 0");
        }
        Integer updatedBy = AdminSession.getInstance().getLoggedInUserId();
        if (updatedBy == null) {
            throw new IllegalStateException("User must be logged in to perform this operation");
        }

        Item item = new Item();
        item.setItemCode(request.getItemCode());
        item.setBatchCode(request.getBatchCode());
        item.setItemName(request.getItemName());
        item.setPrice(request.getPrice());
        item.setPurchaseDate(convertToDate(request.getPurchaseDate()));
        item.setExpiryDate(convertToDate(request.getExpiryDate()));
        item.setInitialQuantity(request.getInitialQuantity());
        item.setCurrentQuantity(request.getCurrentQuantity());
        item.setIsActive(true);
        item.setIsDeleted(false);
        item.setUpdatedDateTime(LocalDateTime.now());
        item.setUpdatedBy(updatedBy);

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

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
