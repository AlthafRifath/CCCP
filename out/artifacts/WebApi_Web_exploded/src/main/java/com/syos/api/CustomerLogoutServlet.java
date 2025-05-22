package com.syos.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CustomerLogoutServlet", urlPatterns = {"/api/customer/logout"})
public class CustomerLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"message\": \"Logged out successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"No session found to log out.\"}");
            }

            out.flush();
        }
    }
}
