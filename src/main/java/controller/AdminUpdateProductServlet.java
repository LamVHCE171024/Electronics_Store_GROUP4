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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Brand;
import model.Category;
import model.CategoryDetail;
import model.CategoryDetailGroup;
import model.Product;
import model.ProductDetail;
import model.Suppliers;

/**
 *
 * @author HP - Gia Khiêm
 */
@MultipartConfig
@WebServlet(name = "AdminUpdateProductServlet", urlPatterns = {"/AdminUpdateProduct"})
public class AdminUpdateProductServlet extends HttpServlet {

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
            out.println("<title>Servlet StaffUpdateInfoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffUpdateInfoServlet at " + request.getContextPath() + "</h1>");
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

        String productIdString = request.getParameter("productId");

        int productId = ((productIdString != null) ? Integer.parseInt(productIdString) : -1);

        if (productId != -1) {

            ProductDAO proDao = new ProductDAO();
            CategoryDAO cateDao = new CategoryDAO();
            BrandDAO brandDao = new BrandDAO();

            List<Category> categoryList = cateDao.getAllCategory();
            List<Brand> brandList = brandDao.getAllBrand();
            Product product = proDao.getProductById(productId);

            List<ProductDetail> productDetailList = proDao.getProductDetailById(productId);
            List<CategoryDetailGroup> categoryGroupList = cateDao.getCategoryDetailGroupById(product.getCategoryId());
            List<CategoryDetail> categporyDetailList = cateDao.getCategoryDetailById(product.getCategoryId());

            request.setAttribute("categoryList", categoryList);
            request.setAttribute("brandList", brandList);
            request.setAttribute("product", product);
            request.setAttribute("productDetailList", productDetailList);
            request.setAttribute("categoryGroupList", categoryGroupList);
            request.setAttribute("categoryDetailList", categporyDetailList);
            request.setAttribute("productId", productId);

            SupplierDAO supDAO = new SupplierDAO();
            List<Suppliers> supList = supDAO.getAllSuppliers();

            request.setAttribute("supList", supList);
            request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/updateProduct/updateProduct.jsp").forward(request, response);
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

        ProductDAO proDAO = new ProductDAO();

        int id = Integer.parseInt(request.getParameter("id"));
        String productName = request.getParameter("productName");

        String priceFormatted = request.getParameter("price");
        priceFormatted = priceFormatted.replace(".", "") // bỏ dấu chấm ngăn cách hàng nghìn
                .replace("₫", "") // bỏ ký tự tiền
                .trim();
        BigDecimal price = new BigDecimal(priceFormatted);

        int Category = Integer.parseInt(request.getParameter("category"));
        int Brand = Integer.parseInt(request.getParameter("brand"));
        int supplier = Integer.parseInt(request.getParameter("suppliers"));

        boolean isFeatured = request.getParameter("isFeatured") != null;
        boolean isBestSeller = request.getParameter("isBestSeller") != null;
        boolean isNew = request.getParameter("isNew") != null;
        boolean isActive = request.getParameter("isActive") != null;

//        <====================================== Xử lý ảnh ===========================================>
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        ProductDetail productDetail = proDAO.getOneProductDetailById(id);
        Product product = proDAO.getProductByID(id);
        Map<String, String> imageUrlMap = new LinkedHashMap<>();
        imageUrlMap.put("fileMain", product.getImageUrl());
        imageUrlMap.put("file1", productDetail.getImageUrl1());
        imageUrlMap.put("file2", productDetail.getImageUrl2());
        imageUrlMap.put("file3", productDetail.getImageUrl3());
        imageUrlMap.put("file4", productDetail.getImageUrl4());

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
                }
            }
        }
        //        <====================================== Xử lý ảnh ===========================================>
        boolean res = proDAO.updateProductInfo(id, productName, price, supplier, Category, Brand, isFeatured, isBestSeller, isNew, isActive, imageUrlMap.get("fileMain"));

        //        <====================================== Xử lý thông tin ===========================================>
        List<ProductDetail> productDetailList = proDAO.getProductDetailById(id);

        for (ProductDetail proDetail : productDetailList) {
            String paramName = "attribute_" + proDetail.getCategoryDetailID();

            String value = request.getParameter(paramName);

            if (value != null && !value.trim().isEmpty()) {
                proDetail.setAttributeValue(value.trim());

                // Cập nhật lại DB
                res = proDAO.updateProductDetail(id, proDetail.getCategoryDetailID(), value, imageUrlMap.get("file1"), imageUrlMap.get("file2"), imageUrlMap.get("file3"), imageUrlMap.get("file4"), imageUrlMap.get("fileMain")); // bạn cần có hàm này trong DAO
            }

        }

        if (res) {
            response.sendRedirect("AdminUpdateProduct?productId=" + id + "&success=1");
        } else {
            response.sendRedirect("AdminUpdateProduct?productId=" + id + "&error=1");
        }

        //        <====================================== Xử lý thông tin ===========================================>
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
