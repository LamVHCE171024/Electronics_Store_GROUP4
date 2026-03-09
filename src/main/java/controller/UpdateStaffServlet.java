package controller;

import dao.StaffDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import model.Staff;

import java.io.IOException;

@WebServlet(name = "UpdateStaffServlet", urlPatterns = {"/UpdateStaffServlet"})
public class UpdateStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String staffIdStr = request.getParameter("id");
        if (staffIdStr != null) {
            int staffId = Integer.parseInt(staffIdStr);
            StaffDAO dao = new StaffDAO();
            Staff staff = dao.getStaffById(staffId);
            Account account = dao.getAccountByStaffId(staffId);

            request.setAttribute("staff", staff);
            request.setAttribute("account", account);
            request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/updateStaff.jsp").forward(request, response);
        } else {
            response.sendRedirect("StaffListServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy dữ liệu từ form
            int accountID = Integer.parseInt(request.getParameter("accountID"));
            int staffID = Integer.parseInt(request.getParameter("staffID"));

            String email = request.getParameter("email");
           

            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phoneNumber");
            String birthDateStr = request.getParameter("birthDate");
            String gender = request.getParameter("gender");
            String position = request.getParameter("position");
            String hiredDateStr = request.getParameter("hiredDate");

            // Chuyển ngày
            java.util.Date birthDate = (birthDateStr != null && !birthDateStr.isEmpty())
                    ? java.sql.Date.valueOf(birthDateStr) : null;
            java.util.Date hiredDate = (hiredDateStr != null && !hiredDateStr.isEmpty())
                    ? java.sql.Date.valueOf(hiredDateStr) : null;

            // Tạo đối tượng Account
            Account account = new Account();
            account.setAccountID(accountID);
            account.setEmail(email);
           

            // Tạo đối tượng Staff
            Staff staff = new Staff();
            staff.setStaffID(staffID);
            staff.setFullName(fullName);
            staff.setPhone(phone);
            staff.setBirthDay(birthDate);
            staff.setGender(gender);
            staff.setPosition(position);
            staff.setHiredDate(hiredDate);

            // Gọi DAO cập nhật
StaffDAO dao = new StaffDAO();
            boolean success = dao.updateStaffWithAccount(account, staff);

            if (success) {
                response.sendRedirect("StaffList?successedit=1");
            } else {
                response.sendRedirect("StaffList?erroredit=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/updateStaff.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating staff information";
    }

}