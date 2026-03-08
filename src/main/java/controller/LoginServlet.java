/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CategoryDAO;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Category;
import model.Customer;

/**
 *
 * @author pc
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        List<Category> categoryList = categoryDAO.getAllCategory(); // hoặc getAllCategory()
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("WEB-INF/View/account/login.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        AccountDAO dao = new AccountDAO();
        HttpSession session = request.getSession();
        Account acc = dao.verifyMD5(email, pass);
        if (dao.checkEmailExisted(email) == false) {
            request.setAttribute("err", "<p style='color:yellow'>The account you entered is not registered. Please sign up first.</p>");
            request.getRequestDispatcher("WEB-INF/View/account/login.jsp").forward(request, response);
        } else if (acc == null || acc.getAccountID() == -1) {
            request.setAttribute("err", "<p style='color:red'>Email or password invalid</p>");
            request.getRequestDispatcher("WEB-INF/View/account/login.jsp").forward(request, response);
        } else if (acc.isIsActive() == false) {
            request.setAttribute("err", "<p style='color:red'>Your account is blocked</p>");
            request.getRequestDispatcher("WEB-INF/View/account/login.jsp").forward(request, response);
        } else if (acc.getRoleID() != 3) {
            request.setAttribute("err", "<p style='color:red'>You are not allowed to login with this role</p>");
            request.getRequestDispatcher("WEB-INF/View/account/login.jsp").forward(request, response);
        } else {
            CustomerDAO customerDao = new CustomerDAO();
            Customer cus = customerDao.getCustomerByAccountId(acc.getAccountID());
            session.setAttribute("cus", cus);
            session.setAttribute("accountId", acc.getAccountID());
            session.setAttribute("role", acc.getRoleID());

            session.setAttribute("user", acc);
            response.sendRedirect("Home");
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
