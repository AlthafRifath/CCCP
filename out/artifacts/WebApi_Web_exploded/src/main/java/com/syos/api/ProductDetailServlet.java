package com.syos.api;

import com.google.gson.Gson;
import main.java.com.syos.dto.WebShopInventoryDTO;
import main.java.com.syos.service.WebShopInventoryService;
import main.java.com.syos.service.interfaces.IWebShopInventoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/api/product"})
public class ProductDetailServlet extends HttpServlet {

    private final IWebShopInventoryService inventoryService = new WebShopInventoryService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemCode = request.getParameter("itemCode");
        String batchCode = request.getParameter("batchCode");

        if (itemCode == null || batchCode == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing itemCode or batchCode");
            return;
        }

        Optional<WebShopInventoryDTO> result = inventoryService.getAllItems().stream()
                .filter(p -> p.getItemCode().equals(itemCode) && p.getBatchCode().equals(batchCode))
                .findFirst();

        if (result.isPresent()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(result.get()));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Item not found");
        }
    }
}
