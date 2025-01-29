package main.java.com.syos.request;

import main.java.com.syos.enums.TransactionType;

public class TransactionRequest {
    private int billID;
    private TransactionType transactionType;

    public TransactionRequest(int billID, TransactionType transactionType) {
        this.billID = billID;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

}
