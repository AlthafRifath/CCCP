package main.java.com.syos.dto;

public class WebShopInventoryDTO {
    private int webShopId;
    private String itemCode;
    private String batchCode;
    private String itemName;
    private int quantityOnline;
    private String imageUrl;
    private double price;

    public WebShopInventoryDTO(int webShopId, String itemCode, String batchCode, String itemName, int quantityOnline, String imageUrl, double price) {
        this.webShopId = webShopId;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.itemName = itemName;
        this.quantityOnline = quantityOnline;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    // Getters
    public int getWebShopId() {
        return webShopId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantityOnline() {
        return quantityOnline;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() {
        return price;
    }
}
