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
import java.io.PrintWriter;

@WebServlet(name = "DeleteAddressServlet", urlPatterns = {"/DeleteAddress"})
public class DeleteAddressServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        HttpSession session = request.getSession();
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        Customer cus = (Customer) session.getAttribute("cus");
        if (cus == null) {
            out.print("{\"success\":false,\"message\":\"Unauthorized.\"}");
            return;
        }

        if (idStr == null) {
            out.print("{\"success\":false,\"message\":\"Missing address id.\"}");
            return;
        }

        int addressId;
        try {
            addressId = Integer.parseInt(idStr);
        } catch (Exception e) {
            out.print("{\"success\":false,\"message\":\"Invalid address id.\"}");
            return;
        }

        AddressDAO dao = new AddressDAO();
        Address address = dao.getAddressById(addressId);
        if (address == null || address.getCustomerId() != cus.getId()) {
            out.print("{\"success\":false,\"message\":\"Address not found or unauthorized.\"}");
            return;
        }

        boolean deleted = dao.deleteAddressById(addressId);
        if (deleted) {
            out.print("{\"success\":true}");
        } else {
            out.print("{\"success\":false,\"message\":\"Delete failed.\"}");
        }
    }
}

