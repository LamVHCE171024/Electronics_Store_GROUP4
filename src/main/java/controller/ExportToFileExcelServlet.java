package controller;

import dao.ImportStockDAO;
import dao.ImportStockDetailDAO;
import model.ImportStock;
import model.ImportStockDetail;
import model.Product;
import model.Suppliers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/ExportToFileExcelServlet")
public class ExportToFileExcelServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ImportStockDetailReport.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ImportStockDetails");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Lấy dữ liệu
        ImportStockDAO stockDAO = new ImportStockDAO();
        ImportStockDetailDAO detailDAO = new ImportStockDetailDAO();
        List<ImportStock> importStocks = stockDAO.getAllImportStocks();
        Map<Integer, ImportStock> importStockMap = new HashMap<>();
        List<ImportStockDetail> allDetails = new ArrayList<>();
        for (ImportStock imp : importStocks) {
            importStockMap.put(imp.getIoid(), imp);
            allDetails.addAll(detailDAO.getDetailsById(imp.getIoid()));
        }

        // Header
        int rowNo = 0;
        XSSFRow row = sheet.createRow(rowNo++);
        int cellNum = 0;

        XSSFCell cell = row.createCell(cellNum++);
        cell.setCellValue("No.");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Import ID");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Import Date");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Supplier Name");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Product ID");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Product Name");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Quantity");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Unit Price");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Total Price");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Quantity Left");

        cell = row.createCell(cellNum++);
        cell.setCellValue("Staff Name");

        // Data
        int stt = 1;
        for (ImportStockDetail detail : allDetails) {
            ImportStock imp = importStockMap.get(detail.getIoid());
            Product prod = detail.getProduct();
            Suppliers sup = (imp != null) ? imp.getSupplier() : null;

            cellNum = 0;
            row = sheet.createRow(rowNo++);

            cell = row.createCell(cellNum++);
            cell.setCellValue(stt++);

            cell = row.createCell(cellNum++);
            cell.setCellValue(detail.getIoid());

            cell = row.createCell(cellNum++);
            cell.setCellValue(imp != null && imp.getImportDate() != null ? sdf.format(imp.getImportDate()) : "");

            cell = row.createCell(cellNum++);
            cell.setCellValue(sup != null ? sup.getName() : "");

            cell = row.createCell(cellNum++);
            cell.setCellValue(prod != null ? String.valueOf(prod.getProductId()) : "");

            cell = row.createCell(cellNum++);
            cell.setCellValue(prod != null ? prod.getProductName() : "");

            cell = row.createCell(cellNum++);
            cell.setCellValue(detail.getQuantity());

            cell = row.createCell(cellNum++);
            cell.setCellValue(detail.getUnitPrice());

            cell = row.createCell(cellNum++);
            cell.setCellValue(detail.getUnitPrice() * detail.getQuantity());

            cell = row.createCell(cellNum++);
            cell.setCellValue(detail.getQuantityLeft());

            cell = row.createCell(cellNum++);
            cell.setCellValue(imp != null ? imp.getFullName() : "");
        }

        for (int i = 0; i < 11; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
