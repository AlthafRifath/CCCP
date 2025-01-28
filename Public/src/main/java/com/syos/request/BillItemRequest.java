package main.java.com.syos.request;

import java.math.BigDecimal;

public class BillItemRequest {
    private String itemCode;
    private String batchCode;
    private int quantity;
    private BigDecimal pricePerItem;
    private BigDecimal totalItemPrice; // Computed value
    private Integer discountID;

    // Constructor (Fixed)
    public BillItemRequest(String itemCode, String batchCode, int quantity, BigDecimal pricePerItem, Integer discountID) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalItemPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity)); // Compute automatically
        this.discountID = discountID;
    }

    // Getters and Setters
    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public String getBatchCode() { return batchCode; }
    public void setBatchCode(String batchCode) { this.batchCode = batchCode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalItemPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity)); // Recalculate on update
    }

    public BigDecimal getPricePerItem() { return pricePerItem; }
    public void setPricePerItem(BigDecimal pricePerItem) {
        this.pricePerItem = pricePerItem;
        this.totalItemPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity)); // Recalculate on update
    }

    public BigDecimal getTotalItemPrice() { return totalItemPrice; } // No setter needed

    public Integer getDiscountID() { return discountID; }
    public void setDiscountID(Integer discountID) { this.discountID = discountID; }
}

