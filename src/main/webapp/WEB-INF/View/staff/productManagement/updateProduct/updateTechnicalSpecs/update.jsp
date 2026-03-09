<%-- 
    Document   : update
    Created on : Jun 22, 2025, 2:00:49 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.ProductDetail"%>
<%@page import="model.CategoryDetail"%>
<%@page import="model.CategoryDetailGroup"%>
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
    List<CategoryDetailGroup> categoryDetailGroupList = (List<CategoryDetailGroup>) request.getAttribute("categoryGroupList");
    List<CategoryDetail> categoryDetailList = (List<CategoryDetail>) request.getAttribute("categoryDetailList");
    List<ProductDetail> productDetailList = (List<ProductDetail>) request.getAttribute("productDetailList");
    Product product = (Product) request.getAttribute("product");
%>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h3><%= product.getProductName()%></h3>
            <div class = "divAllImg col-md-6">
                <%
                    if (productDetailList != null && product != null) {
                        for (ProductDetail proDetail : productDetailList) {
                %>
                <div class="col-md-12">

                    <div class="col-md-12 text-center divAnhLon"
                         style="margin-top: 2%; border-radius: 15px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); background-color: #ffffff; max-height: 400px">
                        <label for="fileInputMain" style="cursor: pointer;">
                            <img id="previewMainImage" src="<%= product.getImageUrl()%>"
                                 style="width: 100%; margin-top: 2%; object-fit: cover; border-radius: 10px; max-height: 370px"
                                 alt="Click to change image"
                                 title="Click to change image">
                        </label>
                        <input type="file" name="fileMain" id="fileInputMain" accept="image/*" style="display: none;" onchange="previewSelectedImage(this, 'previewMainImage')">
                    </div>

                    <div class="d-flex flex-wrap gap-2 row div4AnhNho">

                        <div class="col-md-2 text-center"
                             style="margin-top: 2%; border-radius: 15px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); background-color: #ffffff; max-height: 500px">
                            <label for="fileInput1" style="cursor: pointer;">
                                <img id="previewImage1" src="<%= proDetail.getImageUrl1()%>"
                                     style="width: 100%; margin-top: 2%; object-fit: cover; border-radius: 10px; max-height: 465px"
                                     alt="Click to change image"
                                     title="Click to change image">
                            </label>
                            <input type="file" name="file1" id="fileInput1" accept="image/*" style="display: none;" onchange="previewSelectedImage(this, 'previewImage1')">
                        </div>

                        <div class="col-md-2 text-center"
                             style="margin-top: 2%; border-radius: 15px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); background-color: #ffffff; max-height: 500px">
                            <label for="fileInput2" style="cursor: pointer;">
                                <img id="previewImage2" src="<%= proDetail.getImageUrl2()%>"
                                     style="width: 100%; margin-top: 2%; object-fit: cover; border-radius: 10px; max-height: 465px"
                                     alt="Click to change image"
                                     title="Click to change image">
                            </label>
                            <input type="file" name="file2" id="fileInput2" accept="image/*" style="display: none;" onchange="previewSelectedImage(this, 'previewImage2')">
                        </div>

                        <div class="col-md-2 text-center"
                             style="margin-top: 2%; border-radius: 15px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); background-color: #ffffff; max-height: 500px">
                            <label for="fileInput3" style="cursor: pointer;">
                                <img id="previewImage3" src="<%= proDetail.getImageUrl3()%>"
                                     style="width: 100%; margin-top: 2%; object-fit: cover; border-radius: 10px; max-height: 465px"
                                     alt="Click to change image"
                                     title="Click to change image">
                            </label>
                            <input type="file" name="file3" id="fileInput3" accept="image/*" style="display: none;" onchange="previewSelectedImage(this, 'previewImage3')">
                        </div>

                        <div class="col-md-2 text-center divAnhLon"
                             style="margin-top: 2%; border-radius: 15px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); background-color: #ffffff; max-height: 500px">
                            <label for="fileInput4" style="cursor: pointer;">
                                <img id="previewImage4" src="<%= proDetail.getImageUrl4()%>"
                                     style="width: 100%; margin-top: 2%; object-fit: cover; border-radius: 10px; max-height: 465px"
                                     alt="Click to change image"
                                     title="Click to change image">
                            </label>
                            <input type="file" name="file4" id="fileInput4" accept="image/*" style="display: none;" onchange="previewSelectedImage(this, 'previewImage4')">
                        </div>
                    </div>
                </div>
                <%
                        break;
                    }
                } else {
                %>
                <div class="alert alert-warning">Không có dữ liệu sản phẩm!</div>
                <% }
                %>
            </div>
        </div>
    </body>

    <script>

        function previewSelectedImage(input, imgId) {
            const preview = document.getElementById(imgId);
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
