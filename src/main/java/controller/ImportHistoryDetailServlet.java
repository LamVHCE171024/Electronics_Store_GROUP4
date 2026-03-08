package controller;

import dao.ImportStockDAO;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ImportStock;
import model.ImportStockDetail;

@WebServlet(name = "ImportHistoryDetailServlet", urlPatterns = {"/ImportHistoryDetail"})
public class ImportHistoryDetailServlet extends HttpServlet {
    ImportStockDAO importStockDAO = new ImportStockDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rawId = request.getParameter("id");
        int importId = -1;
        try {
            importId = Integer.parseInt(rawId);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid import ID");
            request.getRequestDispatcher("/WEB-INF/View/staff/stockManagement/importHistoryDetail.jsp").forward(request, response);
            return;
        }
        ImportStock importStock = importStockDAO.getImportStockDetailsByID(importId);

        if (importStock == null) {
            request.setAttribute("error", "Import stock not found!");
            request.getRequestDispatcher("/WEB-INF/View/staff/stockManagement/importHistoryDetail.jsp").forward(request, response);
            return;
        }
        ArrayList<ImportStockDetail> details = (ArrayList<ImportStockDetail>) importStock.getImportStockDetails();
        request.setAttribute("importStock", importStock);
        request.setAttribute("details", details);

        request.getRequestDispatcher("/WEB-INF/View/staff/stockManagement/importHistoryDetail.jsp").forward(request, response);
    }
}
