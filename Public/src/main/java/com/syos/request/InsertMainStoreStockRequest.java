package main.java.com.syos.request;

import java.time.LocalDateTime;

public class InsertMainStoreStockRequest {
    private int storeId;
    private String itemCode;
    private String batchCode;
    private int initialStock;
    private int currentStock;
    private LocalDateTime lastRestockedDate;

    public InsertMainStoreStockRequest(int storeId, String itemCode, String batchCode, int initialStock, int currentStock, LocalDateTime lastRestockedDate) {
        this.storeId = storeId;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
        this.lastRestockedDate = lastRestockedDate;
    }

    // Getters
    public int getStoreId() {
        return storeId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public LocalDateTime getLastRestockedDate() {
        return lastRestockedDate;
    }
}
