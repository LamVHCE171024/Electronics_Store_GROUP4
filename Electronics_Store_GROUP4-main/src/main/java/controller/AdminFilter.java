package controller;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;

@WebFilter(urlPatterns = {
    "/AddPromotionServlet",
    "/AdminAddProductDetail",
    "/AdminCreateProduct",
    "/AdminDashboard",
    "/StaffDeleteProduct",
    "/AdminProduct",
    "/AdminUpdateProduct",
    "/AdminViewProductDetail",
    "/CategoryDetail",
    "/CategoryView",
    "/ChangePasswordStaff",
    "/CreateCategory",
    "/CreateStaffServlet",
    "/CreateSupplier",
    "/DeleteStaffServlet",
    "/InventoryStatistic",
    "/ManageStatistic",
    "/RevenueStatistic",
    "/StaffList",
    "/UpdateCategory",
    "/UpdateStaffServlet",
    "/UpdateSupplier",
    "/ViewSupplier"
        
})
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional: filter initialization
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // avoid creating new session
        Account acc = (session != null) ? (Account) session.getAttribute("admin") : null;


        if (acc == null || acc.getRoleID() != 1) {
            // Not logged in or not an admin
            res.sendRedirect(req.getContextPath() + "/LoginAdmin");
            return;
        }

        // Logged in as admin
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Optional: cleanup code
    }
}

