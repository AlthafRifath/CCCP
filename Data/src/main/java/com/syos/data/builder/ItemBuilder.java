package main.java.com.syos.data.builder;

import main.java.com.syos.data.model.Item;

import java.time.LocalDateTime;

public class ItemBuilder {
    private String itemCode;
    private String batchCode;
    private String itemName;
    private double price;
    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;
    private int initialQuantity;
    private int currentQuantity;
    private boolean isActive;
    private boolean isDeleted;
    private LocalDateTime updatedDateTime;
    private int updatedBy;

    public ItemBuilder setItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public ItemBuilder setBatchCode(String batchCode) {
        this.batchCode = batchCode;
        return this;
    }

    public ItemBuilder setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ItemBuilder setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public ItemBuilder setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public ItemBuilder setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
        return this;
    }

    public ItemBuilder setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
        return this;
    }

    public ItemBuilder setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public ItemBuilder setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public ItemBuilder setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
        return this;
    }

    public ItemBuilder setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Item build() {
        Item item = new Item();
        item.setItemCode(itemCode);
        item.setBatchCode(batchCode);
        item.setItemName(itemName);
        item.setPrice(price);
        item.setPurchaseDate(purchaseDate);
        item.setExpiryDate(expiryDate);
        item.setInitialQuantity(initialQuantity);
        item.setCurrentQuantity(currentQuantity);
        item.setIsActive(isActive);
        item.setIsDeleted(isDeleted);
        item.setUpdatedDateTime(updatedDateTime);
        item.setUpdatedBy(updatedBy);
        return item;
    }
}
