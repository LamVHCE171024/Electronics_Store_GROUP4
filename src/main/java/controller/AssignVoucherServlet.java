/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerVoucherDAO;
import dao.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.CustomerVoucher;
import model.Voucher;

/**
 *
 * @author HP
 */
@WebServlet(name = "AssignVoucherServlet", urlPatterns = {"/AssignVoucher"})
public class AssignVoucherServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private VoucherDAO voucherDAO = new VoucherDAO();
    private CustomerVoucherDAO customerVoucherDAO = new CustomerVoucherDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AssignVoucherServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AssignVoucherServlet at " + request.getContextPath() + "</h1>");
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
        String customerIdRaw = request.getParameter("customerId");
        if (customerIdRaw == null) {
            response.sendRedirect("CustomerList?error=MissingCustomerId");
            return;
        }
        int customerId = Integer.parseInt(customerIdRaw);
        List<Voucher> personalVouchers = voucherDAO.getPersonalVouchersAvailable();
        request.setAttribute("customerId", customerId);
        request.setAttribute("personalVouchers", personalVouchers);
        request.getRequestDispatcher("/WEB-INF/View/staff/customerManagement/asignVoucher.jsp").forward(request, response);
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
        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            int voucherId = Integer.parseInt(request.getParameter("voucherId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String expirationDateRaw = request.getParameter("expirationDate");
            Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateRaw);

            Voucher voucher = voucherDAO.getVoucherById(voucherId);
            if (voucher == null) {
                request.setAttribute("error", "Voucher does not exist.");
                doGet(request, response);
                return;
            }
            Date voucherExpiry = voucher.getExpiryDate();
            Date now = new Date();
            if (voucherExpiry.before(now)) {
                request.setAttribute("error", "Voucher has already expired. Cannot assign to customer.");
                doGet(request, response);
                return;
            }
            if (quantity > voucher.getUsageLimit()) {
                request.setAttribute("error", "The quantity exceeds the usage limit of this voucher (" + voucher.getUsageLimit() + ").");
                doGet(request, response);
                return;
            }

            if (expirationDate.after(voucherExpiry)) {
                request.setAttribute("error", "Customer voucher expiration cannot exceed the master voucher's expiration date ("
                        + new SimpleDateFormat("yyyy-MM-dd").format(voucherExpiry) + ").");
                doGet(request, response);
                return;
            }
            if (customerVoucherDAO.isAsigned(customerId, voucherId)) {
                request.setAttribute("error", "This voucher has already been assigned to this customer.");
                doGet(request, response);
                return;
            }

            CustomerVoucher cv = new CustomerVoucher(customerId, voucherId, expirationDate, quantity);
            boolean ok = customerVoucherDAO.assignVoucher(cv);
            if (ok) {
                response.sendRedirect("CustomerList?success=assigned");
            } else {
                request.setAttribute("error", "Failed to assign voucher.");
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid input or duplicate voucher assignment.");
            doGet(request, response);
        }
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
