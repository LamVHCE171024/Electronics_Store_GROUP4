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

/**
 *
 * @author pc
 */
@WebServlet(name="ChangePasswordStaffServlet", urlPatterns={"/ChangePasswordStaff"})
public class ChangePasswordStaffServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePasswordStaffServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordStaffServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       request.getRequestDispatcher("WEB-INF/View/admin/staffManagement/change-password-staff.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer staffId = (Integer) session.getAttribute("accountId"); // lấy từ session

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String passwordPattern = "^.{9,}$"; // ít nhất 9 ký tự

        // Validate
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match.");
            request.getRequestDispatcher("WEB-INF/View/admin/staffManagement/change-password-staff.jsp")
                   .forward(request, response);
            return;
        }

        if (!newPassword.matches(passwordPattern)) {
            request.setAttribute("error", "Password must be at least 9 characters long.");
            request.getRequestDispatcher("WEB-INF/View/admin/staffManagement/change-password-staff.jsp")
                   .forward(request, response);
            return;
        }

        AccountDAO dao = new AccountDAO();
        boolean success = dao.adminResetPassword(staffId, newPassword);

        if (success) {
            request.setAttribute("success", "Password changed successfully!");
        } else {
            request.setAttribute("error", "Password update failed.");
        }

        request.getRequestDispatcher("WEB-INF/View/admin/staffManagement/change-password-staff.jsp")
               .forward(request, response);
    }


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
