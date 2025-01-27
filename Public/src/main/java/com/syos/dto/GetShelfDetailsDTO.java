package main.java.com.syos.dto;

import java.time.LocalDateTime;

public class GetShelfDetailsDTO {
    private int shelfId;
    private String storeName;
    private String itemCode;
    private String batchCode;
    private int quantityOnShelf;
    private LocalDateTime lastRestockedDate;
    private String itemName; // New field

    public GetShelfDetailsDTO(int shelfId, String storeName, String itemCode, String batchCode, int quantityOnShelf, LocalDateTime lastRestockedDate, String itemName) {
        this.shelfId = shelfId;
        this.storeName = storeName;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantityOnShelf = quantityOnShelf;
        this.lastRestockedDate = lastRestockedDate;
        this.itemName = itemName;
    }

    // Getters
    public int getShelfId() {
        return shelfId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public int getQuantityOnShelf() {
        return quantityOnShelf;
    }

    public LocalDateTime getLastRestockedDate() {
        return lastRestockedDate;
    }

    public String getItemName() { // Getter for itemName
        return itemName;
    }
}
