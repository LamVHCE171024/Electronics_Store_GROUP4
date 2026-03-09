/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pc
 */
@WebServlet(name = "CreateCategoryServlet", urlPatterns = {"/CreateCategory"})
public class CreateCategoryServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateCategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCategoryServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("/WEB-INF/View/admin/categoryManagement/createCategory/createCategory.jsp").forward(request, response);

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");

        CategoryDAO dao = new CategoryDAO();
        int categoryId = dao.addCategory(categoryName, description);  // Tráº£ vá» ID Category

        if (categoryId == -1) {
            request.setAttribute("createError", "1");
            request.getRequestDispatcher("/WEB-INF/View/admin/categoryManagement/createCategory/create.jsp").forward(request, response);
            return;
        }

        // Láº·p qua cÃ¡c nhÃ³m group Ä‘Æ°á»£c gá»­i lÃªn
        int i = 0;
        while (true) {
            String groupName = request.getParameter("groups[" + i + "][name]");
            if (groupName == null) {
                break;
            }

            int groupId = dao.addCategoryDetailsGroup(groupName, categoryId);  // Tráº£ vá» ID Group

            // Láº¥y táº¥t cáº£ cÃ¡c chi tiáº¿t thuá»™c group nÃ y
            String[] details = request.getParameterValues("groups[" + i + "][details][]");
            if (details != null) {
                for (String detail : details) {
                    dao.addCategoryDetails(categoryId, detail, groupId);
                }
            }
            i++;
        }

        // ThÃ nh cÃ´ng
        request.setAttribute("createSuccess", "1");
        request.getRequestDispatcher("/WEB-INF/View/admin/categoryManagement/createCategory/create.jsp").forward(request, response);

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

