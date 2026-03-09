package controller;

import dao.StaffDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Láº¥y email tá»« parameter
        String email = request.getParameter("email");

        // Kiá»ƒm tra email Ä‘Ã£ tá»“n táº¡i chÆ°a
        StaffDAO dao = new StaffDAO();
        boolean exists = dao.isEmailExists(email);

        // Tráº£ vá» káº¿t quáº£ dáº¡ng text
        response.setContentType("text/plain");
        response.getWriter().write(exists ? "EXISTS" : "AVAILABLE");
    }
}

