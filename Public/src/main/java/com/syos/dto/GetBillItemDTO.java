package main.java.com.syos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GetBillItemDTO {
    private String itemCode;
    private String itemName;
    private String batchCode;
    private int quantity;
    private BigDecimal pricePerItem;
    private BigDecimal totalItemPrice;
    private Integer discountID;
    private LocalDateTime updatedDateTime;

    public GetBillItemDTO(String itemCode, String itemName, String batchCode, int quantity, BigDecimal pricePerItem, BigDecimal totalItemPrice, Integer discountID, LocalDateTime updatedDateTime) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchCode = batchCode;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalItemPrice = totalItemPrice;
        this.discountID = discountID;
        this.updatedDateTime = updatedDateTime;
    }

    // Getters
    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerItem() {
        return pricePerItem;
    }

    public BigDecimal getTotalItemPrice() {
        return totalItemPrice;
    }

    public Integer getDiscountID() {
        return discountID;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }
}
