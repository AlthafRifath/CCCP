package com.syos.api;

import com.google.gson.Gson;
import main.java.com.syos.data.dao.ItemDAO;
import main.java.com.syos.data.dao.WebShopInventoryDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.data.model.WebShopInventory;
import main.java.com.syos.model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "AddToCartServlet", urlPatterns = {"/api/cart/add"})
public class AddToCartServlet extends HttpServlet {

    private final ItemDAO itemDAO = new ItemDAO();
    private final WebShopInventoryDAO inventoryDAO = new WebShopInventoryDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read raw JSON Body
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        // Parse JSON
        Map<String, Object> jsonBody = gson.fromJson(sb.toString(), Map.class);
        String itemCode = (String) jsonBody.get("itemCode");
        String batchCode = (String) jsonBody.get("batchCode");
        Double quantityDouble = jsonBody.get("quantity") instanceof Double ? (Double) jsonBody.get("quantity") : null;

        if (itemCode == null || batchCode == null || quantityDouble == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing or invalid JSON: itemCode, batchCode, quantity are required.");
            return;
        }

        int quantity = quantityDouble.intValue();
        if (quantity <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Quantity must be a positive integer.");
            return;
        }

        // Fetch item info
        Optional<Item> itemOpt = itemDAO.findByItemCodeAndBatchCode(itemCode, batchCode);
        Optional<WebShopInventory> inventoryOpt = inventoryDAO.findById(101, itemCode, batchCode); // WebShopID = 101

        if (itemOpt.isEmpty() || inventoryOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Item not found in inventory.");
            return;
        }

        Item item = itemOpt.get();
        WebShopInventory inventory = inventoryOpt.get();

        String key = itemCode + ":" + batchCode;
        HttpSession session = request.getSession();
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        if (cart.containsKey(key)) {
            CartItem existing = cart.get(key);
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(
                    itemCode,
                    batchCode,
                    item.getItemName(),
                    item.getPrice(),
                    quantity,
                    inventory.getImageUrl()
            );
            cart.put(key, newItem);
        }

        session.setAttribute("cart", cart);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Item added to cart.");
        result.put("cartSize", cart.size());

        response.getWriter().write(gson.toJson(result));
    }
}
