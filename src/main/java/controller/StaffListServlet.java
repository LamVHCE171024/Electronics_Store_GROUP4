/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Staff;

/**
 *
 * @author pc
 */
@WebServlet(name = "StaffListServlet", urlPatterns = {"/StaffList"})
public class StaffListServlet extends HttpServlet {

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
            out.println("<title>Servlet StaffListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffListServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        StaffDAO dao = new StaffDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        if (action.equalsIgnoreCase("list")) {
            List<Staff> staff = dao.getStaffList();
            request.setAttribute("staff", staff);
            request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/staffList.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("search")) {
            String keyword = request.getParameter("keyword");
            List<Staff> list = dao.searchStaffByName(keyword);
            if (keyword != null && !keyword.trim().isEmpty()) {
                list = dao.searchStaffByName(keyword);
            } else {
                list = dao.getStaffList();
            }
            request.setAttribute("staff", list);
            if (list.isEmpty()) {
                request.setAttribute("message", "No staff found.");
            }
            request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/staffList.jsp").forward(request, response);
            return;
        }
        if (action.equalsIgnoreCase("detail")) {
            String idRaw = request.getParameter("id");

            int id = 0;
            try {
                id = Integer.parseInt(idRaw);
                Staff sta = dao.getStaffByID(id);
                int accountId = dao.getAccountIdByStaffId(id);
                session.setAttribute("accountId", accountId); // để ChangePassword lấy được
                request.setAttribute("data", sta);
                request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/view-staff-detail.jsp").forward(request, response);
                System.out.println("Session ID: " + session.getId());
                System.out.println("accountId: " + session.getAttribute("accountId"));
            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.print(e.getMessage());
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
    }// </editor-fold>

}
