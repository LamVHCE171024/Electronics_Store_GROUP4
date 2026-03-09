/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Brand;
import model.Category;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Account;

/**
 *
 * @author USER
 */
@WebServlet(name = "GetTargetOptionsServlet", urlPatterns = {"/GetTargetOptionsServlet"})
public class GetTargetOptionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");
        Account user = (Account) session.getAttribute("user");
        if (accountId == null) {
            response.sendRedirect("LoginAdmin");
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        String targetType = request.getParameter("targetType");
        StringBuilder html = new StringBuilder();
        html.append("<option value=\"\">-- Chọn --</option>");

        try (PrintWriter out = response.getWriter()) {
            if ("BRAND".equals(targetType)) {
                BrandDAO brandDAO = new BrandDAO();
                List<Brand> brands = brandDAO.getAllBrand();
                for (Brand brand : brands) {
                    html.append("<option value=\"").append(brand.getBrandId()).append("\">")
                            .append(brand.getBrandName()).append("</option>");
                }
            } else if ("CATEGORY".equals(targetType)) {
                CategoryDAO categoryDAO = new CategoryDAO();
                List<Category> categories = categoryDAO.getAllCategory();
                for (Category category : categories) {
                    html.append("<option value=\"").append(category.getCategoryId()).append("\">")
                            .append(category.getCategoryName()).append("</option>");
                }
            } else if ("PRODUCT".equals(targetType)) {
                ProductDAO productDAO = new ProductDAO();
                List<Product> products = productDAO.getAllProduct();
                for (Product product : products) {
                    html.append("<option value=\"").append(product.getProductId()).append("\">")
                            .append(product.getProductName()).append("</option>");
                }
            }
            out.print(html.toString());
        }
    }
}