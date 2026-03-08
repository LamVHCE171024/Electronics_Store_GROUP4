<%-- 
    Document   : fillterProductList
    Created on : Jun 18, 2025, 9:30:11 PM
    Author     : HP - Gia Khiêm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String selectedFilter = (String) request.getAttribute("selectedFilter");
    if (selectedFilter == null) {
        selectedFilter = "All"; // giá trị mặc định
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">

    </head>

    <body>
        <div class="wrapper">
            <h1>
                Product Management
            </h1>
        </div>
        <div style="display: flex; justify-content: flex-end; gap: 12px;">
            <a class="create-btn" href="AddPromotionServlet">Set Promotion</a>
            <a class="create-btn" href="AdminCreateProduct">Create</a>
        </div>

        <form class="search-form" method="get" action="AdminProduct">
            <input
                type="text"
                name="keyword"
                placeholder="Find by name ..."
                value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>"
                />
            <button type="submit" class="search-btn">Search</button>
        </form>

        <form class="search-form" action="AdminProduct" method="get" style="">
            <div style="display: flex; align-items: center; gap: 16px; flex-wrap: wrap;">
                <label for="filter" class="fw-bold">Filter product:</label>
                <select name="filter" id="filter" onchange="this.form.submit()"
                        style="border-radius: 30px; padding: 6px 16px; font-size: 16px; border: 1px solid #ccc;">
                    <option value="All" <%= "All".equals(selectedFilter) ? "selected" : ""%>>All products</option>
                    <option value="Active" <%= "Active".equals(selectedFilter) ? "selected" : ""%>>Active</option>
                    <option value="Hidden" <%= "Hidden".equals(selectedFilter) ? "selected" : ""%>>Hidden</option>
                    <option value="Featured" <%= "Featured".equals(selectedFilter) ? "selected" : ""%>>Featured</option>
                    <option value="Bestseller" <%= "Bestseller".equals(selectedFilter) ? "selected" : ""%>>Bestseller</option>
                    <option value="New" <%= "New".equals(selectedFilter) ? "selected" : ""%>>New</option>
                    <option value="Discount" <%= "Discount".equals(selectedFilter) ? "selected" : ""%>>Discounted</option>
                </select>
            </div>
        </form>



    </body>

    <style>

        .create-btn {
            background: #22c55e;
            color: #fff;
            margin-bottom: 12px;
            float: right;
            min-width: 110px;
            border: 1.5px solid #1e9c46;
            padding: 8px 16px;
text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
            display: inline-block;
            text-align: center;
            transition: background 0.2s ease;
        }

        .create-btn:hover {
            background: #1e9c46;
            color: #fff;
        }

    </style>
</html>