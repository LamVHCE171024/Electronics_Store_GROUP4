/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;

/**
 *
 * @author pc
 */
@WebServlet(name = "CustomerList", urlPatterns = {"/CustomerList"})
public class CustomerListServlet extends HttpServlet {

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
            out.println("<title>Servlet CustomerList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerList at " + request.getContextPath() + "</h1>");
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
        CustomerDAO dao = new CustomerDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        if (action.equalsIgnoreCase("list")) {

            List<Customer> users = dao.getCustomerList();
            request.setAttribute("userList", users);
            request.getRequestDispatcher("WEB-INF/View/staff/customerManagement/customerList.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("detail")) {
            String idRaw = request.getParameter("id");
            int id = 0;
            try {
                id = Integer.parseInt(idRaw);
                Customer cus = dao.getCustomerbyID(id);
                request.setAttribute("data", cus);
                request.getRequestDispatcher("WEB-INF/View/staff/customerManagement/view-customer-detail.jsp").forward(request, response);
            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.print(e.getMessage());
            }
        }
        if ("changeStatus".equalsIgnoreCase(action)) {
            String idRaw = request.getParameter("id");
            try {
                int id = Integer.parseInt(idRaw);
                boolean success = dao.updateStatus(id);
                if (success) {
                    response.sendRedirect("CustomerList");
                } else {
                    request.setAttribute("error", "Update failed.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid ID format.");
                List<Customer> users = dao.getCustomerList(); // load lại
                request.setAttribute("userList", users);
                request.getRequestDispatcher("WEB-INF/View/staff/customerManagement/customerList.jsp").forward(request, response);
            }

        }
        if (action.equalsIgnoreCase("search")) {
            String keyword = request.getParameter("keyword");
            List<Customer> list = dao.searchCustomerByName(keyword);
            if (keyword != null && !keyword.trim().isEmpty()) {
                list = dao.searchCustomerByName(keyword);
            } else {
                list = dao.getCustomerList();
            }
            request.setAttribute("userList", list);
            if (list.isEmpty()) {
                request.setAttribute("error", "No staff found.");
            }
            request.getRequestDispatcher("customerList.jsp").forward(request, response);
            return;
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
        CustomerDAO dao = new CustomerDAO();
        String action = request.getParameter("action");
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
