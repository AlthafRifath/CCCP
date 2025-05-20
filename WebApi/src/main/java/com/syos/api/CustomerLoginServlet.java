package com.syos.api;

import com.google.gson.Gson;
import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.CustomerLoginRequest;
import main.java.com.syos.service.CustomerAuthService;
import main.java.com.syos.service.interfaces.ICustomerAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CustomerLoginServlet", urlPatterns = {"/api/customer/login"})
public class CustomerLoginServlet extends HttpServlet {

    private final ICustomerAuthService customerAuthService = new CustomerAuthService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try (BufferedReader reader = req.getReader(); PrintWriter out = resp.getWriter()) {
            System.out.println("CustomerLoginServlet - Request handled by thread: " + Thread.currentThread().getName());

            CustomerLoginRequest loginRequest = gson.fromJson(reader, CustomerLoginRequest.class);

            LoginDTO result = customerAuthService.login(loginRequest);

            if (result.isSuccess()) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            out.print(gson.toJson(result));
            out.flush();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Login failed: " + e.getMessage() + "\"}");
        }
    }
}
