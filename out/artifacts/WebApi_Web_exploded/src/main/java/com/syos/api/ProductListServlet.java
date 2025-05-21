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
import java.util.List;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/api/products"})
public class ProductListServlet extends HttpServlet {

    private final IWebShopInventoryService inventoryService = new WebShopInventoryService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<WebShopInventoryDTO> products = inventoryService.getAllItems();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = gson.toJson(products);
        response.getWriter().write(json);
    }
}
