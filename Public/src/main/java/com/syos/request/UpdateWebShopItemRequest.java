package main.java.com.syos.request;

public class UpdateWebShopItemRequest {
    private final int webShopId;
    private final String itemCode;
    private final String batchCode;
    private final String itemName;
    private final int quantityOnline;
    private final String imageUrl;

    public UpdateWebShopItemRequest(int webShopId, String itemCode, String batchCode, String itemName, int quantityOnline, String imageUrl) {
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
