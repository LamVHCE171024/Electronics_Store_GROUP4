<%-- 
    Document   : adminAddProductDetail
    Created on : Jun 29, 2025, 4:56:36 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int categoryId = (int) request.getAttribute("categoryId");
    Product product = (Product) request.getAttribute("product");
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style = "width: 100%">
            <form action="AdminAddProductDetail?categoryId=<%= categoryId%>&productId=<%= product.getProductId()%>" method="post" enctype="multipart/form-data" style="display: flex; flex-direction: column; align-items: center;">
                <div style="margin-top: 5%; width: 68.4%; margin-bottom: -0.2%; background-color: #0D6EFD; border: 0.5px solid gray; padding: 0.8%; border-top-left-radius: 12px; border-top-right-radius: 12px;">
                    <h4 style="color: white; margin: 0; text-align: left;">Add Product</h4>
                </div>

                <div style="width: 70%; display: flex; gap: 1.5%; border: 0.5px solid gray; justify-content: center; align-items: center; background-color: #ffffff; border-bottom: none ">

                    <div style="width: 40%;">
                        <jsp:include page="/WEB-INF/View/admin/productManagement/addProduct/addProductDetail/imgProductDetail.jsp" />
                    </div>

                    <div style="width: 58%; background-color: #ffffff; padding: 10px; border-radius: 12px;">
                        <jsp:include page="/WEB-INF/View/admin/productManagement/addProduct/addProductDetail/productDetail.jsp" />
                    </div>

                </div>
                <div style="text-align: right; width: 67%; border: 0.5px solid gray; padding: 1.5%; border-top: none; border-bottom-left-radius: 12px; border-bottom-right-radius: 12px;">
                    <button class = "btn-success"type="submit">Create</button>
                    <a style = "text-decoration: none;"href="DeleteProductWhenCancel?productId=<%=product.getProductId()%>" class="btn-back">Cancel</a>
                </div>

            </form>
        </div>

        <%
            String success = request.getParameter("success");
            String error = request.getParameter("error");
        %>

        <script>
            window.onload = function () {
            <% if ("1".equals(success)) { %>
                Swal.fire({
                    icon: 'success',
                    title: 'Deleted!',
                    text: 'The product has been hidden.',
                    timer: 2000
                });
            <% } else if ("1".equals(error)) { %>
                Swal.fire({
                    icon: 'error',
                    title: 'Failed!',
                    text: 'Could not hide the product.',
                    timer: 2000
                });
            <% }%>
            };
        </script>

        <style>
            .btn-success {
                background-color: #198754;
                color: white;
                border: 1px solid #198754;
                padding: 10px 20px;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
            }
            .btn-success:hover {
                background-color: #157347;
                border-color: #146c43;
            }

            .btn-back {
                color: #fff;
                background-color: #6c757d;
                border: 1px solid #6c757d;
                padding: 10px 20px;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-back:hover {
                background-color: #5c636a;
                border-color: #565e64;
            }

            .btn-success, .btn-back {
                padding: 8px 16px;  /* trước là 10px 20px */
                font-size: 14px;
            }

        </style>
    </body>
</html>
