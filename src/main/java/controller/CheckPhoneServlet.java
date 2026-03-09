package controller;

import dao.StaffDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/CheckPhoneServlet")
public class CheckPhoneServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String phone = request.getParameter("phone");

        
        StaffDAO dao = new StaffDAO();
        boolean exists = dao.isPhoneExists(phone);

        
        response.setContentType("text/plain");
        response.getWriter().write(exists ? "EXISTS" : "AVAILABLE");
    }
}
