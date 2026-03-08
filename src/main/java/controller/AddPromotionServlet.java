package controller;

import dao.PromotionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/AddPromotionServlet")
public class AddPromotionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/setPromotion/setPromotion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String targetType = request.getParameter("targetType");
            String targetIdStr = request.getParameter("targetId");
            String discountStr = request.getParameter("discount");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String name = request.getParameter("name");

            // Validate targetType
            if (targetType == null || targetType.isEmpty() || !targetType.matches("BRAND|CATEGORY|PRODUCT")) {
                showErrorAlert(response, "Invalid target type! Please select a valid target type (Brand, Category, or Product).");
                return;
            }

            // Validate targetId
            int targetId;
            try {
                targetId = Integer.parseInt(targetIdStr);
                if (targetId <= 0) {
                    showErrorAlert(response, "Invalid target ID! Please select a valid target.");
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorAlert(response, "Invalid target ID! Please enter a valid number.");
                return;
            }

            // Validate discount
            int discount;
            try {
                discount = Integer.parseInt(discountStr);
                if (discount < 1 || discount > 100) {
                    showErrorAlert(response, "Discount percentage must be between 1 and 100!");
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorAlert(response, "Invalid discount percentage! Please enter a number between 1 and 100.");
                return;
            }

            // Validate dates
            Timestamp startDate, endDate;
            try {
                startDate = Timestamp.valueOf(startDateStr.replace("T", " ") + ":00");
                endDate = Timestamp.valueOf(endDateStr.replace("T", " ") + ":00");
                if (!endDate.after(startDate)) {
                    showErrorAlert(response, "End date must be after start date!");
                    return;
                }
            } catch (IllegalArgumentException e) {
                showErrorAlert(response, "Invalid date format! Please use the correct date format.");
                return;
            }

            PromotionDAO promotionDAO = new PromotionDAO();
            if (promotionDAO.isProductAlreadyInActivePromotion(targetId, targetType)) {
                request.setAttribute("error", "This target is already in an active promotion!");
                request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/setPromotion/setPromotion.jsp").forward(request, response);
                return;
            }

            if (promotionDAO.addPromotion(targetType, targetId, discount, startDate, endDate, name)) {
                response.sendRedirect("AdminProduct?successpro=1");
            } else {
                request.setAttribute("error", "Failed to add promotion!");
                request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/setPromotion/setPromotion.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert(response, "System error: " + e.getMessage());
        }
    }

    private void showErrorAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        response.getWriter().write("<script>Swal.fire({ icon: 'error', title: 'Error', text: '" + message + "', }).then(() => { window.location.href = '/TShophm/WEB-INF/View/admin/productManagement/setPromotion/setPromotion.jsp'; });</script>");
        response.getWriter().flush();
    }

    private void showSuccessAlert(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        response.getWriter().write("<script>Swal.fire({ icon: 'success', title: 'Success', text: '" + message + "', }).then(() => { window.location.href = '" + redirectUrl + "'; });</script>");
        response.getWriter().flush();
    }
}
