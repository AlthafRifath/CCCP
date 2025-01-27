package main.java.com.syos.request;

import java.time.LocalDateTime;

public class InsertShelfRequest {
    private int shelfId;
    private int storeIdFromMainStoreStock; // for validation in tblMainStoreStock
    private int storeIdFromStore;
    private String itemCode;
    private String batchCode;
    private int quantityOnShelf;
    private LocalDateTime lastRestockedDate;

    // Getters and setters
    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getStoreIdFromMainStoreStock() {
        return storeIdFromMainStoreStock;
    }

    public void setStoreIdFromMainStoreStock(int storeIdFromMainStoreStock) {
        this.storeIdFromMainStoreStock = storeIdFromMainStoreStock;
    }

    public int getStoreIdFromStore() {
        return storeIdFromStore;
    }

    public void setStoreIdFromStore(int storeIdFromStore) {
        this.storeIdFromStore = storeIdFromStore;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getQuantityOnShelf() {
        return quantityOnShelf;
    }

    public void setQuantityOnShelf(int quantityOnShelf) {
        this.quantityOnShelf = quantityOnShelf;
    }

    public LocalDateTime getLastRestockedDate() {
        return lastRestockedDate;
    }

    public void setLastRestockedDate(LocalDateTime lastRestockedDate) {
        this.lastRestockedDate = lastRestockedDate;
    }
}
