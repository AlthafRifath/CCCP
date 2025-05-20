package com.syos.api;

import com.google.gson.Gson;
import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.CustomerSignupRequest;
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

@WebServlet(name = "CustomerSignupServlet", urlPatterns = {"/api/customer/signup"})
public class CustomerSignupServlet extends HttpServlet {

    private final ICustomerAuthService customerAuthService = new CustomerAuthService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        /// Servlet thread log
        System.out.println("[SignupServlet] Handling request on thread: " + Thread.currentThread().getName());

        try (BufferedReader reader = req.getReader(); PrintWriter out = resp.getWriter()) {
            CustomerSignupRequest signupRequest = gson.fromJson(reader, CustomerSignupRequest.class);
            LoginDTO result = customerAuthService.register(signupRequest);

            if (result.isSuccess()) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            out.print(gson.toJson(result));
            out.flush();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Something went wrong: " + e.getMessage() + "\"}");
        }
    }
}
