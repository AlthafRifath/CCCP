package main.java.com.syos.dto;

import java.time.LocalDateTime;

public class GetItemDTO {
    private String itemCode;
    private String batchCode;
    private String itemName;
    private double price;
    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;
    private int initialQuantity;
    private int currentQuantity;

    public GetItemDTO(String itemCode, String batchCode, String itemName, double price, LocalDateTime purchaseDate, LocalDateTime expiryDate, int initialQuantity, int currentQuantity) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.itemName = itemName;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
    }

    // Getters
    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }
}
