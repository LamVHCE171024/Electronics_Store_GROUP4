package controller;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import model.Account;
import model.Cart;
import model.CartItem;

@WebServlet(name = "AddCartServlet", urlPatterns = {"/AddCartServlet"})
public class AddCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDAO cartDAO = new CartDAO();

        int productId = Integer.parseInt(request.getParameter("productId"));
        int categoryId = (request.getParameter("categoryId") != null) ? Integer.parseInt(request.getParameter("categoryId")) : -1;
        int quantity = request.getParameter("quantity") != null ? Integer.parseInt(request.getParameter("quantity")) : 1;
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");
        Account user = (Account) session.getAttribute("user");
        if (accountId == null) {
            response.sendRedirect("Login");
            return;
        }
        try {
            Cart cart = cartDAO.getCartByAccountId(accountId);
            if (cart == null) {
                cart = new Cart();
                cart.setAccountId(accountId);
                cart.setCreatedAt(LocalDateTime.now());
                cartDAO.createCart(cart);
            }

            // Calculate total quantity needed (existing quantity + new quantity)
            CartItem existingItem = cartDAO.getCartItemByProductAndCart(cart.getCartId(), productId);
            int totalQuantityNeeded = quantity;
            if (existingItem != null) {
                totalQuantityNeeded += existingItem.getQuantity();
            }

            // Check product quantity left from ImportStockDetails
            String redirectUrl = "ProductDetail?productId=" + productId + "&categoryId=" + categoryId;
            if (!cartDAO.getProductQuantityLeft(productId, totalQuantityNeeded)) {
                response.sendRedirect(redirectUrl + "&checkquantity=1");
                return;
            }

            if (existingItem != null) {
                // Update quantity if product already exists in cart
                int newQuantity = existingItem.getQuantity() + quantity;
                cartDAO.updateCartItemQuantity(existingItem.getCartItemID(), newQuantity);
                if ("addcart".equals(action)) {
                    // Redirect with success message for "Add to Cart"
                    response.sendRedirect(redirectUrl + "&successcreate=1");
                } else if ("buynow".equals(action)) {
                    // Store updated cartItemId in session for "Buy Now"
                    session.setAttribute("lastAddedCartItemId", existingItem.getCartItemID());
                    response.sendRedirect("CartList?action=list&accountId=" + accountId);
                }
            } else {
                // Add new cart item
                CartItem cartItem = new CartItem();
                cartItem.setCartID(cart.getCartId());
                cartItem.setProductID(productId);
                cartItem.setQuantity(quantity);
                boolean success = cartDAO.addCartItem(cartItem);
                if (success) {
                    // Get newly added cartItemId
                    CartItem newItem = cartDAO.getCartItemByProductAndCart(cart.getCartId(), productId);
                    if (newItem != null) {
                        if ("addcart".equals(action)) {
                            // Redirect with success message for "Add to Cart"
                            response.sendRedirect(redirectUrl + "&successcreate=1");
                        } else if ("buynow".equals(action)) {
                            // Store new cartItemId in session for "Buy Now"
                            session.setAttribute("lastAddedCartItemId", newItem.getCartItemID());
                            response.sendRedirect("CartList?action=list&accountId=" + accountId);
                        }
                    } else {
                        response.sendRedirect(redirectUrl + "&errorcreate=1");
                    }
                } else {
                    response.sendRedirect(redirectUrl + "&errorcreate=1");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Exception in AddCartServlet: " + e.getMessage());
            session.setAttribute("message", "An error occurred while updating cart.");
            response.sendRedirect("ProductDetail?productId=" + productId + "&categoryId=" + categoryId + "&errorcreate=1");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet to add products to cart";
    }
}
