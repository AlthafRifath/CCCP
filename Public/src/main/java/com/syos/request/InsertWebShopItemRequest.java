package main.java.com.syos.request;

public class InsertWebShopItemRequest {
    private int webShopId;
    private String itemCode;
    private String batchCode;
    private String itemName;
    private int quantityOnline;
    private String imageUrl;

    public InsertWebShopItemRequest(int webShopId, String itemCode, String batchCode, String itemName, int quantityOnline, String imageUrl) {
        this.webShopId = webShopId;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.itemName = itemName;
        this.quantityOnline = quantityOnline;
        this.imageUrl = imageUrl;
    }

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
}
