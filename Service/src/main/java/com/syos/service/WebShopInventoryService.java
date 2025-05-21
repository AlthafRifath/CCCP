package main.java.com.syos.service;

import main.java.com.syos.data.dao.ItemDAO;
import main.java.com.syos.data.dao.WebShopInventoryDAO;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.dao.interfaces.IWebShopInventoryDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.data.model.WebShopInventory;
import main.java.com.syos.dto.WebShopInventoryDTO;
import main.java.com.syos.request.InsertWebShopItemRequest;
import main.java.com.syos.request.UpdateWebShopItemRequest;
import main.java.com.syos.service.interfaces.IWebShopInventoryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WebShopInventoryService implements IWebShopInventoryService {

    private final IWebShopInventoryDAO inventoryDAO;
    private final IItemDAO itemDAO;

    public WebShopInventoryService() {
        this.inventoryDAO = new WebShopInventoryDAO();
        this.itemDAO = new ItemDAO();
    }

    @Override
    public void insertItem(InsertWebShopItemRequest request) {
        WebShopInventory item = new WebShopInventory();
        item.setWebShopID(request.getWebShopId());
        item.setItemCode(request.getItemCode());
        item.setBatchCode(request.getBatchCode());
        item.setItemName(request.getItemName());
        item.setQuantityOnline(request.getQuantityOnline());
        item.setImageUrl(request.getImageUrl());
        item.setDeleted(false);
        item.setLastUpdatedDate(LocalDateTime.now());
        item.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
        item.setUpdatedDateTime(LocalDateTime.now());
        inventoryDAO.save(item);
    }

    @Override
    public void updateItem(UpdateWebShopItemRequest request) {
        Optional<WebShopInventory> optional = inventoryDAO.findById(request.getWebShopId(), request.getItemCode(), request.getBatchCode());
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Item not found for WebShopID: " + request.getWebShopId());
        }
        WebShopInventory item = optional.get();
        item.setItemName(request.getItemName());
        item.setQuantityOnline(request.getQuantityOnline());
        item.setImageUrl(request.getImageUrl());
        item.setUpdatedDateTime(LocalDateTime.now());
        item.setLastUpdatedDate(LocalDateTime.now());
        item.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
        inventoryDAO.update(item);
    }

    @Override
    public void deleteItem(int webShopId, String itemCode, String batchCode) {
        inventoryDAO.delete(webShopId, itemCode, batchCode);
    }

    @Override
    public List<WebShopInventoryDTO> getAllItems() {
        List<WebShopInventory> items = inventoryDAO.findAll();

        return items.stream().map(item -> {
            Optional<Item> itemDetails = itemDAO.findByItemCodeAndBatchCode(item.getItemCode(), item.getBatchCode());
            double price = itemDetails.map(Item::getPrice).orElse(0.0); // fallback to 0 if not found

            return new WebShopInventoryDTO(
                    item.getWebShopID(),
                    item.getItemCode(),
                    item.getBatchCode(),
                    item.getItemName(),
                    item.getQuantityOnline(),
                    item.getImageUrl(),
                    price
            );
        }).collect(Collectors.toList());
    }
}
