/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ImportStockDAO;
import dao.InventoryStatisticDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import model.InventoryStatistic;

/**
 *
 * @author HP
 */
@WebServlet(name = "ImportStatisticServlet", urlPatterns = {"/ImportStatistic"})
public class ImportStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet ImportStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImportStatisticServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private Map<String, Integer> getTop5ShortName(Map<String, Integer> origin) {
        Map<String, Integer> result = new LinkedHashMap<>();
        Map<String, Integer> nameCount = new HashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : origin.entrySet()) {
            String name = entry.getKey();
            String shortName = name;
            if (name.length() > 22) {
                shortName = name.substring(0, 22) + "...";
            }
            if (result.containsKey(shortName)) {
                int suffix = nameCount.getOrDefault(shortName, 1) + 1;
                nameCount.put(shortName, suffix);
                shortName = shortName + " #" + suffix;
            } else {
                nameCount.put(shortName, 1);
            }
            result.put(shortName, entry.getValue());
            count++;
            if (count >= 5) {
                break;
            }
        }
        return result;
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
        try {
            String keyword = request.getParameter("search");
            InventoryStatisticDAO invDao = new InventoryStatisticDAO();
            ImportStockDAO dao = new ImportStockDAO();

            ArrayList<InventoryStatistic> inventoryList;
            if (keyword != null && !keyword.trim().isEmpty()) {
                inventoryList = invDao.searchInventory(keyword);
            } else {
                inventoryList = invDao.getAllProductStock();
            }

            Map<String, Integer> dailyImport = dao.getImportStocksCountByDate();
            Map<String, Integer> monthlyImport = dao.getImportStocksCountByMonth();
            Map<String, Integer> supplierImport = dao.getStocksBySupplier();
            Map<String, Integer> topProductImport = dao.getTopImportedProducts();

            Map<String, Integer> top5ProductImportShort = new LinkedHashMap<>();
            Map<String, String> top5ProductImportFull = new LinkedHashMap<>();
            int count = 0;
            for (Map.Entry<String, Integer> entry : topProductImport.entrySet()) {
                if (count >= 5) {
                    break;
                }
                String fullName = entry.getKey();
                String shortName = fullName;
                if (shortName.length() > 20) {
                    shortName = shortName.substring(0, 20) + "...";
                }
                int idx = 2;
                String check = shortName;
                while (top5ProductImportShort.containsKey(check)) {
                    check = shortName + " #" + idx++;
                }
                shortName = check;

                top5ProductImportShort.put(shortName, entry.getValue());
                top5ProductImportFull.put(shortName, fullName);
                count++;
            }

            Map<String, Integer> top5SupplierImport = getTop5ShortName(supplierImport);
            request.setAttribute("inventoryList", inventoryList);
            request.setAttribute("dailyImport", dailyImport);
            request.setAttribute("monthlyImport", monthlyImport);
            request.setAttribute("supplierImport", top5SupplierImport);
            request.setAttribute("topProductImportShort", top5ProductImportShort);
            request.setAttribute("topProductImportFull", top5ProductImportFull);

            request.getRequestDispatcher("/WEB-INF/View/staff/stockManagement/importStatistic.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching inventory statistics: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/View/staff/stockManagement/importStatistic.jsp").forward(request, response);
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
