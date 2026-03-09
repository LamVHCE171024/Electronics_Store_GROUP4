<%-- 
    Document   : imageProduct
    Created on : Jul 10, 2025, 12:19:25 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.ProductDetail"%>
<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Product product = (Product) request.getAttribute("product");
    List<ProductDetail> productDetail = (List<ProductDetail>) request.getAttribute("productDetailList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/imageProductDetail1.css">

    </head>
    <body>
        <%
            if (product != null) {
        %>

            <%
                if (productDetail != null && product != null) {
                    for (ProductDetail proDetail : productDetail) {
            %>

            <div style = "width: 60%; margin: 0 auto;" class="divAnhLon text-center"> 
                <img id="mainImage" src="<%= product.getImageUrl()%>" class="anhLon">
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
        <%
            } // { cua check khac null
        %>
    </body>

    <script>
        function changeMainImage(src) {
            document.getElementById("mainImage").src = src;
        }
    </script>
</html>
