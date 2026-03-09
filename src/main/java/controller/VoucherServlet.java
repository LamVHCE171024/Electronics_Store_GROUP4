package controller;

import dao.VoucherDAO;
import model.Voucher;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "VoucherServlet", urlPatterns = {"/Voucher"})
public class VoucherServlet extends HttpServlet {

    private VoucherDAO voucherDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        voucherDAO = new VoucherDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                request.getRequestDispatcher("/WEB-INF/View/admin/voucherManagement/voucherForm.jsp").forward(request, response);
                break;

            case "detail":
                showVoucherDetail(request, response);
                break;

            case "edit":
                try {
                String idRaw = request.getParameter("id");
                if (idRaw == null || idRaw.isEmpty()) {
                    throw new IllegalArgumentException("Missing voucher ID.");
                }
                int id = Integer.parseInt(idRaw);
                Voucher voucher = voucherDAO.getVoucherById(id);
                request.setAttribute("voucher", voucher);
                request.getRequestDispatcher("/WEB-INF/View/admin/voucherManagement/voucherForm.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("Voucher?error=InvalidID");
            }
            break;

            case "delete":
                try {
                String idRaw = request.getParameter("id");
                if (idRaw == null || idRaw.isEmpty()) {
                    throw new IllegalArgumentException("Missing voucher ID.");
                }
                int delId = Integer.parseInt(idRaw);
                voucherDAO.deleteVoucher(delId);
                response.sendRedirect("Voucher?success=delete");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("Voucher?error=DeleteFailed");
            }
            break;

            default:
                String keyword = request.getParameter("searchCode");
                List<Voucher> list;

                if (keyword != null && !keyword.trim().isEmpty()) {
                    list = voucherDAO.searchByCode(keyword.trim());
                } else {
                    list = voucherDAO.getAllVouchers();
                }

                request.setAttribute("voucherList", list);
                request.getRequestDispatcher("/WEB-INF/View/admin/voucherManagement/voucherList.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                    ? Integer.parseInt(request.getParameter("id")) : 0;

            String code = request.getParameter("code");
            if (code == null || code.trim().isEmpty()) {
                throw new IllegalArgumentException("Voucher code cannot be empty.");
            }

            int discount = Integer.parseInt(request.getParameter("discountPercent"));
            Date expiry = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("expiryDate"));
            double minAmount = Double.parseDouble(request.getParameter("minOrderAmount"));
            double maxDiscount = Double.parseDouble(request.getParameter("maxDiscountAmount"));
            int usageLimit = Integer.parseInt(request.getParameter("usageLimit"));
            boolean isActive = "true".equals(request.getParameter("isActive"));
            Date createdAt = new Date();
            String description = request.getParameter("description");
            boolean isGlobal = "1".equals(request.getParameter("isGlobal"));

            int usedCount = 0; // Default when creating new
            if (id != 0) {
                // If editing, get current usedCount from DB
                Voucher old = voucherDAO.getVoucherById(id);
                usedCount = old != null ? old.getUsedCount() : 0;
            }
            if (voucherDAO.isCodeDuplicate(code, id)) {
                throw new IllegalArgumentException("Voucher code already exists. Please choose another.");
            }
            if (discount < 1 || discount > 100) {
                throw new IllegalArgumentException("Discount percent must be between 1 and 100.");
            }
            if (minAmount < 0 || maxDiscount < 0) {
                throw new IllegalArgumentException("Amounts cannot be negative.");
            }
            if (usageLimit < 1) {
                throw new IllegalArgumentException("Usage limit must be at least 1.");
            }
            if (usedCount < 0 || usedCount > usageLimit) {
                throw new IllegalArgumentException("Used count is not valid.");
            }
            if (expiry.before(new Date())) {
                throw new IllegalArgumentException("Expiry date must be today or later.");
            }
            if (usedCount >= usageLimit) {
                isActive = false;
            }

            Voucher v = new Voucher(id, code, discount, expiry, minAmount, maxDiscount,
                    usageLimit, usedCount, isActive, createdAt, description, isGlobal);

            if (id == 0) {
                voucherDAO.addVoucher(v);
                response.sendRedirect("Voucher?success=create");
            } else {
                voucherDAO.updateVoucher(v);
                response.sendRedirect("Voucher?success=update");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error occurred: " + e.getMessage());
            // Re-populate, set usedCount = 0 (or from DB if isEdit)
            try {
                int id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                        ? Integer.parseInt(request.getParameter("id")) : 0;
                String code = request.getParameter("code");
                int discount = Integer.parseInt(request.getParameter("discountPercent"));
                Date expiry = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("expiryDate"));
                double minAmount = Double.parseDouble(request.getParameter("minOrderAmount"));
                double maxDiscount = Double.parseDouble(request.getParameter("maxDiscountAmount"));
                int usageLimit = Integer.parseInt(request.getParameter("usageLimit"));
                boolean isActive = request.getParameter("isActive") != null;
                Date createdAt = new Date();
                String description = request.getParameter("description");
                boolean isGlobal = "1".equals(request.getParameter("isGlobal"));
                int usedCount = 0;
                if (id != 0) {
                    Voucher old = voucherDAO.getVoucherById(id);
                    usedCount = old != null ? old.getUsedCount() : 0;
                }
                Voucher retryVoucher = new Voucher(id, code, discount, expiry, minAmount,
                        maxDiscount, usageLimit, usedCount, isActive, createdAt, description, isGlobal);
                request.setAttribute("voucher", retryVoucher);
            } catch (Exception ex) {
                request.setAttribute("voucher", null);
            }
            request.getRequestDispatcher("/WEB-INF/View/admin/voucherManagement/voucherForm.jsp").forward(request, response);
        }
    }

    private void showVoucherDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idRaw = request.getParameter("id");
            if (idRaw == null || idRaw.isEmpty()) {
                throw new IllegalArgumentException("Missing voucher ID.");
            }
            int id = Integer.parseInt(idRaw);

            Voucher voucher = voucherDAO.getVoucherById(id);
            if (voucher != null) {
                request.setAttribute("voucher", voucher);
                request.getRequestDispatcher("/WEB-INF/View/admin/voucherManagement/voucherDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("Voucher?error=VoucherNotFound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Voucher?error=InvalidVoucherDetail");
        }
    }
}
