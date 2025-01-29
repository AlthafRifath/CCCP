package main.java.com.syos.service.interfaces;

import main.java.com.syos.request.TransactionRequest;

public interface ITransactionService {
    void recordTransaction(TransactionRequest request);
}
