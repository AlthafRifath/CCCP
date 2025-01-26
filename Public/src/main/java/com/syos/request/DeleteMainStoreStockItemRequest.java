package main.java.com.syos.request;

public class DeleteMainStoreStockItemRequest {
    private int storeId;
    private String itemCode;
    private String batchCode;

    public DeleteMainStoreStockItemRequest(int storeId, String itemCode, String batchCode) {
        this.storeId = storeId;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
    }

    // Getters
    public int getStoreId() {
        return storeId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }
}
