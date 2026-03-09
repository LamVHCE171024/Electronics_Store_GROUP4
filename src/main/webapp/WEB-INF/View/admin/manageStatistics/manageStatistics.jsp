<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="model.InventoryStatistic"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Statistics</title>

        <!-- Bootstrap & FontAwesome -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>

        <!-- Shared Sidebar & Custom Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">

        <style>
            /* Buttons */
            .btn-inventory {
                background: #22c55e;
                color: #fff;
                font-weight: 700;
                border-radius: 8px;
                padding: 9px 18px;
                border: 1.5px solid #1e9c46;
                min-width: 110px;
            }
            .btn-inventory:hover {
                background: #1e9c46;
            }

            .btn-revenue {
                background: #0dd6f7;
                color: #000;
                font-weight: 700;
                border-radius: 8px;
                padding: 9px 18px;
                min-width: 110px;
                border: 1.5px solid #0bbdd8;
            }
            .btn-revenue:hover {
                background: #0bbdd8;
            }

            /* Column width */
            th.product-id-col, td.product-id-col {
                width: 90px;
                text-align: center;
                white-space: nowrap;
            }

            th.status-col, td.status-col {
                width: 140px;
                text-align: center;
            }

            /* Status tags */
            .status-tag {
                padding: 4px 10px;
                border-radius: 999px;
                font-size: 13px;
                font-weight: 600;
                display: inline-block;
            }

            .status-warning {
                background-color: #facc15;
                color: #000;
            }

            .status-inactive {
                background-color: #ef4444;
                color: white;
            }

            .status-active {
                background-color: #22c55e;
                color: white;
            }

            

        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <h1>Manage Statistic</h1>

                    <!-- Nút INVENTORY/REVENUE, không margin-bottom -->
                    <div class="text-end" style="margin-bottom:0;">
                        <a href="InventoryStatistic" class="btn btn-inventory me-2">INVENTORY</a>
                        <a href="RevenueStatistic" class="btn btn-revenue">REVENUE</a>
                    </div>
                    <!-- Search form đúng chuẩn khoảng cách -->
                    <form class="search-form" action="ManageStatistic" method="get" autocomplete="off" style="margin-bottom:21.5px; margin-top:11px;">
                        <input type="text" class="form-control" name="keyword" placeholder="Search product..." />
                        <button type="submit" class="search-btn">Search</button>
                    </form>

                    <%
                        String message = (String) request.getAttribute("message");
                        ArrayList<InventoryStatistic> stats = (ArrayList<InventoryStatistic>) request.getAttribute("inventoryStatistics");
                        if (message != null) {
                    %>
                    <div class="alert alert-danger text-center my-3"><%= message%></div>
                    <% } %>

                    <table aria-label="Suppliers table">
                        <thead class="table-primary">
                            <tr>
                                <th class="product-id-col">Product ID</th>
                                <th>Product Name</th>
                                <th>Category</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th class="status-col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (stats != null && !stats.isEmpty()) {
                                    for (InventoryStatistic item : stats) {
                                        int qty = item.getStockQuantity();
                                        String statusClass = qty == 0 ? "status-inactive"
                                                : (qty <= 5 ? "status-warning" : "status-active");
                                        String statusText = qty == 0 ? "OUT OF STOCK"
                                                : (qty <= 5 ? "LOW STOCK" : "IN STOCK");
                            %>
                            <tr>
                                <td class="product-id-col"><%= item.getProductId()%></td>
                                <td><%= item.getFullName()%></td>
                                <td><%= item.getCategoryName()%></td>
                                <td><%= item.getStockQuantity()%></td>
                                <td>
                                    <%= item.getProductImportPrice() != null
                                            ? String.format("%,d", item.getProductImportPrice().intValue())
                                            : "-"%>
                                </td>
                                <td class="status-col">
                                    <span class="status-tag <%= statusClass%>"><%= statusText%></span>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="6" class="text-center">No data found.</td>
                            </tr>
                            <% }%>
                        </tbody>
                    </table>
                </main>
            </div>
        </div>
    </body>
</html>
