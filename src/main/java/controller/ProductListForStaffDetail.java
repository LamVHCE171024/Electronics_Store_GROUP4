/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.CategoryDetail;
import model.CategoryDetailGroup;
import model.Product;
import model.ProductDetail;

/**
 *
 * @author USER
 */
@WebServlet(name="ProductListForStaffDetail", urlPatterns={"/ProductListForStaffDetail"})
public class ProductListForStaffDetail extends HttpServlet {
   
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
            out.println("<title>Servlet ProductListForStaffDetail</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListForStaffDetail at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdString = request.getParameter("productId");
        int productId = (productIdString != null) ? Integer.parseInt(productIdString) : -1;

        if (productId != -1) {
            ProductDAO proDAO = new ProductDAO();
            CategoryDAO cateDAO = new CategoryDAO();

            Product product = proDAO.getProductById(productId);
            List<ProductDetail> productDetailList = proDAO.getProductDetailById(productId);
            List<CategoryDetailGroup> categporyGroupList = cateDAO.getCategoryDetailGroupById(product.getCategoryId());
            List<CategoryDetail> categporyDetailList = cateDAO.getCategoryDetailById(product.getCategoryId());
            
            request.setAttribute("product", product);
            request.setAttribute("productDetailList", productDetailList);
            request.setAttribute("categoryGroupList", categporyGroupList);
            request.setAttribute("categoryDetailList", categporyDetailList);
            request.setAttribute("productId", productId);

            request.getRequestDispatcher("/WEB-INF/View/staff/productManagement/viewProductDetail/adminProductDetail.jsp").forward(request, response);
        }
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
        processRequest(request, response);
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
