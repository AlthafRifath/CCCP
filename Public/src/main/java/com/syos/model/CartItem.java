package main.java.com.syos.model;

public class CartItem {
    private String itemCode;
    private String batchCode;
    private String itemName;
    private double price;
    private int quantity;
    private String imageUrl;

    public CartItem(String itemCode, String batchCode, String itemName, double price, int quantity, String imageUrl) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public String getItemCode() { return itemCode; }
    public String getBatchCode() { return batchCode; }
    public String getItemName() { return itemName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getImageUrl() { return imageUrl; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
