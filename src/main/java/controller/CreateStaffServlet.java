package controller;

import dao.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Staff;

@WebServlet(name = "CreateStaffServlet", urlPatterns = {"/CreateStaffServlet"})
public class CreateStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/createStaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy dữ liệu từ form
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phoneNumber");
            String birthDateStr = request.getParameter("birthDate");
            String gender = request.getParameter("gender");
            String position = request.getParameter("position");
            String hiredDateStr = request.getParameter("hiredDate");

            // Chuyển đổi ngày tháng sang java.util.Date
            java.util.Date birthDate = null;
            java.util.Date hiredDate = null;
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                birthDate = java.sql.Date.valueOf(birthDateStr);
            }
            if (hiredDateStr != null && !hiredDateStr.isEmpty()) {
                hiredDate = java.sql.Date.valueOf(hiredDateStr);
            }

            // Tạo đối tượng Account
            Account account = new Account();
            account.setEmail(email);
            account.setPasswordHash(password); // Hash password nếu cần
//            account.setRoleID(roleID);
           

            // Tạo đối tượng Staff
            Staff staff = new Staff();
            staff.setFullName(fullName);
            staff.setPhone(phone);
            staff.setBirthDay(birthDate);
            staff.setGender(gender);
            staff.setPosition(position);
            staff.setHiredDate(hiredDate);

            // Gọi DAO để thêm vào DB
            StaffDAO dao = new StaffDAO();
            boolean success = dao.createStaffWithAccount(account, staff);

            if (success) {
                response.sendRedirect("StaffList?successcreate=1");
            } else {
                response.sendRedirect("StaffList?errorcreate=1");
            }
} catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/createStaff.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for creating new staff";
    }
}