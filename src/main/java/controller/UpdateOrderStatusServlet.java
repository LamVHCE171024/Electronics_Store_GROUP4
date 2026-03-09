/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.OrderStatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Order;
import model.OrderDetail;
import model.OrderStatus;

/**
 *
 * @author VinhNTCE181630
 */
@WebServlet(name = "UpdateOrderStatusServlet", urlPatterns = {"/UpdateOrder"})
public class UpdateOrderStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrderStatus</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrderStatus at " + request.getContextPath() + "</h1>");
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
        String orderID = request.getParameter("orderID");
        OrderDAO oDAO = new OrderDAO();
        Order o = oDAO.getOrderByID(orderID);

        OrderDetailDAO odDAO = new OrderDetailDAO();
        List<OrderDetail> list = odDAO.getOrderDetail(orderID);
        if (o.getFullName() != "") {
            request.setAttribute("dataDetail", list);
            request.setAttribute("data", o);
            request.getRequestDispatcher("/WEB-INF/View/staff/orderManagement/orderDetailsView.jsp").forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/ViewOrderListServlet");
        }
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
        String status = request.getParameter("update");
        String orderID = request.getParameter("orderID");
        try {

            OrderDAO oDAO = new OrderDAO();

            if (status != null && orderID != null) {
                int count = oDAO.updateOrder(Integer.parseInt(orderID), Integer.parseInt(status));

                if (count > 0) {
                    // ✅ Update thành công → quay về danh sách và show thông báo
                    response.sendRedirect(request.getContextPath() + "/ViewOrderList?success=update");
                } else {
                    // ❌ Update thất bại → vẫn quay về danh sách nhưng hiện thông báo lỗi
                    response.sendRedirect(request.getContextPath() + "/ViewOrderList?error=1");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/ViewOrderList?error=1");
            }

        } catch (NumberFormatException e) {
            System.out.println(e);
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
