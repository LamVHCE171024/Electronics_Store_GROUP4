package controller;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dao.OrderDAO;
import dao.OrderDetailDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Account;
import model.Customer;

import model.Order;
import model.OrderDetail;

/**
 *
 * @author VinhNTCE181630
 */
@WebServlet(name = "CustomerOrderDetailServlet", urlPatterns = {"/CustomerOrderDetail"})
public class CustomerOrderDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet CustomerOrderDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerOrderDetailServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        OrderDAO odao = new OrderDAO();
        OrderDetailDAO detailDAO = new OrderDetailDAO();
        Account acc = (Account) session.getAttribute("user");
        Customer cus = (Customer) session.getAttribute("cus");
        if (acc == null || cus == null) {
            response.sendRedirect("Login");
            return;
        }
        Order order = odao.getOrderByID(orderID + "");
        List<OrderDetail> details = detailDAO.getOrderDetailsByOrderID(orderID);
        List<Order> orders = odao.getOrdersByCustomerID(cus.getId());
        request.setAttribute("data", order);
        request.setAttribute("orderList", orders);
        request.setAttribute("dataDetail", details);
        request.getRequestDispatcher("/WEB-INF/View/customer/orderManagement/viewOrderDetail.jsp").forward(request, response);
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
        processRequest(request, response);
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
