package main.java.com.syos.request;

public class UpdateItemRequest {
    private String itemCode;
    private String batchCode;
    private double price;
    private int initialQuantity;
    private int currentQuantity;
    private boolean isActive;

    public UpdateItemRequest(String itemCode, String batchCode, double price, int initialQuantity, int currentQuantity, boolean isActive) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.price = price;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.isActive = isActive;
    }

    // Getters
    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public double getPrice() {
        return price;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
