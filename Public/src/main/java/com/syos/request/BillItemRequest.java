package main.java.com.syos.request;

import java.math.BigDecimal;

public class BillItemRequest {
    private String itemCode;
    private String batchCode;
    private int quantity;
    private BigDecimal pricePerItem;
    private BigDecimal totalItemPrice;
    private Integer discountID;

    // Constructors
    public BillItemRequest(String itemCode, String batchCode, int quantity, BigDecimal pricePerItem, BigDecimal totalItemPrice, Integer discountID) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalItemPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity));
        this.discountID = discountID;
    }

    // Getters and Setters
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(BigDecimal pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public BigDecimal getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(BigDecimal totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public Integer getDiscountID() {
        return discountID;
    }

    public void setDiscountID(Integer discountID) {
        this.discountID = discountID;
    }
}
