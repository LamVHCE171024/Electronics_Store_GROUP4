<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.RevenueStatistic"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Revenue Statistic</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
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
                margin-right: 0;
            }
            .btn-revenue:hover {
                background: #0bbdd8;
            }

            .btn-inventory,
.btn-revenue,
.search-form {
    visibility: hidden;
}

            canvas {
                width: 100% !important;
                height: 300px !important;
            }
            .chart-row {
                display: flex;
                flex-wrap: wrap;
                gap: 32px;
                margin-top: 32px;
            }
            .chart-box {
                flex: 1 1 0;
                background: #fff;
                padding: 16px;
                border-radius: 16px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.05);
            }
            .chart-box h5 {
                text-align: center;
                margin-bottom: 16px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <h1>Revenue Statistic</h1>
                    <div class="text-end" style="margin-bottom:0;">
                        <a href="InventoryStatistic" class="btn btn-inventory me-2">INVENTORY</a>
                        <a href="RevenueStatistic" class="btn btn-revenue">REVENUE</a>
                    </div>
                   
                    <form class="search-form" action="RevenueStatistic" method="get" autocomplete="off" style="margin-bottom:21.5px; margin-top:11px;">
                        <input type="text" class="form-control" name="keyword" placeholder="Search product..." />
                        <button type="submit" class="search-btn">Search</button>
                    </form>


                    <% String message = (String) request.getAttribute("message"); %>
                    <% if (message != null) {%>
                    <div class="alert alert-danger text-center"><%= message%></div>
                    <% } %>

                    <%
                        String statType = (String) request.getAttribute("statType");
                        ArrayList<RevenueStatistic> stats = (ArrayList<RevenueStatistic>) request.getAttribute("revenueStatistics");
                        ArrayList<RevenueStatistic> chartDayData = (ArrayList<RevenueStatistic>) request.getAttribute("chartDayData");
                        ArrayList<RevenueStatistic> chartMonthData = (ArrayList<RevenueStatistic>) request.getAttribute("chartMonthData");

                        List<String> fixCategories = Arrays.asList("Air Conditioners", "Washing Machine", "Television", "Fridge", "Rice Cookers");
                        Map<String, Long> categoryRevenueDay = new LinkedHashMap<>();
                        Map<String, Long> categoryRevenueMonth = new LinkedHashMap<>();
                        for (String cat : fixCategories) {
                            categoryRevenueDay.put(cat, 0L);
                            categoryRevenueMonth.put(cat, 0L);
                        }
                        if (chartDayData != null) {
                            for (RevenueStatistic r : chartDayData) {
                                if (categoryRevenueDay.containsKey(r.getCategoryName())) {
                                    categoryRevenueDay.put(r.getCategoryName(), r.getTotalRevenue());
                                }
                            }
                        }
                        if (chartMonthData != null) {
                            for (RevenueStatistic r : chartMonthData) {
                                if (categoryRevenueMonth.containsKey(r.getCategoryName())) {
                                    categoryRevenueMonth.put(r.getCategoryName(), r.getTotalRevenue());
                                }
                            }
                        }

                        StringBuilder dayLabels = new StringBuilder();
                        StringBuilder dayData = new StringBuilder();
                        StringBuilder monthLabels = new StringBuilder();
                        StringBuilder monthData = new StringBuilder();
                        for (String cat : fixCategories) {
                            dayLabels.append("\"").append(cat).append("\",");
                            dayData.append(categoryRevenueDay.get(cat)).append(",");
                            monthLabels.append("\"").append(cat).append("\",");
                            monthData.append(categoryRevenueMonth.get(cat)).append(",");
                        }
                        if (dayLabels.length() > 0) {
                            dayLabels.setLength(dayLabels.length() - 1);
                        }
                        if (dayData.length() > 0) {
                            dayData.setLength(dayData.length() - 1);
                        }
                        if (monthLabels.length() > 0) {
                            monthLabels.setLength(monthLabels.length() - 1);
                        }
                        if (monthData.length() > 0)
                            monthData.setLength(monthData.length() - 1);
                    %>

                    <table aria-label="Suppliers table">
                        <thead class="table-primary">
                            <tr>
                                <th style="text-align:center; vertical-align:middle;">Time Period</th>
                                <th style="text-align:center; vertical-align:middle;">Total Orders</th>
                                <th style="text-align:center; vertical-align:middle;">Total Products Sold</th>
                                <th style="text-align:center; vertical-align:middle;">Total Revenue (₫)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (stats != null && !stats.isEmpty()) {
                                    for (RevenueStatistic r : stats) {
                                        String label = "N/A";
                                        if ("month".equals(statType)) {
                                            label = String.format("%02d/%d", r.getOrderMonth(), r.getOrderYear());
                                        } else if ("day".equals(statType)) {
                                            label = new java.text.SimpleDateFormat("dd/MM/yyyy").format(r.getOrderDate());
                                        } else if ("category".equals(statType)) {
                                            label = r.getCategoryName();
                                        }
                            %>
                            <tr>
                                <td style="text-align:center; vertical-align:middle;"><%= label%></td>
                                <td style="text-align:center; vertical-align:middle;"><%= r.getTotalOrder()%></td>
                                <td style="text-align:center; vertical-align:middle;"><%= r.getTotalProductsSold()%></td>
                                <td style="text-align:center; vertical-align:middle;"><%= String.format("%,d ₫", r.getTotalRevenue())%></td>
                            </tr>
                            <% }
                            } else { %>
                            <tr><td colspan="4">No revenue data available.</td></tr>
                            <% }%>
                        </tbody>
                    </table>

                    <div class="chart-row">
                        <div class="chart-box">
                            <h5>Revenue by Category (Latest Day)</h5>
                            <canvas id="dayChart"></canvas>
                        </div>
                        <div class="chart-box">
                            <h5>Revenue by Category (Latest Month)</h5>
                            <canvas id="monthChart"></canvas>
                        </div>
                    </div>

                    <script>
                        const dayLabels = [<%= dayLabels.toString()%>];
                        const dayData = [<%= dayData.toString()%>];
                        const monthLabels = [<%= monthLabels.toString()%>];
                        const monthData = [<%= monthData.toString()%>];

                        const chartOpts = {
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {display: false},
                                title: {display: false}
                            },
                            layout: {padding: 10},
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    ticks: {
                                        callback: (v) => v.toLocaleString() + ' ₫'
                                    }
                                }
                            }
                        };

                        new Chart(document.getElementById('dayChart').getContext('2d'), {
                            type: 'bar',
                            data: {
                                labels: dayLabels,
                                datasets: [{
                                        data: dayData,
                                        backgroundColor: '#61a5e8'
                                    }]
                            },
                            options: {
                                ...chartOpts,
                                animation: false
                            }
                        });

                        new Chart(document.getElementById('monthChart').getContext('2d'), {
                            type: 'bar',
                            data: {
                                labels: monthLabels,
                                datasets: [{
                                        data: monthData,
                                        backgroundColor: '#61a5e8'
                                    }]
                            },
                            options: {
                                ...chartOpts,
                                animation: false
                            }
                        });
                    </script>

                </main>
            </div>
        </div>
    </body>
</html>
