package controller;

import dao.CustomerDAO;
import dao.CustomerVoucherDAO;
import model.Account;
import model.Customer;
import model.CustomerVoucher;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewCustomerVoucherServlet", urlPatterns = {"/ViewCustomerVoucher"})
public class ViewCustomerVoucherServlet extends HttpServlet {

    private final CustomerVoucherDAO customerVoucherDAO = new CustomerVoucherDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login"); 
            return;
        }

        Customer customer = customerDAO.getCustomerByAccountId(user.getAccountID());
        if (customer == null) {
            response.sendRedirect("Home"); 
            return;
        }

        int customerId = customer.getId();
        List<CustomerVoucher> voucherList = customerVoucherDAO.getAllVouchersForCustomer(customerId);

        request.setAttribute("voucherList", voucherList);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/WEB-INF/View/customer/voucherCustomer/voucherListCustomer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "View vouchers for a logged-in customer (global & personal)";
    }
}

