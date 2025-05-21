package com.syos.api;

import com.google.gson.Gson;
import main.java.com.syos.model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ViewCartServlet", urlPatterns = {"/api/cart"})
public class ViewCartServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Map<String, CartItem> cart = null;

        if (session != null) {
            cart = (Map<String, CartItem>) session.getAttribute("cart");
        }

        List<CartItem> cartItems = (cart != null) ? new ArrayList<>(cart.values()) : new ArrayList<>();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(cartItems));
    }
}
