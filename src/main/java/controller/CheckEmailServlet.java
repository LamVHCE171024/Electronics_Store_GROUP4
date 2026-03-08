package controller;

import dao.StaffDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy email từ parameter
        String email = request.getParameter("email");

        // Kiểm tra email đã tồn tại chưa
        StaffDAO dao = new StaffDAO();
        boolean exists = dao.isEmailExists(email);

        // Trả về kết quả dạng text
        response.setContentType("text/plain");
        response.getWriter().write(exists ? "EXISTS" : "AVAILABLE");
    }
}
