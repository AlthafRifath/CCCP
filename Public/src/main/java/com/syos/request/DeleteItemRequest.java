package main.java.com.syos.request;

public class DeleteItemRequest {
    private String itemCode;
    private String batchCode;

    public DeleteItemRequest(String itemCode, String batchCode) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
    }

    // Getters
    public String getItemCode() {
        return itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }
}
