package main.java.com.syos.request;

public class UpdateShelfRequest {
    private int storeId;
    private int shelfId;
    private String itemCode;
    private String batchCode;
    private int storeIdFromMainStoreStock;
    private int quantityOnShelf;
    private int quantityToAdd;

    // Getters and setters
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

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

    public int getStoreIdFromMainStoreStock() {
        return storeIdFromMainStoreStock;
    }

    public void setStoreIdFromMainStoreStock(int storeIdFromMainStoreStock) {
        this.storeIdFromMainStoreStock = storeIdFromMainStoreStock;
    }

    public int getQuantityOnShelf() {
        return quantityOnShelf;
    }

    public void setQuantityOnShelf(int quantityOnShelf) {
        this.quantityOnShelf = quantityOnShelf;
    }

    public int getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setQuantityToAdd(int quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }
}
