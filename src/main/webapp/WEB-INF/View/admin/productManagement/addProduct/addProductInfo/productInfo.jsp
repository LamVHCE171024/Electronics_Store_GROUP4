<%-- 
    Document   : productInfo
    Created on : Jun 24, 2025, 10:43:34 AM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Suppliers"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Category"%>
<%@page import="model.Brand"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Product product = (Product) request.getAttribute("product");
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    List<Brand> brandList = (List<Brand>) request.getAttribute("brandList");
    List<Suppliers> supList = (List<Suppliers>) request.getAttribute("supList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="form-wrapper" style = "width: 100%">

            <div class="mb-3">
                <label class="form-label">Product Name</label>
                <input type="text" class="form-control" name="productName" required/>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Description</label>
                <input type="text" class="form-control" name="productName" required/>
            </div>

            <div class="mb-3">
                <label class="form-label">Suppliers</label>
                <select class="form-control" id="suppliers" name="suppliers" required>
                    <option value="">-- Select suppliers --</option>
                    <% for (Suppliers sup : supList) {%>
                    <option value="<%= sup.getSupplierID()%>"><%= sup.getName()%></option>
                    <% }%>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Category</label>
                <select class="form-control" id="category" name="category" onchange="updateBrands()" required>
                    <option value="">-- Select category --</option>
                    <% for (Category c : categoryList) {%>
                    <option value="<%= c.getCategoryId()%>"><%= c.getCategoryName()%></option>
                    <% }%>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Brand</label>
                <select class="form-control" id="brand" name="brand" required>
                    <option value="">-- Select brand --</option>
                </select>
            </div>

            <!-- Checkboxes -->
            <div style="display: flex; gap: 30px;">
                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isFeatured" name="isFeatured">
                    <label class="form-check-label" for="isFeatured">Is Featured</label>
                </div>

                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isNew" name="isNew">
                    <label class="form-check-label" for="isNew">Is New</label>
                </div>
            </div>

            <div style="display: flex; gap: 20px;">
                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isBestSeller" name="isBestSeller">
                    <label class="form-check-label" for="isBestSeller">Is Best Seller</label>
                </div>

                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isActive" name="isActive">
                    <label class="form-check-label" for="isActive">Is Active</label>
                </div>
            </div>
 
        </div>
    </body>

    <script>
        var jsBrandList = [];

        <% for (Brand b : brandList) {%>
        jsBrandList.push({
            id: <%= b.getBrandId()%>,
            name: "<%= b.getBrandName()%>",
            categoryId: <%= b.getCategoryID()%>
        });
        <% }%>

        function updateBrands() {
            const categoryId = document.getElementById("category").value;
            const brandSelect = document.getElementById("brand");

            // Xóa tất cả brand cũ
            brandSelect.innerHTML = '<option value="">-- Chọn thương hiệu --</option>';

            // Lọc và thêm brand mới theo category
            jsBrandList.forEach(brand => {
                if (brand.categoryId.toString() === categoryId.toString()) {
                    const option = document.createElement("option");
                    option.value = brand.id;
                    option.textContent = brand.name;
                    brandSelect.appendChild(option); // ❌ không cần .selected
                }
            });
        }

    </script>
</html>
