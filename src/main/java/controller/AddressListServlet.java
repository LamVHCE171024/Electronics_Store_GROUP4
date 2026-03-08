
package controller;

import dao.AddressDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Address;
import model.Customer;

@WebServlet(name = "AddressListServlet", urlPatterns = {"/AddressListServlet"})
public class AddressListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("cus");

        if (user == null || customer == null) {
           
            response.sendRedirect("Login");
            return;
        }

        // Lấy danh sách địa chỉ
        AddressDAO addressDAO = new AddressDAO();
        List<Address> addresses = addressDAO.getAddressesByCustomerId(customer.getId());
        request.setAttribute("addresses", addresses);
        request.setAttribute("fromCheckout", request.getParameter("fromCheckout"));
        request.setAttribute("selectedCartItemIds", request.getParameter("selectedCartItemIds"));

        if (addresses.isEmpty()) {
            session.setAttribute("message", "You do not have any address yet. Please add a new address.");
        }

        request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/addressList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("cus");

        if (user == null || customer == null) {
           
            response.sendRedirect("Login");
            return;
        }

        try {
            String addressIdStr = request.getParameter("addressId");
            String selectedCartItemIds = request.getParameter("selectedCartItemIds");
            String fromCheckout = request.getParameter("fromCheckout");

            if (addressIdStr == null || addressIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("AddressID is required");
            }

            int addressId;
            try {
                addressId = Integer.parseInt(addressIdStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid AddressID: " + addressIdStr);
            }

            AddressDAO addressDAO = new AddressDAO();
            Address selectedAddress = addressDAO.getAddressById(addressId);
            if (selectedAddress == null) {
                throw new IllegalArgumentException("Invalid AddressID: " + addressIdStr);
            }

            // Lưu địa chỉ được chọn vào session
            session.setAttribute("selectedAddress", selectedAddress);
            session.setAttribute("message", "Choose a successful address!");

            if ("true".equalsIgnoreCase(fromCheckout)) {
                response.sendRedirect("CheckoutServlet?action=checkout&selectedCartItemIds=" + (selectedCartItemIds != null ? selectedCartItemIds : ""));
            } else {
                response.sendRedirect("AddressListServlet");
            }

        } catch (IllegalArgumentException e) {
            Logger.getLogger(AddressListServlet.class.getName()).log(Level.SEVERE, "Validation error: {0}", e.getMessage());
            session.setAttribute("message", "Real Error: " + e.getMessage());
            response.sendRedirect("AddressListServlet?fromCheckout=" + request.getParameter("fromCheckout") + 
                                 "&selectedCartItemIds=" + (request.getParameter("selectedCartItemIds") != null ? request.getParameter("selectedCartItemIds") : ""));
        }
    }
}
