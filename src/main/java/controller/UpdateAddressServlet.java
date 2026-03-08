package controller;

import dao.AddressDAO;
import model.Address;
import model.Customer;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UpdateAddressServlet", urlPatterns = {"/UpdateAddress"})
public class UpdateAddressServlet extends HttpServlet {

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
        } catch (NumberFormatException e) {
            response.sendRedirect("ViewShippingAddress");
            return;
        }

        AddressDAO dao = new AddressDAO();
        Address addr = dao.getAddressById(addressId);

        // Không cho sửa nếu không đúng chủ sở hữu
        if (addr == null || addr.getCustomerId() != cus.getId()) {
            response.sendRedirect("ViewShippingAddress");
            return;
        }

        request.setAttribute("address", addr);
        request.setAttribute("hasDefault", dao.hasDefaultAddress(cus.getId()));
        request.getRequestDispatcher("/WEB-INF/View/customer/shippingAddress/UpdateShippingAddress.jsp").forward(request, response);
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

        String idStr = request.getParameter("id");
        int addressId;
        try {
            addressId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("ViewShippingAddress");
            return;
        }

        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String addressDetails = request.getParameter("addressDetails");
        boolean isDefault = request.getParameter("isDefault") != null;

        // Validate input
        if (province == null || district == null || ward == null || addressDetails == null
                || province.trim().isEmpty() || district.trim().isEmpty()
                || ward.trim().isEmpty() || addressDetails.trim().isEmpty()) {
            AddressDAO dao = new AddressDAO();
            Address addr = dao.getAddressById(addressId);
            request.setAttribute("address", addr);
            request.setAttribute("error", "Please fill in all required fields.");
            request.getRequestDispatcher("/WEB-INF/View/customer/shippingAddress/UpdateShippingAddress.jsp").forward(request, response);
            return;
        }

        AddressDAO dao = new AddressDAO();
        // Nếu chọn default hoặc chưa có default thì set default
        if (!dao.hasDefaultAddress(cus.getId()) || isDefault) {
            isDefault = true;
            dao.unsetDefaultAddresses(cus.getId());
        }

        Address address = new Address(
            addressId,
            cus.getId(),
            province,
            district,
            ward,
            addressDetails,
            isDefault
        );
        dao.updateAddress(address);

        response.sendRedirect("ViewShippingAddress");
    }
}
