<%-- 
    Document   : filterProduct
    Created on : Jul 12, 2025, 6:02:22 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Product> productList = (List<Product>) request.getAttribute("productList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <style>
            body {
                background-color: #F2F4F7 !important;
                padding: 0px !important;
            }

            .filterAndBrand {
                border-radius: 12px;
            }

            .p {
                font-size: 14px;
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 0; /* xoá khoảng trắng mặc định */
                padding: 2px 6px;
                border-radius: 8px;
                color: #0567da;
                ;
            }

            .divGoiY {
                display: flex;
                gap: 5%;
                background-color: #EAECF0;
                padding: 2px;
                justify-content: center; /* căn giữa theo chiều ngang */
                align-items: center;
            }

            .divSoLuong a {
                text-decoration: none;
            }

            .home{
                font-size: 14px;
                font-weight: 400;
                line-height: 20px;
                text-align: left;
                color: rgba(152, 162, 179, 1);
            }

            .soLuong {
                color: rgba(52, 64, 84, 1);
                font-size: 14px;
                font-weight: 400;
                line-height: 20px;
                text-align: left;
            }
        </style>
    </head>

    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />

        <div class = "divGoiY">
            <p class="p">Air Conditioning</p>
            <p class="p">Refrigerator</p>
            <p class="p">Television</p>
            <p class="p">Fridge</p>
            <p class="p">Rice Cookers</p>
        </div>

        <div class = "divSoLuong" style = "padding: 5px;">
            <a class = "home" href = "Home">Home ></a>
            <a class = "soLuong" href=""><%=(productList != null) ? productList.size() : 0%></a>
            <a class = "soLuong" href=""><%=(productList != null) ? productList.get(0).getCategoryName() : 0%></a>
        </div>

        <div class = "banner">
            <jsp:include page="/WEB-INF/View/customer/productManagement/sortProduct/banner.jsp" />
        </div>

        <div class = "container-fluid" style = "background-color: #ffffff; border-radius: 12px; margin-top: 1%; width: 98%;" >
            <div class = "filterAndBrand" style = "display: flex; gap: 1%;">
                <jsp:include page="/WEB-INF/View/customer/productManagement/sortProduct/filterAndBrand.jsp" />
                <jsp:include page="/WEB-INF/View/customer/productManagement/filterProduct/brands.jsp" />
            </div>

            <div class = "productList" style = "width: 100%; margin-top: 1%; ">
                <jsp:include page="/WEB-INF/View/customer/productManagement/sortProduct/productList.jsp" />
            </div>
        </div>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />

    </body>
</html>
