package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.BillDAO;
import main.java.com.syos.data.dao.BillItemDAO;
import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.dao.interfaces.IBillDAO;
import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.data.model.BillItem;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.request.BillItemRequest;
import main.java.com.syos.request.CreateBillRequest;
import main.java.com.syos.service.interfaces.IBillService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillService implements IBillService {
    private final IBillDAO billDAO;
    private final IBillItemDAO billItemDAO;
    private final IShelfDAO shelfDAO;

    public BillService() {
        this.billDAO = new BillDAO();
        this.billItemDAO = new BillItemDAO();
        this.shelfDAO = new ShelfDAO();
    }

    @Override
    public void createBill(CreateBillRequest request) {
        TransactionManager.execute(session -> {
            // Create Bill
            Bill bill = new Bill();
            bill.setBillDate(LocalDateTime.now());
            bill.setSerialNumber("BILL-" + System.currentTimeMillis());
            bill.setCashTendered(request.getCashTendered());
            bill.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
            bill.setUpdatedDateTime(LocalDateTime.now());

            List<BillItem> billItems = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (BillItemRequest itemRequest : request.getBillItems()) {
                // Fetch Shelf
                Shelf shelf = session.createQuery(
                                "FROM Shelf WHERE itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                                Shelf.class)
                        .setParameter("itemCode", itemRequest.getItemCode())
                        .setParameter("batchCode", itemRequest.getBatchCode())
                        .uniqueResult();

                if (shelf == null) {
                    throw new IllegalArgumentException("Shelf not found for ItemCode: " + itemRequest.getItemCode() +
                            ", BatchCode: " + itemRequest.getBatchCode());
                }

                // Check stock availability
                if (shelf.getQuantityOnShelf() < itemRequest.getQuantity()) {
                    throw new IllegalArgumentException("Insufficient stock on shelf for ItemCode: " +
                            itemRequest.getItemCode() + ", BatchCode: " + itemRequest.getBatchCode());
                }

                // Deduct stock from shelf
                shelf.setQuantityOnShelf(shelf.getQuantityOnShelf() - itemRequest.getQuantity());
                shelf.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
                shelf.setUpdatedDateTime(LocalDateTime.now());
                session.update(shelf);

                // Create BillItem
                BillItem billItem = new BillItem();
                billItem.setBill(bill);
                billItem.setItemCode(itemRequest.getItemCode());
                billItem.setBatchCode(itemRequest.getBatchCode());
                billItem.setQuantity(itemRequest.getQuantity());
                billItem.setPricePerItem(itemRequest.getPricePerItem());
                billItem.setTotalItemPrice(itemRequest.getTotalItemPrice());
                billItems.add(billItem);

                totalAmount = totalAmount.add(billItem.getTotalItemPrice());
            }

            bill.setTotalAmount(totalAmount);
            bill.setChange(request.getCashTendered().subtract(totalAmount));

            // Save Bill and BillItems
            session.save(bill);
            for (BillItem billItem : billItems) {
                session.save(billItem);
            }

            System.out.println("Bill created successfully!");
            return null;
        });
    }
}
