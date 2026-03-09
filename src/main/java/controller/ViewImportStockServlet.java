/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ImportStockDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.ImportStock;
import model.Suppliers;

/**
 *
 * @author HP
 */
@WebServlet(name="ViewImportStockServlet", urlPatterns={"/ImportStockHistory"})
public class ViewImportStockServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewImportStockServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewImportStockServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
        ImportStockDAO importStockDAO = new ImportStockDAO();


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nhận tham số filter
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String supplierIdRaw = request.getParameter("supplierId");

        Integer supplierId = null;
        try {
            if (supplierIdRaw != null && !supplierIdRaw.isEmpty()) {
                supplierId = Integer.parseInt(supplierIdRaw);
            }
        } catch (Exception e) {}

        // Lấy danh sách lịch sử lọc theo filter
        ArrayList<ImportStock> history = importStockDAO.getImportHistoryFiltered(from, to, supplierId);
        // Lấy danh sách supplier cho select filter
        ArrayList<Suppliers> suppliers = importStockDAO.getAllActiveSuppliers();

        request.setAttribute("importHistory", history);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute("supplierId", supplierId);

        request.getRequestDispatcher("/WEB-INF/View/staff/stockManagement/importStockList.jsp").forward(request, response);
    }


    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
