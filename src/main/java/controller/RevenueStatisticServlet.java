/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RevenueStatisticDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import model.RevenueStatistic;

/**
 *
 * @author HP
 */
@WebServlet(name = "RevenueStatisticServlet", urlPatterns = {"/RevenueStatistic"})
public class RevenueStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet RevenueStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RevenueStatisticServlet at " + request.getContextPath() + "</h1>");
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

        String type = request.getParameter("type");
        RevenueStatisticDAO dao = new RevenueStatisticDAO();
        ArrayList<RevenueStatistic> stats = new ArrayList<>();

        try {
            ArrayList<RevenueStatistic> dayStats = dao.getRevenueByDay();
            if (!dayStats.isEmpty()) {
                Date latestDate = new Date(dayStats.get(dayStats.size() - 1).getOrderDate().getTime());
                        System.out.println("Latest order date: " + latestDate);

                ArrayList<RevenueStatistic> dayCategoryData = dao.getRevenueByCategoryOnDay(latestDate);
                request.setAttribute("chartDayData", dayCategoryData);
                
            }

            ArrayList<RevenueStatistic> monthStats = dao.getRevenueByMonth();
            if (!monthStats.isEmpty()) {
                int latestMonth = monthStats.get(monthStats.size() - 1).getOrderMonth();
                int latestYear = monthStats.get(monthStats.size() - 1).getOrderYear();
                ArrayList<RevenueStatistic> monthCategoryData = dao.getRevenueByCategoryOnMonth(latestMonth, latestYear);
                request.setAttribute("chartMonthData", monthCategoryData);
            }

            if (type == null || type.equalsIgnoreCase("day")) {
                stats = dayStats; 
                request.setAttribute("statType", "day");

            } else if (type.equalsIgnoreCase("month")) {
                stats = monthStats;
                request.setAttribute("statType", "month");

            } else if (type.equalsIgnoreCase("category")) {
                String cid = request.getParameter("categoryId");
                int categoryId = cid != null ? Integer.parseInt(cid) : 1;
                stats = dao.getRevenueByCategory(categoryId);
                request.setAttribute("statType", "category");
                request.setAttribute("selectedCategoryId", categoryId);
            }

            request.setAttribute("revenueStatistics", stats);

        } catch (Exception e) {
            request.setAttribute("message", "Error fetching revenue data: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/View/admin/manageStatistics/revenueStatistic.jsp").forward(request, response);
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
