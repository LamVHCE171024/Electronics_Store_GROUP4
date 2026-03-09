/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import utils.EmailService;
import utils.OTPManager;

/**
 *
 * @author pc
 */
@WebServlet(name = "VerifyOTPServlet", urlPatterns = {"/Verify"})
public class VerifyOTPServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerifyOTPServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyOTPServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/View/account/verify.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO dao = new AccountDAO();
        int enteredOtp;

        try {
            enteredOtp = Integer.parseInt(request.getParameter("otp"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid OTP format.");
            request.getRequestDispatcher("WEB-INF/View/account/verify.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        OTPManager otpManager = (OTPManager) session.getAttribute("otpManager");
        String otpPurpose = (String) session.getAttribute("otpPurpose");

        String email;
        String password = null;

        // Xác định email và password theo mục đích OTP
        if ("register".equals(otpPurpose)) {
            email = (String) session.getAttribute("tempEmail");
            password = (String) session.getAttribute("tempPassword");
        } else if ("forgot".equals(otpPurpose)) {
            email = (String) session.getAttribute("resetEmail");
        } else {
            request.setAttribute("error", "Invalid OTP context.");
            request.getRequestDispatcher("WEB-INF/View/account/verify.jsp").forward(request, response);
            return;
        }

        // Kiểm tra OTP
        if (otpManager == null || otpManager.isExpired()) {
            request.setAttribute("error", "Your OTP has expired. Please try again.");
            request.getRequestDispatcher("WEB-INF/View/account/verify.jsp").forward(request, response);
            return;
        }

        if (EmailService.verifyOTP(email, enteredOtp)) {
            request.setAttribute("error", "Incorrect OTP.");
            request.getRequestDispatcher("WEB-INF/View/account/verify.jsp").forward(request, response);
            return;
        }

        // ✅ In ra để debug
        System.out.println("🔍 OTP người dùng nhập: " + enteredOtp);
        System.out.println("✅ OTP hệ thống lưu: " + otpManager.getOtpCode());

        // Đăng ký
        if ("register".equals(otpPurpose)) {
            String passwordHash = dao.hashMD5(password);
            String fullName = (String) session.getAttribute("tempFullName");
            String phone = (String) session.getAttribute("tempPhone");

            // ✅ Gọi hàm mới có thêm khách hàng luôn
            boolean success = dao.addNewAccount(email, passwordHash, fullName, phone);

            if (success) {
                session.removeAttribute("otpManager");
                session.removeAttribute("tempEmail");
                session.removeAttribute("tempPassword");
                session.removeAttribute("tempPhone");
                session.removeAttribute("tempFullName");
                session.removeAttribute("otpPurpose");

                EmailService.sendSuccessEmail(email);
                response.sendRedirect("Login");
            } else {
                request.setAttribute("error", "Account creation failed.");
                request.getRequestDispatcher("WEB-INF/View/account/verify.jsp").forward(request, response);
            }

        } else if ("forgot".equals(otpPurpose)) {
            // Chuyển đến trang đặt lại mật khẩu
            session.setAttribute("resetEmail", email);
            session.removeAttribute("otpManager");
            session.removeAttribute("otpPurpose");
            session.setAttribute("otpVerified", true);
            response.sendRedirect("ResetPassword");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
