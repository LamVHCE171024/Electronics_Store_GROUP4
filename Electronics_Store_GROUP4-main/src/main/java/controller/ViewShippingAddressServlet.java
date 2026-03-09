package controller;

import dao.AddressDAO;
import model.Address;
import model.Customer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ViewShippingAddressServlet", urlPatterns = {"/ViewShippingAddress"})
public class ViewShippingAddressServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("cus");
        if (cus == null) {
            response.sendRedirect("Login");
            return;
        }
        AddressDAO dao = new AddressDAO();
        List<Address> addressList = dao.getAllAddressesByCustomerId(cus.getId());
        if (addressList == null || addressList.isEmpty()) {
            request.setAttribute("noAddress", true);
        } else {
            request.setAttribute("addressList", addressList);
        }
        request.getRequestDispatcher("/WEB-INF/View/customer/shippingAddress/ViewShippingAddress.jsp").forward(request, response);
    }
}

