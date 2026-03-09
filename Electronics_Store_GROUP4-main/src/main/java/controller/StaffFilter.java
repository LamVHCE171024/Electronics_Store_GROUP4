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
    "/StaffDashboard",
    "/ImportStock",
    "/ImportStockHistory",
    "/ViewOrderList",
    "/ViewOrderDetail",
    "/CustomerList",
    "/AssignVoucher",
    "/ImportStatistic",
    "/ImportHistoryDetail",
    "/ViewListNewFeedback",
    "/ViewFeedBackForStaff"
})
public class StaffFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khá»Ÿi táº¡o filter náº¿u cáº§n
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // false -> khÃ´ng táº¡o má»›i náº¿u chÆ°a cÃ³

        // ChÆ°a login
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/LoginStaff");
            return;
        }

        // Láº¥y account Ä‘á»ƒ kiá»ƒm tra role
        Account acc = (Account) session.getAttribute("user");
        if (acc == null || acc.getRoleID() != 2) {
            res.sendRedirect(req.getContextPath() + "/LoginStaff");
            return;
        }

        // Náº¿u pass háº¿t â†’ cho Ä‘i tiáº¿p
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // XÃ³a tÃ i nguyÃªn náº¿u cáº§n
    }
}

