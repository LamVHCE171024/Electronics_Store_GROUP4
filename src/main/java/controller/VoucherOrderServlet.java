package controller;

import dao.CustomerVoucherDAO;
import dao.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Customer;
import model.CustomerVoucher;
import model.Voucher;

@WebServlet(name = "VoucherOrderServlet", urlPatterns = {"/VoucherOrder"})
public class VoucherOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerVoucherDAO customerVoucherDAO = new CustomerVoucherDAO();
        Customer customer = (Customer) request.getSession().getAttribute("cus");
        if (customer == null) {
            response.sendRedirect("Login");
            return;
        }

        // LẤY selectedCartItemIds từ session và set vào request
        HttpSession session = request.getSession();
        String selectedCartItemIds = (String) session.getAttribute("selectedCartItemIds");

        int customerId = customer.getId();
        List<CustomerVoucher> voucherList = customerVoucherDAO.getAllVouchersForCustomer(customerId);
        request.setAttribute("voucherList", voucherList);
        request.setAttribute("cus", customer);

        // ĐẶT selectedCartItemIds vào request để JSP có thể sử dụng
        request.setAttribute("selectedCartItemIds", selectedCartItemIds);

        request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Handles voucher application during checkout";
    }
}
