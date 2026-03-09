/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import dao.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Brand;
import model.Category;
import model.Product;
import model.Suppliers;

/**
 *
 * @author HP - Gia Khiêm
 */
@MultipartConfig
@WebServlet(name = "AdminAddProductServlet", urlPatterns = {"/AdminCreateProduct"})
public class AdminAddProductServlet extends HttpServlet {

    private Cloudinary cloudinary;

    @Override
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dgnyskpc3",
                "api_key", "398517693378845",
                "api_secret", "ho0bvkCgpHDBFoUW3M9bG8apAKk",
                "secure", true
        ));
    }

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
            out.println("<title>Servlet AdminAddProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminAddProductServlet at " + request.getContextPath() + "</h1>");
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
        CategoryDAO cateDAO = new CategoryDAO();
        List<Category> categoryList = cateDAO.getAllCategory();

        BrandDAO brandDAO = new BrandDAO();
        List<Brand> brandList = brandDAO.getAllBrand();

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("brandList", brandList);
        
        SupplierDAO supDAO = new SupplierDAO();
        List<Suppliers> supList = supDAO.getAllSuppliers();
        
        request.setAttribute("supList", supList);
        request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/addProduct/addProductInfo/adminAddProduct.jsp").forward(request, response);
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
        ProductDAO proDAO = new ProductDAO();

        String productName = request.getParameter("productName");
        
        String discription = request.getParameter("discription");
        
//        BigDecimal price = new BigDecimal(priceFormatted);

        int Category = Integer.parseInt(request.getParameter("category"));
        int Brand = Integer.parseInt(request.getParameter("brand"));
        int Suppliers = Integer.parseInt(request.getParameter("suppliers"));
        
        int stock = 0;

        boolean isFeatured = request.getParameter("isFeatured") != null;
        boolean isBestSeller = request.getParameter("isBestSeller") != null;
        boolean isNew = request.getParameter("isNew") != null;
        boolean isActive = request.getParameter("isActive") != null;

//        <====================================== Xử lý ảnh ===========================================>
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Map<String, String> imageUrlMap = new LinkedHashMap<>();
        imageUrlMap.put("fileMain", null);

        for (String key : imageUrlMap.keySet()) {
            Part part = request.getPart(key);
            if (part != null && part.getSize() > 0) {
                InputStream is = part.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, bytesRead);
                }
                byte[] fileBytes = buffer.toByteArray();

                Map uploadResult = cloudinary.uploader().upload(fileBytes,
                        ObjectUtils.asMap("resource_type", "auto"));

                String url = (String) uploadResult.get("secure_url");
                if (url != null) {
                    imageUrlMap.put(key, url); // ⚡ Update lại value
                } else {
                    imageUrlMap.put(key, "https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png");
                }
            } else {
                imageUrlMap.put(key, "https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png");
            }
        }

//        <====================================== Xử lý anh ===========================================>
        int productId = proDAO.insertProduct(productName, discription, Suppliers, stock, Category, Brand, isFeatured, isBestSeller, isNew, isActive, imageUrlMap.get("fileMain"));

        if (productId > 0) {
            response.sendRedirect("AdminAddProductDetail?productId=" + productId + "&categoryId=" + Category);
        } else {
            response.sendRedirect("AdminCreateProduct?productId=&error=1");
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
