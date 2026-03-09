package controller;

import dao.CustomerVoucherDAO;
import dao.VoucherDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        // Láº¤Y selectedCartItemIds tá»« session vÃ  set vÃ o request
        HttpSession session = request.getSession();
        String selectedCartItemIds = (String) session.getAttribute("selectedCartItemIds");

        int customerId = customer.getId();
        List<CustomerVoucher> voucherList = customerVoucherDAO.getAllVouchersForCustomer(customerId);
        request.setAttribute("voucherList", voucherList);
        request.setAttribute("cus", customer);

        // Äáº¶T selectedCartItemIds vÃ o request Ä‘á»ƒ JSP cÃ³ thá»ƒ sá»­ dá»¥ng
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

