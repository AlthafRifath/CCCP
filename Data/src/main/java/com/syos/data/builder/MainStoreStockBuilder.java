package main.java.com.syos.data.builder;

import main.java.com.syos.data.model.MainStoreStock;

import java.time.LocalDateTime;

public class MainStoreStockBuilder {
    private int storeId;
    private String itemCode;
    private String batchCode;
    private int initialStock;
    private int currentStock;
    private LocalDateTime lastRestockedDate;
    private boolean isDeleted;
    private Integer updatedBy;
    private LocalDateTime updatedDateTime;

    public MainStoreStockBuilder setStoreId(int storeId) {
        this.storeId = storeId;
        return this;
    }

    public MainStoreStockBuilder setItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public MainStoreStockBuilder setBatchCode(String batchCode) {
        this.batchCode = batchCode;
        return this;
    }

    public MainStoreStockBuilder setInitialStock(int initialStock) {
        this.initialStock = initialStock;
        return this;
    }

    public MainStoreStockBuilder setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
        return this;
    }

    public MainStoreStockBuilder setLastRestockedDate(LocalDateTime lastRestockedDate) {
        this.lastRestockedDate = lastRestockedDate;
        return this;
    }

    public MainStoreStockBuilder setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public MainStoreStockBuilder setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MainStoreStockBuilder setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
        return this;
    }

    public MainStoreStock build() {
        MainStoreStock mainStoreStock = new MainStoreStock();
        mainStoreStock.setStoreID(storeId);
        mainStoreStock.setItemCode(itemCode);
        mainStoreStock.setBatchCode(batchCode);
        mainStoreStock.setInitialStock(initialStock);
        mainStoreStock.setCurrentStock(currentStock);
        mainStoreStock.setLastRestockedDate(lastRestockedDate);
        mainStoreStock.setDeleted(isDeleted);
        mainStoreStock.setUpdatedBy(updatedBy);
        mainStoreStock.setUpdatedDateTime(updatedDateTime);
        return mainStoreStock;
    }
}
