package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.BillDAO;
import main.java.com.syos.data.dao.BillItemDAO;
import main.java.com.syos.data.dao.interfaces.IBillDAO;
import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.dto.GetBillDTO;
import main.java.com.syos.dto.GetBillItemDTO;
import main.java.com.syos.request.CreateBillRequest;
import main.java.com.syos.service.interfaces.IBillService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BillService implements IBillService {
    private final IBillDAO billDAO;
    private final IBillItemDAO billItemDAO;

    public BillService() {
        this.billDAO = new BillDAO();
        this.billItemDAO = new BillItemDAO();
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

    @Override
    public GetBillDTO getBillByID(int billID) {
        Bill bill = billDAO.findByBillID(billID);
        return convertToDTO(bill);
    }

    @Override
    public GetBillDTO getBillBySerialNumber(String serialNumber) {
        Bill bill = billDAO.findBySerialNumber(serialNumber);
        return convertToDTO(bill);
    }

    private GetBillDTO convertToDTO(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found.");
        }
        List<GetBillItemDTO> items = billItemDAO.findBillItemsWithItemNames(bill.getBillID())
                .stream()
                .map(obj -> new GetBillItemDTO(
                        (String) obj[0],  // ItemCode
                        (String) obj[7],  // ItemName
                        (String) obj[1],  // BatchCode
                        (int) obj[2],     // Quantity
                        (BigDecimal) obj[3],  // PricePerItem
                        (BigDecimal) obj[4],  // TotalItemPrice
                        (Integer) obj[5],  // DiscountID
                        (LocalDateTime) obj[6] // UpdatedDateTime
                ))
                .collect(Collectors.toList());

        return new GetBillDTO(
                bill.getBillID(),
                bill.getCustomerID(),
                bill.getDiscountID(),
                bill.getSerialNumber(),
                bill.getBillDate(),
                bill.getTotalAmount(),
                bill.getCashTendered(),
                bill.getChange(),
                items);
    }
}

