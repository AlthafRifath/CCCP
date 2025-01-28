package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.BillDAO;
import main.java.com.syos.data.dao.interfaces.IBillDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.request.CreateBillRequest;
import main.java.com.syos.service.interfaces.IBillService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillService implements IBillService {
    private final IBillDAO billDAO;

    public BillService() {
        this.billDAO = new BillDAO();
    }

    @Override
    public int createBill(CreateBillRequest request) {
        return TransactionManager.execute(session -> {
            // Create Bill
            Bill bill = new Bill();
            bill.setBillDate(LocalDateTime.now());
            bill.setSerialNumber("BILL-" + System.currentTimeMillis());
            bill.setCashTendered(request.getCashTendered());
            bill.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
            bill.setUpdatedDateTime(LocalDateTime.now());

            bill.setTotalAmount(BigDecimal.ZERO); // Initially set to 0
            bill.setChange(request.getCashTendered()); // Set initial cash change

            session.save(bill);
            session.flush(); // Ensure BillID is generated

            System.out.println("Bill created successfully! Bill ID: " + bill.getBillID());
            return bill.getBillID();  // Return BillID for BillItem insertion
        });
    }
}

