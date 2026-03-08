
package controller;
import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import model.CartItem;

@WebServlet(name = "RemoveCartItemServlet", urlPatterns = {"/RemoveCartItem"})
public class RemoveCartItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("RemoveCartItemServlet: POST request received, action: " + request.getParameter("action"));
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String accountIdRaw = request.getParameter("accountId");
        if ("deleteMultiple".equals(action)) {
            try {
                String selectedItems = request.getParameter("selectedItems");
                if (selectedItems == null || selectedItems.isEmpty()) {
                    session.setAttribute("message", "No items selected for deletion.");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw);
                    return;
                }
                List<String> itemIds = Arrays.asList(selectedItems.split(","));
                CartDAO cartDAO = new CartDAO();
                boolean isSuccess = cartDAO.deleteMultipleCartItems(itemIds);
                if (isSuccess) {
                    session.setAttribute("message", "Multiple items deleted successfully!");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw + "&successdeletem=1");
                } else {
                    session.setAttribute("message", "Failed to delete multiple items.");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw + "&errordeletem=1");
                }
            } catch (Exception e) {
                System.err.println("Error in deleteMultiple: " + e.getMessage());
                session.setAttribute("message", "Error deleting cart items.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
            }
        } else {
            session.setAttribute("message", "Invalid action.");
            response.sendRedirect("CartList?accountId=" + accountIdRaw);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("RemoveCartItemServlet: GET request received, action: " + request.getParameter("action"));
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String accountIdRaw = request.getParameter("accountId");
        try {
            if ("remove".equals(action)) {
                int cartItemId = Integer.parseInt(request.getParameter("id"));
                System.out.println("Removing CartItemID: " + cartItemId);
                CartDAO cartDAO = new CartDAO();
                boolean isSuccess = cartDAO.deleteCartItem(cartItemId);
                if (isSuccess) {
                    session.setAttribute("message", "Cart item deleted successfully!");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw + "&successdeletes=1");
                } else {
                    session.setAttribute("message", "Failed to delete cart item.");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw + "&errordeletes=1");
                }
            } else if ("update".equals(action)) {
                int cartItemId = Integer.parseInt(request.getParameter("id"));
                int newQuantity = Integer.parseInt(request.getParameter("quantity"));
                System.out.println("Updating CartItemID: " + cartItemId + ", New Quantity: " + newQuantity);
                if (newQuantity <= 0) {
                    session.setAttribute("message", "Quantity must be greater than 0!");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw);
                    return;
                }
                CartDAO cartDAO = new CartDAO();
                boolean success = cartDAO.updateCartItemQuantity(cartItemId, newQuantity);
                if (success) {
                    session.setAttribute("message", "Quantity updated successfully!");
                    session.setAttribute("lastUpdatedCartItemId", cartItemId); // Lưu cartItemId vừa cập nhật
                    response.sendRedirect("CartList?accountId=" + accountIdRaw);
                } else {
                    System.out.println("Failed to update quantity for CartItemID: " + cartItemId);
                    session.setAttribute("message", "Failed to update quantity!");
                    response.sendRedirect("CartList?accountId=" + accountIdRaw);
                }
            } else {
                session.setAttribute("message", "Invalid action.");
                response.sendRedirect("CartList?accountId=" + accountIdRaw);
            }
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException in RemoveCartItemServlet: " + e.getMessage());
            session.setAttribute("message", "Invalid input parameters.");
            response.sendRedirect("CartList?accountId=" + accountIdRaw);
        } catch (Exception e) {
            System.err.println("Exception in RemoveCartItemServlet: " + e.getMessage());
            session.setAttribute("message", "An error occurred while processing the request.");
            response.sendRedirect("CartList?accountId=" + accountIdRaw);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to remove or update items in cart";
    }
}
