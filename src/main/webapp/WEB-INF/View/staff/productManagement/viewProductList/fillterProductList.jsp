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
    </head>
    
    <body>
        <div>
            <h1 class = "col-md-9 fw-bold display-5" style = "margin-left: 18.9%; margin-top: 2%">
                Product Management
            </h1>
        </div>

        <div style="display: flex; align-items: center; margin-top: 3%;">
            
            <p style="margin-left: 22%; color: black; font-weight: 500; font-size: 200%;"><%= (selectedFilter != null && !selectedFilter.isEmpty()) ? (selectedFilter + " ") : "All" %>Product</p><!-- comment -->
            
            <form style="margin-left: 15%; margin-bottom: 1%; margin-right: 2%" action="StaffProduct" method="get">
                <label for="filter" class="form-label fw-bold me-2">Filter Products:</label>
                <select name="filter" id="filter" class="form-select" onchange="this.form.submit()" 
                        style="width: 250px; display: inline-block;">
                    <option value="All" <%= "All".equals(selectedFilter) ? "selected" : ""%>>All products</option>
                    <option value="Active" <%= "Active".equals(selectedFilter) ? "selected" : ""%>>Active products</option>
                    <option value="Hidden" <%= "Hidden".equals(selectedFilter) ? "selected" : ""%>>Hidden products</option>
                    <option value="Featured" <%= "Featured".equals(selectedFilter) ? "selected" : ""%>>Featured products</option>
                    <option value="Bestseller" <%= "Bestseller".equals(selectedFilter) ? "selected" : ""%>>Bestseller products</option>
                    <option value="New" <%= "New".equals(selectedFilter) ? "selected" : ""%>>New products</option>
                    <option value="Discount" <%= "Discount".equals(selectedFilter) ? "selected" : ""%>>Discounted products</option>
                </select>
            </form>

            <a href="CreateCategory" 
               class="btn btn-success d-flex align-items-center shadow rounded-pill px-3 py-2"
               style="width: fit-content; text-decoration: none; margin-bottom: 1%;">
                <img src="https://cdn0.iconfinder.com/data/icons/round-ui-icons/512/add_blue.png" 
                     alt="Add icon" 
                     style="width: 20px; height: 20px; margin-right: 5px; filter: brightness(0) invert(1);">
                <span style="color: white; font-weight: 500; font-size: 16px;">Add</span>
            </a>
        </div>


    </body>
</html>
