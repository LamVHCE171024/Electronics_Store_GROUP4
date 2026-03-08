<%-- 
    Document   : productDetail
    Created on : Jun 19, 2025, 4:39:39 PM
    Author     : HP - Minh
--%>


<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Product product = (Product) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>


    <body>
        <div class = "container">
            <div  style="display: flex; align-items: center; gap: 10px; margin-bottom: 3%;">

                <h1 class = "display-5 fw-bold" style="font-size: 320%; margin: 0;">Product Management</h1>
                <span style="font-size: 120%; color: gray; margin-top: 4%;">View Product Detail</span>
            </div>
            <h3><%= product.getProductName()%></h3>

        </div>
    </body>
</html>
