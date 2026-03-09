/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.FeedbackDAO;
import dao.ProductRatingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import model.Account;
import model.Customer;

/**
 *
 * @author VinhNTCE181630
 */
@WebServlet(name = "WriteFeedbackServlet", urlPatterns = {"/WriteFeedback"})
public class WriteFeedbackServlet extends HttpServlet {

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
        try {
            // Lấy các tham số từ form
            String customerIDStr = request.getParameter("customerID");
            String productIDStr = request.getParameter("productID");
            String orderIDStr = request.getParameter("orderID");
            String starStr = request.getParameter("star");
            String comment = request.getParameter("comment");

            // Kiểm tra null và rỗng trước khi parse
            if (customerIDStr == null || customerIDStr.trim().isEmpty()) {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderIDStr + "&error=feedback");
                return;
            }

            if (productIDStr == null || productIDStr.trim().isEmpty()) {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderIDStr + "&error=feedback");
                return;
            }

            if (orderIDStr == null || orderIDStr.trim().isEmpty()) {
                response.sendRedirect("CustomerOrderDetail?error=feedback");
                return;
            }

            if (starStr == null || starStr.trim().isEmpty()) {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderIDStr + "&error=feedback");
                return;
            }

            if (comment == null || comment.trim().isEmpty()) {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderIDStr + "&error=feedback");
                return;
            }

            // Parse các giá trị
            int customerID = Integer.parseInt(customerIDStr.trim());
            int productID = Integer.parseInt(productIDStr.trim());
            int orderID = Integer.parseInt(orderIDStr.trim());
            int star = Integer.parseInt(starStr.trim());

            // Thử dùng ProductRatingDAO thay vì FeedbackDAO
            ProductRatingDAO dao = new ProductRatingDAO();
            boolean alreadyRated = dao.hasRatedProduct(customerID, productID, orderID);
            if (alreadyRated) {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderID + "&error=alreadyRated");
                return;
            }
            int result = dao.addProductRating(customerID, productID, orderID, star, comment.trim());
            if (result > 0) {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderID + "&success=feedback");
            } else {
                response.sendRedirect("CustomerOrderDetail?orderID=" + orderID + "&error=feedback");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("CustomerOrderDetail?error=feedback");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerOrderDetail?error=feedback");
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
