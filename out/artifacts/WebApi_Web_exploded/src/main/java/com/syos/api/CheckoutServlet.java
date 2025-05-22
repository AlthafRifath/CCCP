package com.syos.api;

import com.syos.util.GsonFactory;
import com.google.gson.Gson;
import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.MainStoreStockDAO;
import main.java.com.syos.data.dao.TransactionDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.data.model.BillItem;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.model.Transaction;
import main.java.com.syos.dto.GetBillDTO;
import main.java.com.syos.enums.TransactionType;
import main.java.com.syos.model.CartItem;
import main.java.com.syos.service.BillItemService;
import main.java.com.syos.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/api/checkout"})
public class CheckoutServlet extends HttpServlet {

    private final Gson gson = GsonFactory.create();
    private final BillService billService = new BillService();
    private final BillItemService billItemService = new BillItemService();
    private final MainStoreStockDAO stockDAO = new MainStoreStockDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read JSON body
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Map<String, Object> payload = gson.fromJson(sb.toString(), Map.class);
        Double cashTenderedRaw = (Double) payload.get("cashTendered");

        if (cashTenderedRaw == null || cashTenderedRaw <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid cashTendered amount.");
            return;
        }

        BigDecimal cashTendered = BigDecimal.valueOf(cashTenderedRaw);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("cart") == null || session.getAttribute("customerId") == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Cart or customer session not found.");
            return;
        }

        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
        Integer customerId = (Integer) session.getAttribute("customerId");
        Integer updatedBy = customerId;

        // Final container to hold generated BillID
        final int[] billIDHolder = new int[1];

        TransactionManager.execute(hSession -> {
            // Step 1: Create Bill
            Bill bill = new Bill();
            bill.setCustomerID(customerId);
            bill.setSerialNumber("BILL-" + System.currentTimeMillis());
            bill.setBillDate(LocalDateTime.now());
            bill.setCashTendered(cashTendered);
            bill.setUpdatedBy(updatedBy);
            bill.setUpdatedDateTime(LocalDateTime.now());
            bill.setTotalAmount(BigDecimal.ZERO);
            bill.setChange(BigDecimal.ZERO);
            bill.setDeleted(false);

            hSession.save(bill);
            hSession.flush();  // get generated BillID
            billIDHolder[0] = bill.getBillID();

            BigDecimal totalAmount = BigDecimal.ZERO;

            for (CartItem cartItem : cart.values()) {
                // Step 2: Deduct stock
                Optional<MainStoreStock> stockOpt = stockDAO.findByStoreIdAndItemCodeAndBatchCode(1, cartItem.getItemCode(), cartItem.getBatchCode()); // Assuming StoreID = 1

                if (stockOpt.isEmpty() || stockOpt.get().getCurrentStock() < cartItem.getQuantity()) {
                    throw new IllegalArgumentException("Insufficient stock for item: " + cartItem.getItemCode());
                }

                MainStoreStock stock = stockOpt.get();
                stock.setCurrentStock(stock.getCurrentStock() - cartItem.getQuantity());
                stock.setUpdatedBy(updatedBy);
                stock.setUpdatedDateTime(LocalDateTime.now());
                hSession.update(stock);

                // Step 3: Create BillItem
                BigDecimal itemTotal = BigDecimal.valueOf(cartItem.getPrice()).multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                BillItem billItem = new BillItem();
                billItem.setBillID(bill.getBillID());
                billItem.setItemCode(cartItem.getItemCode());
                billItem.setBatchCode(cartItem.getBatchCode());
                billItem.setQuantity(cartItem.getQuantity());
                billItem.setPricePerItem(BigDecimal.valueOf(cartItem.getPrice()));
                billItem.setTotalItemPrice(itemTotal);
                billItem.setUpdatedBy(updatedBy);
                billItem.setUpdatedDateTime(LocalDateTime.now());
                billItem.setDeleted(false);

                hSession.save(billItem);
                totalAmount = totalAmount.add(itemTotal);
            }

            // Step 4: Update Bill totals
            bill.setTotalAmount(totalAmount);
            bill.setChange(cashTendered.subtract(totalAmount));
            hSession.update(bill);

            // Step 5: Record Transaction
            Transaction txn = new Transaction();
            txn.setBillID(bill.getBillID());
            txn.setTransactionType(TransactionType.Online.name());
            txn.setTransactionDate(LocalDateTime.now());
            txn.setUpdatedBy(updatedBy);
            txn.setUpdatedDateTime(LocalDateTime.now());
            txn.setDeleted(false);

            hSession.save(txn);
            return null;
        });

        // Clear the cart from session
        session.removeAttribute("cart");

        // Fetch the final bill and respond with full DTO
        GetBillDTO receipt = billService.getBillByID(billIDHolder[0]);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(receipt));
    }
}
