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

@WebServlet(name="SetDefaultAddressServlet", urlPatterns={"/SetDefaultAddress"})
public class SetDefaultAddressServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("cus");
        if (cus == null) {
            response.sendRedirect("Login");
            return;
        }
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("ViewShippingAddress");
            return;
        }
        int addressId;
        try {
            addressId = Integer.parseInt(idStr);
        } catch (Exception e) {
            response.sendRedirect("ViewShippingAddress");
            return;
        }

        AddressDAO dao = new AddressDAO();
        Address addr = dao.getAddressById(addressId);
        if (addr != null && addr.getCustomerId() == cus.getId()) {
            dao.unsetDefaultAddresses(cus.getId());// bo dia chi cu
            // Set Ä‘á»‹a chá»‰ nÃ y lÃ  máº·c Ä‘á»‹nh
            addr.setDefault(true);
            dao.updateAddress(addr);
        }
        response.sendRedirect("ViewShippingAddress");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Set an address as default for the current customer";
    }
}

