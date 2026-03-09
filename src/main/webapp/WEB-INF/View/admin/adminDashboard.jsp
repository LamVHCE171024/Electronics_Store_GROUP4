<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.NumberFormat, java.util.Locale"%>
<% 
    Account acc = (Account) session.getAttribute("admin");
    if (acc == null || acc.getRoleID() != 1) {
        response.sendRedirect("LoginAdmin");
        return;
    }
    Integer totalStaff = (Integer) request.getAttribute("totalStaff");
    Integer totalProduct = (Integer) request.getAttribute("totalProduct");
    Integer totalSupplier = (Integer) request.getAttribute("totalSupplier");
    Long monthlyRevenue = (Long) request.getAttribute("monthlyRevenue");
    if (totalStaff == null) totalStaff = 0;
    if (totalProduct == null) totalProduct = 0;
    if (totalSupplier == null) totalSupplier = 0;
    if (monthlyRevenue == null) monthlyRevenue = 0L;
    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Dashboard - E-commerce System</title>
        <!-- Bootstrap CDN -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <!-- Fontawesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <!-- Sidebar CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <!-- Dashboard CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/adminDashboard.css">
        <style>
            .stat-card { min-width: 220px; }
            .stat-value {
                font-size: 2rem;
                font-weight: bold;
                margin-top: 12px;
                color: #1c7ed6;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sideBar.jsp" />

            <main class="main-content">
                <header class="header d-flex justify-content-between align-items-center">
                    <div class="header-left">
                        <h1>Dashboard</h1>
                    </div>
                    <div class="user-info d-flex align-items-center">
                        <div class="user-avatar me-3">AD</div>
                        <div class="user-details me-3">
                            <h3>Admin User</h3>
                            <p>Administrator</p>
                        </div>
                        <button class="logout-btn btn btn-danger" onclick="alert('Logged out successfully!'); window.location.href = 'Logout';">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </button>
                    </div>
                </header>

                <!-- Thống kê 4 chỉ số -->
                <div class="stats-grid d-flex justify-content-between my-5 gap-4">
                    <div class="stat-card p-4 shadow rounded bg-white text-center">
                        <div class="stat-header mb-2">
                            Total Staff
                            <span class="stat-icon ms-2"><i class="fas fa-user-tie"></i></span>
                        </div>
                        <div class="stat-value"><%= totalStaff %></div>
                    </div>
                    <div class="stat-card p-4 shadow rounded bg-white text-center">
                        <div class="stat-header mb-2">
                            Total Products
                            <span class="stat-icon ms-2"><i class="fas fa-box"></i></span>
                        </div>
                        <div class="stat-value"><%= totalProduct %></div>
                    </div>
                    <div class="stat-card p-4 shadow rounded bg-white text-center">
                        <div class="stat-header mb-2">
                            Total Suppliers
                            <span class="stat-icon ms-2"><i class="fas fa-users"></i></span>
                        </div>
                        <div class="stat-value"><%= totalSupplier %></div>
                    </div>
                    <div class="stat-card p-4 shadow rounded bg-white text-center">
                        <div class="stat-header mb-2">
                            Monthly Revenue
                            <span class="stat-icon ms-2"><i class="fas fa-chart-line"></i></span>
                        </div>
                        <div class="stat-value text-success"><%= nf.format(monthlyRevenue) %> ₫</div>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>