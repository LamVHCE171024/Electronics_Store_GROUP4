<%-- 
    Document   : staffAddProduct
    Created on : Jun 24, 2025, 10:43:57 AM
    Author     : HP - Gia Khiêm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    </head>
    <body style="width: 100%; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: #ffffff;">
        <form action="AdminCreateProduct" method="post" enctype="multipart/form-data" style="display: flex; flex-direction: column; align-items: center;">

            <div style="margin-top: 10%; width: 66%; margin-bottom: -0.2%; background-color: #0D6EFD; border: 0.5px solid gray; padding: 0.8%; border-top-left-radius: 12px; border-top-right-radius: 12px;">
                <h4 style="color: white; margin: 0; text-align: left;">Add Product</h4>
            </div>

            <div style="width: 66%; display: flex; gap: 1.5%; border: 0.5px solid gray; justify-content: center; align-items: center; background-color: #ffffff; border-bottom: none ">

                <div style="width: 48%;">
                    <jsp:include page="/WEB-INF/View/admin/productManagement/addProduct/addProductInfo/imgProduct.jsp" />
                </div>

                <div style="width: 48%; background-color: #ffffff; padding: 10px; border-radius: 12px;">
                    <jsp:include page="/WEB-INF/View/admin/productManagement/addProduct/addProductInfo/productInfo.jsp" />
                </div>

            </div>
            <div style="text-align: right; width: 66%; border: 0.5px solid gray; padding: 1.5%; border-top: none; border-bottom-left-radius: 12px; border-bottom-right-radius: 12px;">
                <button class = "btn-success"type="submit">Next</button>
                <a style = "text-decoration: none;"href="AdminProduct" class="btn-back">Cancel</a>
            </div>

        </form>

    </body>



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
</html>
