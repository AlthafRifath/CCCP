package main.java.com.syos.reports;

import main.java.com.syos.data.dao.BillDAO;
import main.java.com.syos.data.dao.TransactionDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.data.model.Transaction;

import java.util.List;

public class BillReport extends AbstractReportGenerator{
    private final BillDAO billDAO;
    private final TransactionDAO transactionDAO;
    private List<Bill> bills;

    public BillReport() {
        this.billDAO = new BillDAO();
        this.transactionDAO = new TransactionDAO();
    }

    @Override
    protected void fetchData() {
        System.out.println("Fetching customer transactions...");
        bills = billDAO.getAllBills();
    }

    @Override
    protected void processData() {
        System.out.println("Processing customer transactions...");
    }

    @Override
    protected void formatReport() {
        System.out.println("\n=== Bill Report ===");
        System.out.println("Bill ID | Customer ID | Total Amount | Transaction Type | Date");

        if (bills.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Bill bill : bills) {
                Transaction transaction = transactionDAO.getTransactionByBillID(bill.getBillID());
                String transactionType = transaction != null ? transaction.getTransactionType().toString() : "N/A";

                System.out.println(bill.getBillID() + " | " +
                        (bill.getCustomerID() != null ? bill.getCustomerID() : "Walk-in") + " | " +
                        bill.getTotalAmount() + " | " +
                        transactionType + " | " +
                        bill.getBillDate());
            }
        }
    }
}
