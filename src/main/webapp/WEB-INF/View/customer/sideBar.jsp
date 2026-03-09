<%@page import="model.Account"%>
<%@page import="model.Customer"%>
<%
        Account acc = (Account) session.getAttribute("user");
        Customer user = (Customer) session.getAttribute("cus");
        if (acc == null || user == null) {
            response.sendRedirect("Login");
            return;
        }

    String currentPage = request.getRequestURI();
%>
<aside class="account-sidebar shadow-sm bg-white rounded-4 p-3 me-4" style="min-width: 240px;max-width: 270px;">
    <div class="mb-4 d-flex align-items-center">
        <div class="avatar bg-light rounded-circle d-flex align-items-center justify-content-center me-3" style="width:48px;height:48px;">
            <i class="bi bi-person-circle fs-2 text-primary"></i>
        </div>
        <div>
            <div style="font-weight: 600;"><%= user != null ? user.getFullName() : "Customer"%></div>
            <div class="text-muted" style="font-size:13px;"><%= user != null ? user.getPhone() : ""%></div>
        </div>
    </div>
    <ul class="nav flex-column account-menu">
        <li class="nav-item mb-1">
            <a href="ViewProfile?id=<%= acc.getAccountID()%>"
               class="nav-link<%= currentPage.contains("ViewProfile") ? " active" : ""%>"
               style="display:flex;align-items:center;">
                <i class="bi bi-person-circle me-2"></i> <span>Profile</span>
            </a>
        </li>
        <li class="nav-item mb-1">
            <a href="${pageContext.request.contextPath}/ViewOrderOfCustomer"
               class="nav-link<%= currentPage.contains("ViewOrder") ? " active" : ""%>"
               style="display:flex;align-items:center;">
                <i class="bi bi-bag-check me-2"></i> <span>Order</span>
            </a>
        </li>
        <li class="nav-item mb-1">
            <a href="ViewShippingAddress"
               class="nav-link<%= currentPage.contains("ViewShippingAddress") ? " active" : ""%>"
               style="display:flex;align-items:center;">
                <i class="bi bi-geo-alt me-2"></i> <span>Shipping Addresses</span>
            </a>
        </li>
        <li class="nav-item mb-1">
            <a href="ViewCustomerVoucher"
               class="nav-link<%= currentPage.contains("ViewVoucher") ? " active" : ""%>"
               style="display:flex;align-items:center;">
                <i class="bi bi-ticket-perforated me-2"></i> <span>My Vouchers</span>
            </a>
        </li>
        <li class="nav-item mt-2">
            <a href="Logout" class="nav-link text-danger" style="display:flex;align-items:center;">
                <i class="bi bi-box-arrow-right me-2"></i> <span>Logout</span>
            </a>
        </li>
    </ul>
</aside>
