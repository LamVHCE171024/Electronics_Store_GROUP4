<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page import="model.ProductDetail"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<ProductDetail> productDetail = (List<ProductDetail>) request.getAttribute("productDetailList");
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết hình ảnh sản phẩm</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <link rel="stylesheet" href="Css/imageProductDetail1.css">
    </head>

    <body>


        <div class = "divAllImg" style = "width: 100%">
            <%
                if (productDetail != null && product != null) {
                    for (ProductDetail proDetail : productDetail) {
            %>

            <div class="divAnhLon text-center col-md-12"> 
                <img style = "width: 100%" id="mainImage" src="<%= product.getImageUrl()%>" class="anhLon img-fluid main-img">
            </div>

            <div style = "" class="d-flex flex-wrap gap-2 row div4AnhNho">
                <div class="img-thumbnail col-auto" style = "margin-left: 8%;">
                    <img class ="" src="<%= proDetail.getImageUrl1()%>" onclick="changeMainImage(this.src)">
                </div>

                <div class="img-thumbnail col-auto">
                    <img class = "" src="<%= proDetail.getImageUrl2()%>" onclick="changeMainImage(this.src)">
                </div>

                <div class="img-thumbnail col-auto">
                    <img class = "" src="<%= proDetail.getImageUrl3()%>" onclick="changeMainImage(this.src)">
                </div>

                <div class="img-thumbnail col-auto">
                    <img class = "" src="<%= proDetail.getImageUrl4()%>" onclick="changeMainImage(this.src)">
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
    </body>
</html>

<script>
    function changeMainImage(src) {
        document.getElementById("mainImage").src = src;
    }
</script>

