package main.java.com.syos.dto;

import java.time.LocalDateTime;

public class GetShelfDetailsDTO {
    private int storeId;
    private String storeName;
    private String itemCode;
    private String batchCode;
    private int quantityOnShelf;
    private LocalDateTime lastRestockedDate;

    public GetShelfDetailsDTO(int storeId, String storeName, String itemCode, String batchCode, int quantityOnShelf, LocalDateTime lastRestockedDate) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantityOnShelf = quantityOnShelf;
        this.lastRestockedDate = lastRestockedDate;
    }

    // Getters
    public int getStoreId() {
        return storeId;
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
}
