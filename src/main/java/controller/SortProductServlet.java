/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import model.Brand;
import model.Category;
import model.Product;

/**
 *
 * @author HP - Gia Khiêm
 */
@WebServlet(name = "SortProductServlet", urlPatterns = {"/SortProduct"})
public class SortProductServlet extends HttpServlet {

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
            out.println("<title>Servlet SortProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SortProductServlet at " + request.getContextPath() + "</h1>");
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
        ProductDAO proDao = new ProductDAO();
        BrandDAO brandDao = new BrandDAO();

        String sort = request.getParameter("sort");

        List<Product> productList = null;
        List<Brand> brandList = null;

        String categoryIdStr = null;
        int brandIdOld = -1;
        int categoryId = -1;
        int brandId = -1;

        BigDecimal minPrice = BigDecimal.ZERO;
        BigDecimal maxPrice = null;

        String brandCategory = request.getParameter("brandcategory");
        
        if (brandCategory != null && brandCategory.contains("-")) {
            String[] parts = brandCategory.split("-");
            
            if (parts.length >= 2) {
                brandId = Integer.parseInt(parts[0]);
                categoryId = Integer.parseInt(parts[1]);
            }
            
            if (parts.length == 3) {
                brandIdOld = Integer.parseInt(parts[2]);
            }

        }

        String priceRangeAndCategory = request.getParameter("priceRangeCategory");
        String priceRange = null;
        if (priceRangeAndCategory != null && priceRangeAndCategory.contains("-")) {
            String[] priceRangeAndCategoryArr = priceRangeAndCategory.split("-");
            
            if (priceRangeAndCategoryArr.length == 2) {
                priceRange = priceRangeAndCategoryArr[0];
                categoryId = Integer.parseInt(priceRangeAndCategoryArr[1]);
            }
        }
        
        if (categoryId != -1) {
            brandList = brandDao.getBrandByCategoryId(categoryId);
        }
            
        if (priceRange != null) {

            switch (priceRange) {
                case "under7":
                    minPrice = new BigDecimal("100000");
                    maxPrice = new BigDecimal("7000000");
                    break;
                case "7to9":
                    minPrice = new BigDecimal("7000000");
                    maxPrice = new BigDecimal("9000000");
                    break;
                case "9to12":
                    minPrice = new BigDecimal("9000000");
                    maxPrice = new BigDecimal("12000000");
                    break;
                case "12to15":
                    minPrice = new BigDecimal("12000000");
                    maxPrice = new BigDecimal("15000000");
                    break;
                case "15to20":
                    minPrice = new BigDecimal("15000000");
                    maxPrice = new BigDecimal("20000000");
                    break;
                case "above20":
                    minPrice = new BigDecimal("20000000");
                    maxPrice = new BigDecimal("200000000");
                    break;
            }
        }
        if (brandId != -1 && priceRange != null) {
            productList = proDao.getProductByBrandAndPrice(brandId, minPrice, maxPrice);
        } else if (brandId != -1 && priceRange == null) {
            productList = proDao.getProductByBrand(brandId);
        } else if (brandId == -1 && priceRange != null) {
            productList = proDao.getProductByCategoryAndPrice(categoryId, minPrice, maxPrice);
        } else {
            productList = proDao.getProductByCategory(categoryId);
        }
        
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.getAllCategory(); // hoặc getAllCategory()
        request.setAttribute("categoryList", categoryList);
        
        request.setAttribute("productList", productList);
        request.setAttribute("brandList", brandList);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("brandId", brandId);
        request.setAttribute("brandIdOld", brandIdOld);
        request.getRequestDispatcher("/WEB-INF/View/customer/productManagement/sortProduct/sortProduct.jsp").forward(request, response);
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
