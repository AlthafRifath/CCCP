package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.TransactionDAO;
import main.java.com.syos.data.dao.interfaces.ITransactionDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.data.model.Transaction;
import main.java.com.syos.request.TransactionRequest;
import main.java.com.syos.service.interfaces.ITransactionService;

import java.time.LocalDateTime;

public class TransactionService implements ITransactionService {
    private final ITransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }

    @Override
    public void recordTransaction(TransactionRequest request) {
        TransactionManager.execute(session -> {
            // Ensure Bill exists before adding Transaction
            Bill bill = session.get(Bill.class, request.getBillID());

            if (bill == null) {
                throw new IllegalArgumentException("Bill with ID " + request.getBillID() + " not found.");
            }

            Transaction transaction = new Transaction();
            transaction.setBillID(request.getBillID());
            transaction.setTransactionType(request.getTransactionType().name());
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
            transaction.setUpdatedDateTime(LocalDateTime.now());

            session.save(transaction);
            System.out.println("Transaction recorded successfully for Bill ID: " + request.getBillID());
            return null;
        });
    }
}
