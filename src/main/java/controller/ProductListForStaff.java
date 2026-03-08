/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import dao.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Brand;
import model.Category;
import model.CategoryDetail;
import model.CategoryDetailGroup;
import model.Product;
import model.ProductDetail;
import model.Suppliers;

/**
 *
 * @author USER
 */
@WebServlet(name = "ProductListForStaff", urlPatterns = {"/ProductListForStaff"})
public class ProductListForStaff extends HttpServlet {

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
            out.println("<title>Servlet ProductListForStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListForStaff at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        if (action.equalsIgnoreCase("list")) {
            List<Product> products = dao.getProductList();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/View/staff/productManagement/productList.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("search")) {
            String keyword = request.getParameter("keyword");
            List<Product> products;
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = dao.searchProductByName(keyword);
            } else {
                products = dao.getProductList();
            }
            request.setAttribute("products", products);
            if (products.isEmpty()) {
                request.setAttribute("message", "No product found.");
            }
            request.getRequestDispatcher("/WEB-INF/View/staff/productManagement/productList.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("detail")) {
            String idRaw = request.getParameter("id");
            try {
                int id = Integer.parseInt(idRaw);

                if (id != -1) {
                    ProductDAO proDAO = new ProductDAO();
                    CategoryDAO cateDAO = new CategoryDAO();
                    SupplierDAO supDAO = new SupplierDAO();
                    BrandDAO brandDAO = new BrandDAO();

                    Product product = proDAO.getProductById(id);
                    List<ProductDetail> productDetailList = proDAO.getProductDetailById(id);
                    List<CategoryDetailGroup> categporyGroupList = cateDAO.getCategoryDetailGroupById(product.getCategoryId());
                    List<CategoryDetail> categporyDetailList = cateDAO.getCategoryDetailById(product.getCategoryId());
                    List<Suppliers> supList = supDAO.getAllSuppliers();
                    List<Brand> brandList = brandDAO.getAllBrand();

                    List<Category> categoryList = cateDAO.getAllCategory();

                    request.setAttribute("product", product);
                    request.setAttribute("productDetailList", productDetailList);
                    request.setAttribute("categoryGroupList", categporyGroupList);
                    request.setAttribute("categoryDetailList", categporyDetailList);
                    request.setAttribute("productId", id);
                    request.setAttribute("supList", supList);
                    request.setAttribute("categoryList", categoryList);
                    request.setAttribute("brandList", brandList);
                    request.getRequestDispatcher("/WEB-INF/View/staff/productManagement/updateProduct/updateInfo/update.jsp").forward(request, response);

                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
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
    }
}
