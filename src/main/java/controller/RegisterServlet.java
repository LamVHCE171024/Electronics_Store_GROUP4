/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import model.Category;
import utils.EmailService;
import utils.OTPManager;

/**
 *
 * @author pc
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/Register"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
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
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.getAllCategory(); // hoáº·c getAllCategory()
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("WEB-INF/View/account/register.jsp").forward(request, response);
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
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String passwordPattern = "^.{9,}$";
        if (!password.matches(passwordPattern)) {
             request.setAttribute("error", "Password must be at least 9 characters long.");
            request.setAttribute("phone", phone);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.getRequestDispatcher("WEB-INF/View/account/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password and Confirm Password do not match.");
            request.getRequestDispatcher("WEB-INF/View/account/register.jsp").forward(request, response);
            request.setAttribute("phone", phone);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            return;
        }

        AccountDAO dao = new AccountDAO();
        if (dao.checkEmailExisted(email)) {
            request.setAttribute("error", "This email is already registered.");
            request.setAttribute("phone", phone);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.getRequestDispatcher("WEB-INF/View/account/register.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        // Gá»­i OTP cÃ³ kiá»ƒm soÃ¡t sá»‘ láº§n vÃ  thá»i háº¡n
        int otpCode = EmailService.generateVerificationCode();
        boolean emailSent = EmailService.sendOTPEmail(email, otpCode, "REGISTER"); // Gá»­i Ä‘Ãºng mÃ£ vá»«a táº¡o
        OTPManager otpManager = new OTPManager(otpCode, 5);
        session.setAttribute("otpManager", otpManager);
        session.setAttribute("otpPurpose", "register");
        if (!emailSent) {
            request.setAttribute("error", "Failed to send OTP. You may have reached the resend limit (max 3 times).");
            request.getRequestDispatcher("WEB-INF/View/account/register.jsp").forward(request, response);
            return;
        }

        // LÆ°u táº¡m thÃ´ng tin ngÆ°á»i dÃ¹ng chá» xÃ¡c minh
        session.setAttribute("tempEmail", email);
        session.setAttribute("tempPassword", password);
        session.setAttribute("tempFullName", fullName);
        session.setAttribute("tempPhone", phone);

        response.sendRedirect("Verify");
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

