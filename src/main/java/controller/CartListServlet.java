package controller;

import dao.CartDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.CartItem;

@WebServlet(name = "CartListServlet", urlPatterns = {"/CartList"})
public class CartListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDAO dao = new CartDAO();
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        if (action == null) {
            action = "list";
        }

        try {
            int accountId = user.getAccountID();

            if (action.equalsIgnoreCase("list")) {
                List<CartItem> cartItems = dao.getCartItemsByAccountId(accountId);
                request.setAttribute("cartItems", cartItems);

                if (cartItems.isEmpty()) {
                    request.setAttribute("message", "No items found in the cart.");
                }

                request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/viewCart.jsp").forward(request, response);
            } else if (action.equalsIgnoreCase("shop")) {
                request.getRequestDispatcher("/WEB-INF/View/customer/homePage/homePage.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Account ID.");
            request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/viewCart.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDAO cartDAO = new CartDAO();
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        String action = request.getParameter("action");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        int accountId = user.getAccountID();

        try {
            if ("saveSelectedItems".equals(action)) {
                String selectedCartItemIds = request.getParameter("selectedCartItemIds");
                session.setAttribute("selectedCartItemIds", selectedCartItemIds);
                response.setContentType("text/plain");
                response.getWriter().write("Selected items saved");
                return;
            } else if ("update".equals(action)) {
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                int newQuantity = Integer.parseInt(request.getParameter("quantity"));

                // Kiá»ƒm tra sá»‘ lÆ°á»£ng há»£p lá»‡
                if (newQuantity <= 0) {
                    session.setAttribute("message", "Quantity must be greater than 0!");
                    response.sendRedirect("CartList?action=list&accountId=" + accountId);
                    return;
                }

                // Láº¥y thÃ´ng tin cart item Ä‘á»ƒ validation
                CartItem cartItem = cartDAO.getCartItemById(cartItemId);
                if (cartItem == null) {
                    session.setAttribute("message", "Cart item not found!");
                    response.sendRedirect("CartList?action=list&accountId=" + accountId);
                    return;
                }

//                // Cáº­p nháº­t sá»‘ lÆ°á»£ng
//                boolean success = cartDAO.updateCartItemQuantity(cartItemId, newQuantity);
//
//                if (success) {
//                    session.setAttribute("message", "Quantity updated successfully!");
//                } else {
//                    session.setAttribute("message", "Failed to update quantity!");
//                }
//
//                response.sendRedirect("CartList?action=list&accountId=" + accountId);
//                return;
            }

        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException in CartListServlet: " + e.getMessage());
            session.setAttribute("message", "Invalid input parameters.");
        } catch (Exception e) {
            System.err.println("Exception in CartListServlet: " + e.getMessage());
            session.setAttribute("message", "An error occurred while updating cart.");
        }

        response.sendRedirect("CartList?action=list&accountId=" + accountId);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing cart items by AccountID";
    }
}

