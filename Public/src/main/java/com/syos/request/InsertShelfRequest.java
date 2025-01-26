package main.java.com.syos.request;

import java.time.LocalDateTime;

public class InsertShelfRequest {
    private int shelfId;
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
