package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.BillItemDAO;
import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.data.model.BillItem;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.request.BillItemRequest;
import main.java.com.syos.service.interfaces.IBillItemService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillItemService implements IBillItemService {
    private final IBillItemDAO billItemDAO;
    private final IShelfDAO shelfDAO;

    public BillItemService() {
        this.billItemDAO = new BillItemDAO();
        this.shelfDAO = new ShelfDAO();
    }

    @Override
    public void addBillItems(int billID, List<BillItemRequest> billItems) {
        TransactionManager.execute(session -> {
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (BillItemRequest itemRequest : billItems) {
                // Fetch Shelf
                Shelf shelf = shelfDAO.findByItemAndBatch(itemRequest.getItemCode(), itemRequest.getBatchCode());

                if (shelf == null || shelf.getQuantityOnShelf() < itemRequest.getQuantity()) {
                    throw new IllegalArgumentException("Insufficient stock for ItemCode: " + itemRequest.getItemCode());
                }

                // Deduct stock from shelf
                shelf.setQuantityOnShelf(shelf.getQuantityOnShelf() - itemRequest.getQuantity());
                shelf.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
                shelf.setUpdatedDateTime(LocalDateTime.now());
                session.update(shelf);

                // Create BillItem
                BillItem billItem = new BillItem();
                billItem.setBillID(billID); // Assign BillID from Bill
                billItem.setItemCode(itemRequest.getItemCode());
                billItem.setBatchCode(itemRequest.getBatchCode());
                billItem.setQuantity(itemRequest.getQuantity());
                billItem.setPricePerItem(itemRequest.getPricePerItem());
                billItem.setTotalItemPrice(itemRequest.getTotalItemPrice());
                billItem.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
                billItem.setUpdatedDateTime(LocalDateTime.now());

                session.save(billItem);
                totalAmount = totalAmount.add(billItem.getTotalItemPrice());
            }

            // Update Bill totalAmount
            Bill bill = session.get(Bill.class, billID);
            bill.setTotalAmount(totalAmount);
            bill.setChange(bill.getCashTendered().subtract(totalAmount));
            session.update(bill);

            System.out.println("Bill items added successfully!");
            return null;
        });
    }
}

