package com.syos.api;

import com.syos.util.TransactionManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("{\"message\": \"Hello from test\"}");
        out.flush();

        try {
            String responseJson = TransactionManager.execute(session -> {
                System.out.println("Session " + session.hashCode() + " used by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate DB delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "{\"message\": \"Processed by thread " + Thread.currentThread().getName() + "\"}";
            });

            out.print(responseJson);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}
