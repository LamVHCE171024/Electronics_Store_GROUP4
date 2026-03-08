/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dao.CategoryDAO;
import dao.ProductDAO;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.CategoryDetail;
import model.CategoryDetailGroup;
import model.Product;
import model.ProductDetail;

/**
 *
 * @author HP - Gia Khiêm
 */
@MultipartConfig

@WebServlet(name = "AdminAddProductDetailServlet", urlPatterns = {"/AdminAddProductDetail"})
public class AdminAddProductDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminAddProductDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminAddProductDetailServlet at " + request.getContextPath() + "</h1>");
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
        int productId = Integer.parseInt(request.getParameter("productId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Product product = proDAO.getProductById(productId);
        request.setAttribute("product", product);

        CategoryDAO cateDAO = new CategoryDAO();
        List<CategoryDetailGroup> cateDetailGroup = cateDAO.getCategoryDetailGroupById(categoryId);
        List<CategoryDetail> cateDetail = cateDAO.getCategoryDetailById(categoryId);
        request.setAttribute("categoryGroupList", cateDetailGroup);
        request.setAttribute("categoryDetailList", cateDetail);
        request.setAttribute("categoryId", categoryId);

        request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/addProduct/addProductDetail/adminAddProductDetail.jsp").forward(request, response);
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
        //        <====================================== Xử lý ảnh ===========================================>
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        boolean checkInsertValue = false;
        String categoryIdStr = request.getParameter("categoryId");

        String productIdStr = request.getParameter("productId");
        if (categoryIdStr != null && productIdStr != null) {
            int categoryId = Integer.parseInt(categoryIdStr);
            int productId = Integer.parseInt(productIdStr);
            ProductDAO proDAO = new ProductDAO();
            CategoryDAO cateDAO = new CategoryDAO();

            ProductDetail productDetail = proDAO.getOneProductDetailById(productId);
            Product product = proDAO.getProductByID(productId);
            Map<String, String> imageUrlMap = new LinkedHashMap<>();
//            imageUrlMap.put("fileMain", product.getImageUrl());
            imageUrlMap.put("file1", null);
            imageUrlMap.put("file2", null);
            imageUrlMap.put("file3", null);
            imageUrlMap.put("file4", null);

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

            boolean checkInsertImg = proDAO.insertImageProductDetail(imageUrlMap.get("file1"), imageUrlMap.get("file2"), imageUrlMap.get("file3"), imageUrlMap.get("file4"), productId);
            if (checkInsertImg) {
                List<CategoryDetail> categoryDetailList = cateDAO.getCategoryDetailById(categoryId);

                for (CategoryDetail cateDetail : categoryDetailList) {
                    String paramName = "attribute_" + cateDetail.getCategoryDetailID();

                    String value = request.getParameter(paramName);

                    if (value != null && !value.trim().isEmpty()) {
                        // Cập nhật lại DB
                        checkInsertValue = proDAO.insertProductDetail(productId, cateDetail.getCategoryDetailID(), value);
                    } 

                }
                if (checkInsertImg && checkInsertValue) {
                    response.sendRedirect("AdminCreateProduct?success=1");
                } else {
                    response.sendRedirect("AdminCreateProduct?error=1");

                }
            }
            //        <====================================== Xử lý ảnh ===========================================>
        }
//        response.sendRedirect("AdminCreateProduct?error=1");
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
