package main.java.com.syos.service;

import main.java.com.syos.data.builder.ItemBuilder;
import main.java.com.syos.data.dao.ItemDAO;
import main.java.com.syos.data.dao.SoftDeleteItemDAO;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.dto.GetItemDTO;
import main.java.com.syos.request.DeleteItemRequest;
import main.java.com.syos.request.InsertItemRequest;
import main.java.com.syos.request.UpdateItemRequest;
import main.java.com.syos.service.interfaces.IItemService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ItemService implements IItemService {
    private final IItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new SoftDeleteItemDAO(new ItemDAO());
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

        Item item = new ItemBuilder()
                .setItemCode(request.getItemCode())
                .setBatchCode(request.getBatchCode())
                .setItemName(request.getItemName())
                .setPrice(request.getPrice())
                .setPurchaseDate(request.getPurchaseDate())
                .setExpiryDate(request.getExpiryDate())
                .setInitialQuantity(request.getInitialQuantity())
                .setCurrentQuantity(request.getCurrentQuantity())
                .setIsActive(true)
                .setIsDeleted(false)
                .setUpdatedDateTime(LocalDateTime.now())
                .setUpdatedBy(AdminSession.getInstance().getLoggedInUserId())
                .build();
        itemDAO.save(item);
    }

    @Override
    public GetItemDTO getItemByItemCodeAndBatchCode(String itemCode, String batchCode) {
        Optional<Item> itemOptional = itemDAO.findByItemCodeAndBatchCode(itemCode, batchCode);

        if (itemOptional.isEmpty()) {
            throw new IllegalArgumentException(
                    "Item not found for ItemCode: " + itemCode + " and BatchCode: " + batchCode
            );
        }

        Item item = itemOptional.get();
        return new GetItemDTO(
                item.getItemCode(), item.getBatchCode(), item.getItemName(), item.getPrice(),
                item.getPurchaseDate(), item.getExpiryDate(), item.getInitialQuantity(),
                item.getCurrentQuantity()
        );
    }

    @Override
    public void updateItem(UpdateItemRequest request) {
        Optional<Item> itemOptional = itemDAO.findByItemCodeAndBatchCode(request.getItemCode(), request.getBatchCode());

        if (itemOptional.isEmpty()) {
            throw new IllegalArgumentException("Item not found for ItemCode: " + request.getItemCode() +
                    " and BatchCode: " + request.getBatchCode());
        }

        Item item = itemOptional.get();
        item.setPrice(request.getPrice());
        item.setInitialQuantity(request.getInitialQuantity());
        item.setCurrentQuantity(request.getCurrentQuantity());
        item.setIsActive(request.getIsActive());
        item.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
        item.setUpdatedDateTime(LocalDateTime.now());

        itemDAO.update(item);
        System.out.println("Item updated successfully.");
    }

    @Override
    public void deleteItem(DeleteItemRequest request) {
        SoftDeleteItemDAO softDeleteDAO = (SoftDeleteItemDAO) itemDAO;
        softDeleteDAO.softDelete(request.getItemCode(), request.getBatchCode());
    }
}
