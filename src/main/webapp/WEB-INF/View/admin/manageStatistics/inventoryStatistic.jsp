<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="model.InventoryStatistic"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Inventory Statistics</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
        <style>
            /* 2 nút giống Manage Statistic */
            .btn-inventory {
                background: #22c55e;
                color: #fff;
                font-weight: 700;
                border-radius: 8px;
                padding: 9px 18px;
                border: 1.5px solid #1e9c46;
                min-width: 110px;
                margin-right: 8px;
            }
            .btn-inventory:hover { background: #1e9c46; }

            .btn-revenue {
                background: #0dd6f7;
                color: #000;
                font-weight: 700;
                border-radius: 8px;
                padding: 9px 18px;
                min-width: 110px;
                border: 1.5px solid #0bbdd8;
                margin-right: 0;
            }
            .btn-revenue:hover { background: #0bbdd8; }

            
            .btn-inventory, .btn-revenue { visibility: hidden; } 

            th, td { vertical-align: middle; }
            th { text-align: center; }
            td.text-center { text-align: center !important; }
            .status-tag {
                padding: 4px 14px;
                border-radius: 999px;
                font-size: 13px;
                font-weight: 600;
                display: inline-block;
            }
            .out-of-stock { background-color: #ef4444; color: white; }
            .low-stock { background-color: #facc15; color: black; }
            .in-stock { background-color: #22c55e; color: white; }

            .search-btn {
                background: #2563eb;
                color: white;
                border: none;
                padding: 6px 14px;
                border-radius: 6px;
                font-weight: 600;
            }
            .search-btn:hover { background: #1d4ed8; }
            #categoryChart {
                width: 100% !important;
                max-width: 600px;
                height: 300px;
                margin: 0 auto;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <h1>Inventory Statistic</h1>
                    <div class="text-end" style="margin-bottom:0;">
                        <a href="InventoryStatistic" class="btn btn-inventory me-2">INVENTORY</a>
                        <a href="RevenueStatistic" class="btn btn-revenue">REVENUE</a>
                    </div>
                    <form class="search-form" action="InventoryStatistic" method="get" style="margin-bottom:21.5px; margin-top:11px;">
                        <input type="text" name="keyword" placeholder="Search by name, brand, ..." class="form-control" />
                        <button type="submit" class="search-btn">Search</button>
                    </form>

                    <%
                        String message = (String) request.getAttribute("message");
                        ArrayList<InventoryStatistic> stats = (ArrayList<InventoryStatistic>) request.getAttribute("inventoryStatistics");
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        List<String> fixCategories = Arrays.asList("Air Conditioners", "Washing Machine", "Television", "Fridge", "Rice Cookers");
                        Map<String, Integer> categoryStock = new LinkedHashMap<>();
                        for (String cat : fixCategories) categoryStock.put(cat, 0);

                        if (stats != null) {
                            for (InventoryStatistic item : stats) {
                                int stockQty = item.getImportQuantity() - item.getSoldQuantity();
                                String cat = item.getCategoryName();
                                if (categoryStock.containsKey(cat)) categoryStock.put(cat, categoryStock.get(cat) + stockQty);
                            }
                        }

                        StringBuilder labelsJS = new StringBuilder();
                        StringBuilder dataJS = new StringBuilder();
                        for (String cat : fixCategories) {
                            labelsJS.append("\"").append(cat).append("\",");
                            dataJS.append(categoryStock.get(cat)).append(",");
                        }
                        if (labelsJS.length() > 0) labelsJS.setLength(labelsJS.length() - 1);
                        if (dataJS.length() > 0) dataJS.setLength(dataJS.length() - 1);
                    %>

                    <table aria-label="Suppliers table">
                        <thead class="table-primary">
                            <tr>
                                <th>Import ID</th>
                                <th>Category</th>
                                <th>Brand</th>
                                <th>Product</th>
                                <th>Import Qty</th>
                                <th>Sold Qty</th>
                                <th>Stock Qty</th>
                                <th>Supplier</th>
                                <th>Import Date</th>
                                <th>Import Price</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (stats != null && !stats.isEmpty()) {
                                    for (InventoryStatistic item : stats) {
                                        int importQty = item.getImportQuantity();
                                        int soldQty = item.getSoldQuantity();
                                        int stockQty = importQty - soldQty;
                                        String statusClass = stockQty == 0 ? "out-of-stock" : (stockQty <= 5 ? "low-stock" : "in-stock");
                                        String statusText = stockQty == 0 ? "OUT OF STOCK" : (stockQty <= 5 ? "LOW STOCK" : "IN STOCK");
                            %>
                            <tr>
                                <td class="text-center"><%= item.getImportId() %></td>
                                <td><%= item.getCategoryName()%></td>
                                <td><%= item.getBrandName()%></td>
                                <td><%= item.getFullName()%></td>
                                <td class="text-center"><%= importQty%></td>
                                <td class="text-center"><%= soldQty%></td>
                                <td class="text-center"><%= stockQty%></td>
                                <td><%= item.getSupplierName()%></td>
                                <td><%= item.getImportDate()%></td>
                                <td>
                                    <%
                                        Object priceObj = item.getProductImportPrice();
                                        double price = 0.0;
                                        if (priceObj != null) {
                                            try {
                                                price = Double.parseDouble(priceObj.toString());
                                            } catch (NumberFormatException e) {
                                                price = 0.0;
                                            }
                                        }
                                        out.print(price > 0 ? currencyFormat.format(price) : "-");
                                    %>
                                </td>
                                <td class="text-center">
                                    <span class="status-tag <%= statusClass%>"><%= statusText%></span>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="11" class="text-center">No inventory data found.</td>
                            </tr>
                            <% }%>
                        </tbody>
                    </table>

                    <canvas id="categoryChart"></canvas>
                    <div class="text-center mt-2 text-secondary fw-bold" style="font-size: 15px;">
                        Total stock quantity per category
                    </div>
                </main>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            const barColors = [
                "#60a5fa", // Air Conditioners
                "#fbbf24", // Washing Machine
                "#34d399", // Television
                "#f472b6", // Fridge
                "#c084fc"  // Rice Cookers
            ];
            const labels = [<%= labelsJS.toString()%>];
            const data = [<%= dataJS.toString()%>];
            const ctx = document.getElementById('categoryChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        data: data,
                        backgroundColor: barColors,
                        borderRadius: 16,
                        borderSkipped: false,
                        barPercentage: 0.6,
                        categoryPercentage: 0.6
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: true,
                    animation: false,
                    plugins: {legend: {display: false}},
                    scales: {
                        x: {
                            grid: {display: false},
                            ticks: {color: "#6b7280", font: {size: 14, weight: '500'}}
                        },
                        y: {
                            grid: {display: false},
                            ticks: {display: false},
                            beginAtZero: true,
                            min: 0
                        }
                    }
                }
            });
        </script>
    </body>
</html>
