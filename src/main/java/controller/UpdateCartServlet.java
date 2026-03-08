package controller;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.CartItem;

@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCart"})
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("UpdateCartServlet: GET request received, action: " + request.getParameter("action"));
        HttpSession session = request.getSession();
        try {
            String action = request.getParameter("action");
            String accountIdRaw = request.getParameter("accountId");
            String cartItemIdRaw = request.getParameter("id");
            String quantityRaw = request.getParameter("quantity");

            // Validate inputs
            if (action == null || cartItemIdRaw == null || quantityRaw == null || accountIdRaw == null) {
                session.setAttribute("message", "Missing required parameters.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
                return;
            }
            if (!"update".equals(action)) {
                session.setAttribute("message", "Invalid action.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
                return;
            }

            int cartItemId = Integer.parseInt(cartItemIdRaw);
            int quantity = Integer.parseInt(quantityRaw);
            int accountId = Integer.parseInt(accountIdRaw);

            // Validate quantity
            if (quantity < 1) {
                session.setAttribute("message", "Quantity must be at least 1.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
                return;
            }

            CartDAO cartDAO = new CartDAO();

            // Get the cart item to retrieve the productId
            CartItem cartItem = cartDAO.getCartItemById(cartItemId); // Assume this method exists in CartDAO to fetch CartItem by cartItemId
            if (cartItem == null) {
                session.setAttribute("message", "Cart item not found.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
                return;
            }

            // Check product quantity left from ImportStockDetails
            if (!cartDAO.getProductQuantityLeft(cartItem.getProductID(), quantity)) {
                session.setAttribute("message", "Requested quantity exceeds available stock.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw + "&checkquantity=1");
                return;
            }

            // Update cart item quantity
            boolean updated = cartDAO.updateCartItemQuantity(cartItemId, quantity);
            System.out.println("Update result for cartItemId " + cartItemId + ": " + updated);

            if (updated) {
                session.setAttribute("message", "Quantity updated successfully!");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
            } else {
                session.setAttribute("message", "Failed to update quantity.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e.getMessage());
            session.setAttribute("message", "Invalid input parameters.");
            response.sendRedirect("CartList?accountId=" + request.getParameter("accountId"));
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            session.setAttribute("message", "An error occurred while updating quantity.");
            response.sendRedirect("CartList?accountId=" + request.getParameter("accountId"));
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles cart item quantity updates";
    }
}