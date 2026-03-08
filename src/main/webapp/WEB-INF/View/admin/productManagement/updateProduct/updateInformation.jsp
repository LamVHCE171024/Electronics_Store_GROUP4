<%-- 
    Document   : update
    Created on : Jun 22, 2025, 2:00:49 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.ProductDetail"%>
<%@page import="model.Suppliers"%>
<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String success = request.getParameter("success");
    String error = request.getParameter("error");
    Product product = (Product) request.getAttribute("product");
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    List<Brand> brandList = (List<Brand>) request.getAttribute("brandList");
    List<Suppliers> supList = (List<Suppliers>) request.getAttribute("supList");
    List<ProductDetail> productDetailList = (List<ProductDetail>) request.getAttribute("productDetailList");
%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link rel="stylesheet" href="Css/staffUpdateProduct1.css">
    </head>
    <body>
        <% if (product != null && productDetailList != null) {%>

        <div class="form-wrapper">
            <div class="mb-3">
                <label class="form-label">ID</label>
                <input type="text" class="form-control" name="id" required value="<%= product.getProductId()%>" readonly/>
            </div>

            <div class="mb-3">
                <label class="form-label">Product Name</label>
                <input type="text" class="form-control" name="productName" required value="<%= product.getProductName()%>"/>
            </div>

            <%
                String priceFormatted = "";
                if (product.getPrice() != null) {
                    BigDecimal price = product.getPrice();
                    Locale localeVN = new Locale("vi", "VN");
                    NumberFormat currencyVN = NumberFormat.getInstance(localeVN);
                    priceFormatted = currencyVN.format(price);
                }

            %>

            <div class="mb-3">
                <label class="form-label">Price</label>
                <input type="text" min="1" class="form-control" name="price" required value="<%= priceFormatted%>"/>
            </div>
            <%
                }
            %>

            <div class="mb-3">
                <label class="form-label">Supplier</label>
                <select class="form-control" id="suppliers" name="suppliers">
                    <option value="">-- Select Supplier --</option>
                    <% for (Suppliers sup : supList) {
                            boolean isSelected = (sup.getSupplierID() == product.getSupplierId());
                    %>
                    <option value="<%= sup.getSupplierID()%>" <%= isSelected ? "selected" : ""%>>
                        <%= sup.getName()%>
                    </option>
                    <% }%>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Category</label>
                <select class="form-control" id="category" name="category" onchange="updateBrands()">
                    <option value="">-- Select category --</option>
                    <% for (Category c : categoryList) {
                            boolean isSelected = (c.getCategoryId() == product.getCategoryId());
                    %>
                    <option value="<%= c.getCategoryId()%>" <%= isSelected ? "selected" : ""%>>
                        <%= c.getCategoryName()%>
                    </option>
                    <% }%>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Brand</label>
                <select class="form-control" id="brand" name="brand">
                    <option value="">-- Select brand --</option>
                    <% for (Brand b : brandList) {
                            boolean isSelected = (b.getBrandId() == product.getBrandId());
                    %>
                    <option value="<%= b.getBrandId()%>" <%= isSelected ? "selected" : ""%>>
                        <%= b.getBrandName()%>
                    </option>
                    <% }%>
                </select>
            </div>

            <!-- Checkboxes -->
            <div style="display: flex; gap: 30px;">
                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isFeatured" name="isFeatured" <%= product.isIsFeatured() ? "checked" : ""%>>
                    <label class="form-check-label" for="isFeatured">Is Featured</label>
                </div>

                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isNew" name="isNew" <%= product.isIsNew() ? "checked" : ""%>>
                    <label class="form-check-label" for="isNew">Is New</label>
                </div>
            </div>

            <div style="display: flex; gap: 20px;">
                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isBestSeller" name="isBestSeller" <%= product.isIsBestSeller() ? "checked" : ""%>>
                    <label class="form-check-label" for="isBestSeller">Is Best Seller</label>
                </div>

                <div class="form-check mb-2">
                    <input class="form-check-input rounded-circle" type="checkbox" id="isActive" name="isActive" <%= product.isIsActive() ? "checked" : ""%>>
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
            const selectedBrandId = "<%= product.getBrandId()%>";

            brandSelect.innerHTML = '<option value="">-- Chọn thương hiệu --</option>';

            jsBrandList.forEach(brand => {
                if (brand.categoryId.toString() === categoryId.toString()) {
                    const option = document.createElement("option");
                    option.value = brand.id;
                    option.textContent = brand.name;
                    if (brand.id.toString() === selectedBrandId.toString()) {
                        option.selected = true;
                    }
                    brandSelect.appendChild(option);
                }
            });
        }

        function previewSelectedImage(event) {
            const input = event.target;
            const preview = document.getElementById('previewImage');
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    preview.src = e.target.result;
                };
                reader.readAsDataURL(input.files[0]);
            }
        }

        window.onload = function () {
            updateBrands();
        <% if ("1".equals(success)) { %>
            Swal.fire({
                icon: 'success',
                title: 'Update Successful!',
                text: 'The product information has been updated.',
                timer: 2000,
                showConfirmButton: false
            });
        <% } else if ("1".equals(error)) { %>
            Swal.fire({
                icon: 'error',
                title: 'Update Failed!',
                text: 'Unable to update the product. Please try again.',
                timer: 2000,
                showConfirmButton: false
            });
        <% }%>
        };
    </script>

    <style>
        .form-check-input.rounded-circle {
            border-radius: 50% !important;
            width: 1.2em;
            height: 1.2em;
        }
    </style>
</html>
