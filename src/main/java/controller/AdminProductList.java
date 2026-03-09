/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author HP - Gia Khiêm
 */
@WebServlet(name = "StaffProductList", urlPatterns = {"/AdminProduct"})
public class AdminProductList extends HttpServlet {

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
            out.println("<title>Servlet StaffProductList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffProductList at " + request.getContextPath() + "</h1>");
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

        ProductDAO proDAO = new ProductDAO();
        String filter = request.getParameter("filter");
        List<Product> productList = new ArrayList<>();

        if (filter == null || filter.equals("All")) {
            productList = proDAO.getAllProduct();
        } else if (filter.equals("Featured")) {
            productList = proDAO.getProductIsFeatured();
        } else if (filter.equals("Bestseller")) {
            productList = proDAO.getProductIsBestSeller();
        } else if (filter.equals("New")) {
            productList = proDAO.getProductIsNew();
        } else if (filter.equals("Discount")) {
            productList = proDAO.getDiscountedProducts();
        } else if (filter.equals("Active")) {
            productList = proDAO.getAllProductActive();
        } else if (filter.equals("InActive")) {
            productList = proDAO.getAllProductInactive(); // fallback
        }

        String keyword = request.getParameter("keyword");
        if (keyword != null) {
            productList = proDAO.getProductByKeyword(keyword);
        }

        request.setAttribute("productList", productList);
        request.setAttribute("selectedFilter", filter); // để giữ lại lựa chọn sau reload
        request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/viewProductList/adminProductList.jsp").forward(request, response);

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
