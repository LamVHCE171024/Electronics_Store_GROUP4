package controller;

import dao.AddressDAO;
import model.Address;
import model.Customer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddAddressServlet", urlPatterns = {"/AddAddress"})
public class AddAddressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("cus");
        if (cus == null) {
            response.sendRedirect("Login");
            return;
        }
        AddressDAO addressDAO = new AddressDAO();
        boolean hasDefault = addressDAO.hasDefaultAddress(cus.getId());
        request.setAttribute("hasDefault", hasDefault); // truyá»n biáº¿n vÃ o JSP
        request.getRequestDispatcher("/WEB-INF/View/customer/shippingAddress/AddShippingAddress.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("cus");
        if (cus == null) {
            response.sendRedirect("Login");
            return;
        }
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String addressDetails = request.getParameter("addressDetails");

        if (province == null || district == null || ward == null || addressDetails == null
                || province.trim().isEmpty() || district.trim().isEmpty()
                || ward.trim().isEmpty() || addressDetails.trim().isEmpty()) {
            request.setAttribute("error", "Please fill in all required fields.");
            AddressDAO addressDAO = new AddressDAO();
            boolean hasDefault = addressDAO.hasDefaultAddress(cus.getId());
            request.setAttribute("hasDefault", hasDefault);
            request.getRequestDispatcher("/WEB-INF/View/customer/shippingAddress/AddShippingAddress.jsp").forward(request, response);
            return;
        }

        AddressDAO addressDAO = new AddressDAO();
        boolean hasDefault = addressDAO.hasDefaultAddress(cus.getId());

        boolean isDefault;
        if (!hasDefault) {
            isDefault = true;
        } else {
            isDefault = (request.getParameter("isDefault") != null);
        }
        if (isDefault) {
            addressDAO.unsetDefaultAddresses(cus.getId());
        }

        Address newAddress = new Address(
                0,
                cus.getId(),
                province,
                district,
                ward,
                addressDetails,
                isDefault
        );
        addressDAO.createAddress(newAddress);

        response.sendRedirect("ViewShippingAddress");
    }
}

