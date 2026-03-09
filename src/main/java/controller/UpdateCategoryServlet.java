/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.CategoryDetail;
import model.CategoryDetailGroup;

/**
 *
 * @author HP - Gia Khiêm
 */
@WebServlet(name = "UpdateCategoryServlet", urlPatterns = {"/UpdateCategory"})
public class UpdateCategoryServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateCategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCategoryServlet at " + request.getContextPath() + "</h1>");
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
        String cateId = request.getParameter("categoryId");
        int categoryId = (cateId != null) ? Integer.parseInt(cateId) : -1;
        if (categoryId != -1) {

            CategoryDAO categoryDAO = new CategoryDAO();

            List<CategoryDetailGroup> categoryDetaiGrouplList = categoryDAO.getCategoryDetailGroupById(categoryId);
            request.setAttribute("categoryDetaiGrouplList", categoryDetaiGrouplList);

            List<CategoryDetail> categoryDetailList = categoryDAO.getCategoryDetailById(categoryId);
            request.setAttribute("categoryDetailList", categoryDetailList);

            List<Category> categoryList = categoryDAO.getAllCategory(); // hoặc getAllCategory()
            request.setAttribute("categoryList", categoryList);

            Category category = categoryDAO.getCategoryById(categoryId);
            request.setAttribute("category", category);

            request.setAttribute("categoryId", categoryId);
            request.getRequestDispatcher("/WEB-INF/View/admin/categoryManagement/updateCategory/updateCategory.jsp").forward(request, response);

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
        CategoryDAO dao = new CategoryDAO();
        String rawCategoryId = request.getParameter("categoryId");
        int categoryId = -1;
        if (rawCategoryId != null && !rawCategoryId.isEmpty()) {
            categoryId = Integer.parseInt(rawCategoryId);

            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");
            dao.updateCategory(categoryId, categoryName, description);

            //        <== update category 
            String[] groupIds = request.getParameterValues("groupId");
            String[] groupNames = request.getParameterValues("groupName");
            boolean checkUpdateCategoryDetail = true;
            if (groupIds != null && groupNames != null && groupIds.length == groupNames.length) {
                for (int i = 0; i < groupIds.length; i++) {
                    int groupId = Integer.parseInt(groupIds[i]);
                    String groupName = groupNames[i];

                    // Gọi DAO để cập nhật tên nhóm
                    checkUpdateCategoryDetail &= dao.updateCategporyDetailGroup(groupId, groupName);
                }
            }
            //        <== update category 

            //        <== update category detail ==>
            String[] detailIds = request.getParameterValues("detailId");
            String[] detailNames = request.getParameterValues("detailName");
            boolean checkUpdateCategoryDetailGroup = true;
            if (detailIds != null && detailNames != null && detailIds.length == detailNames.length) {
                for (int i = 0; i < detailIds.length; i++) {
                    int detailId = Integer.parseInt(detailIds[i]);
                    String detailName = detailNames[i];

                    // Gọi DAO để update từng chi tiết
                    checkUpdateCategoryDetailGroup &= dao.updateCategporyDetail(detailId, detailName);
                }
            }
            //        <== update category detail ==>
            
            if (checkUpdateCategoryDetail == true && checkUpdateCategoryDetailGroup == true) {
                response.sendRedirect("UpdateCategory?categoryId=" + categoryId + "&success=1");
            } else {
                response.sendRedirect("UpdateCategory?categoryId=" + categoryId + "&error=1");
            }
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
