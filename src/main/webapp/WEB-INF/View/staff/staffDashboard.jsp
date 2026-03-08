<%@ page import="model.Account" %>
<%@ page import="model.Staff" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    Account acc = (Account) session.getAttribute("user");
    Staff staff = (Staff) session.getAttribute("staff");
    if (acc == null || acc.getRoleID() != 2 || staff == null) {
        response.sendRedirect("LoginStaff");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Staff Dashboard - TShop Store</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Staff.css">
    </head>
    <body>

        <div class="container">
            <!-- Sidebar -->
            <jsp:include page="sideBar.jsp" />

            <!-- Wrapper + Main -->
            <div class="wrapper">
                <main class="main-content">
                    <jsp:include page="header.jsp" />

                    <h1>Staff Dashboard</h1>



<div class="stats-grid" style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px;">
                        <div class="stat-card bg-secondary text-white p-3 rounded">
                            <h5>Today's Orders</h5>
                            <p style="font-size: 1.8rem; font-weight: bold;">${todayOrders != null ? todayOrders : 0}</p>
                        </div>
                        <div class="stat-card bg-success text-white p-3 rounded">
                            <h5>New Feedback</h5>
                            <p style="font-size: 1.8rem; font-weight: bold;">${newFeedback != null ? newFeedback : 0}</p>
                        </div>

                        <div class="stat-card bg-warning text-white p-3 rounded">
                            <h5>Low Stock</h5>
                            <p style="font-size: 1.8rem; font-weight: bold;">${lowStockAlerts != null ? lowStockAlerts : 0}</p>
                        </div>
                        <div class="stat-card bg-danger text-white p-3 rounded">
                            <h5>Total Customers</h5>
                            <p style="font-size: 1.8rem; font-weight: bold;">${totalCustomers != null ? totalCustomers : 0}</p>
                        </div>

                    </div>


                </main>
            </div>
        </div>
    </body>
</html>
