<%@page import="model.CategoryDetailGroup"%>
<%@page import="model.CategoryDetail"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%
    List<CategoryDetail> categoryDetailList = (List<CategoryDetail>) request.getAttribute("categoryDetailList");
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    List<CategoryDetailGroup> categoryDetailGroup = (List<CategoryDetailGroup>) request.getAttribute("categoryDetaiGrouplList");
    int categoryId = (int) request.getAttribute("categoryId");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Category Detail</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 85%;
            margin: 0 auto;
        }

        .header {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-top: 3%;
            margin-bottom: 3%;
        }

        .header h1 {
            font-size: 40px;
            margin: 0;
            font-weight: 600;
            color: #333;
        }

        .category-card {
            display: flex;
            gap: 20px;
            margin-bottom: 3%;
            overflow-x: auto;
        }

        .category-card .card {
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 12px;
            display: inline-block;
            width: 160px;
            text-align: center;
        }

        .category-card .card.active {
            border: 2px solid #0d6efd;
        }

        .category-card img {
            max-width: 100%;
            height: 100px;
            object-fit: contain;
        }

        .category-card h2 {
            font-size: 18px;
            margin-top: 10px;
            color: #333;
        }

        .technical-specs {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .technical-specs h2 {
            font-size: 24px;
            margin-bottom: 10px;
            color: #333;
        }

        .category-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .category-table td,
        .category-table th {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        .group-header {
            background-color: #f1f1f1;
            cursor: pointer;
        }

        .group-header h2 {
            margin: 0;
            color: #333;
        }

        .arrow-icon {
            font-size: 18px;
            margin-left: 10px;
        }

        .hidden {
            display: none !important;
        }

        .no-data-message {
            text-align: center;
            padding: 10px;
            color: gray;
        }

    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Category Detail</h1>
        </div>

        <!-- Category Cards -->
        <div class="category-card">
            <%
                if (categoryList != null) {
                    for (Category cate : categoryList) {
                        if (cate.getIsActive()) {
                            boolean check = (cate.getCategoryId() == categoryId);
            %>
            <div class="card <%= check ? "active" : "" %>">
                <img src="<%= cate.getImgUrlLogo() %>" alt="Category Logo">
                <h2><%= cate.getCategoryName() %></h2>
            </div>
            <%      }
                    }
                }
            %>
        </div>

        <!-- Technical Specifications -->
        <div class="technical-specs">
            <h2>Technical Specifications</h2>
            <table class="category-table">
                <tbody>
                <%
                    if (categoryDetailGroup != null) {
                        int groupIndex = 0;
                        for (CategoryDetailGroup cateGroup : categoryDetailGroup) {
                %>
                    <!-- Group Header Row -->
                    <tr class="group-header" onclick="toggleDetails(<%= groupIndex %>)">
                        <td colspan="2">
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <h2><%= cateGroup.getNameCategoryDetailsGroup() %></h2>
                                <span class="arrow-icon" id="arrow<%= groupIndex %>">▼</span>
                            </div>
                        </td>
                    </tr>

                    <!-- Group Detail Rows -->
                    <%
                        if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                            for (CategoryDetail cateList : categoryDetailList) {
                                if (cateList.getCategoryDetailsGroupID() == cateGroup.getCategoryDetailsGroupID()) {
                    %>
                    <tr class="hidden group-details detailGroup<%= groupIndex %>">
                        <td class="category-name"><%= cateList.getCategoryDatailName() %></td>
                        <td></td>
                    </tr>
                    <%
                                }
                            }
                        }
                        groupIndex++;
                    }
                } else {
                %>
                    <tr><td colspan="2" class="no-data-message">No data</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        function toggleDetails(index) {
            const rows = document.querySelectorAll('.detailGroup' + index);
            const arrowIcon = document.getElementById('arrow' + index);

            rows.forEach(row => {
                row.classList.toggle('hidden');
            });

            arrowIcon.innerText = arrowIcon.innerText === '▼' ? '▲' : '▼';
        }
    </script>
</body>
</html>
