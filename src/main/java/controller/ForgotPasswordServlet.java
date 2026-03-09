/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.EmailService;
import utils.OTPManager;

/**
 *
 * @author pc
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/ForgotPassword"})
public class ForgotPasswordServlet extends HttpServlet {

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
            out.println("<title>Servlet ForgotPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("WEB-INF/View/account/forgot-password.jsp").forward(request, response);
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
        String email = request.getParameter("email").trim();
        HttpSession session = request.getSession();
        AccountDAO dao = new AccountDAO();

        // Kiá»ƒm tra email cÃ³ tá»“n táº¡i khÃ´ng
        if (!dao.checkEmailExisted(email)) {
            request.setAttribute("error", "Email does not exist in the system.");
            request.getRequestDispatcher("WEB-INF/View/account/forgot-password.jsp").forward(request, response);
            return;
        }

        // Táº¡o OTP má»›i
        int otpCode = EmailService.generateVerificationCode();
        OTPManager otpManager = new OTPManager(otpCode, 5); // háº¿t háº¡n sau 5 phÃºt

        // Gá»­i OTP qua email
        boolean emailSent = EmailService.sendOTPEmail(email, otpCode, "RESET_PASSWORD");
        if (!emailSent) {
            request.setAttribute("error", "Failed to send OTP email. Please try again later.");
            request.getRequestDispatcher("WEB-INF/View/account/forgot-password.jsp").forward(request, response);
            return;
        }

        // LÆ°u OTP vÃ  má»¥c Ä‘Ã­ch vÃ o session
        session.setAttribute("otpManager", otpManager);
        session.setAttribute("otpPurpose", "forgot"); // âš ï¸ ÄÃºng lÃ  'forgot' Ä‘á»ƒ Verify xá»­ lÃ½ Ä‘Ãºng!
        session.setAttribute("resetEmail", email);

        // Äiá»u hÆ°á»›ng Ä‘áº¿n trang xÃ¡c minh OTP
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

