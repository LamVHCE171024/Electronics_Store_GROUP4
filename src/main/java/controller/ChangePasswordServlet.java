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
import model.Customer;

/**
 *
 * @author pc
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePassword"})
public class ChangePasswordServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("cus");

        if (user == null || customer == null) {

            response.sendRedirect("Login");
            return;
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
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("cus");
        if (user == null || customer == null) {
            response.sendRedirect("Login");
            return;
        }
        request.getRequestDispatcher("WEB-INF/View/customer/profile/change-password.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect("Login");
            return;
        }
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String passwordPattern = "^.{9,}$";

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match.");
            request.getRequestDispatcher("WEB-INF/View/customer/profile/change-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.matches(passwordPattern)) {
            request.setAttribute("error", "Password must be at least 9 characters long.");
            request.getRequestDispatcher("WEB-INF/View/customer/profile/change-password.jsp").forward(request, response);
            return;
        }

        AccountDAO dao = new AccountDAO();
        boolean success = dao.changePassword(accountId, oldPassword, newPassword);

        if (success) {
            request.setAttribute("success", "Password changed successfully!");
        } else {
            request.setAttribute("error", "Old password is incorrect or update failed.");
        }
        request.getRequestDispatcher("WEB-INF/View/customer/profile/change-password.jsp").forward(request, response);
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
