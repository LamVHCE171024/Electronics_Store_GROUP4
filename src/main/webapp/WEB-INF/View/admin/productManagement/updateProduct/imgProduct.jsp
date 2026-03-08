<%-- 
    Document   : imgProduct
    Created on : Jul 30, 2025, 7:45:09 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.util.List"%>
<%@page import="model.ProductDetail"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Product product = (Product) request.getAttribute("product");
    List<ProductDetail> productDetailList = (List<ProductDetail>) request.getAttribute("productDetailList");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <div style="padding: 10px 20px; max-height: 320px; overflow: hidden;">
            <label for="fileInputMain" style="cursor: pointer; display: block;">
                <img id="previewImageMain" src="<%= product.getImageUrl()%>"
                     style="width: 100%; max-height: 320px; object-fit: contain; border-radius: 10px; display: block;"
                     alt="Click to change image"
                     title="Click to change image">
            </label>

            <input type="file" name="fileMain" id="fileInputMain" accept="image/*"
                   style="display: none;" onchange="previewSelectedImage(event, 'previewImageMain')">
        </div>

        <%
            if (productDetailList != null) {
                ProductDetail proDetail = productDetailList.get(0);
        %>


        <div class="d-flex flex-wrap gap-3 row" style="justify-content: center;">

            <!-- Ảnh nhỏ 1 -->
            <div class="img-thumbnail text-center" style="border: 1px solid #ccc; border-radius: 10px; max-height: 500px; width: 20%;">
                <label for="fileInput1" style="cursor: pointer; width: 100%;">
                    <img id="previewImage1" src="<%= proDetail.getImageUrl1()%>"
                         style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 465px"
                         alt="Click to change image"
                         title="Click to change image">
                </label>
                <input type="file" name="file1" id="fileInput1" accept="image/*"
                       style="display: none;" onchange="previewSelectedImage(event, 'previewImage1')">
            </div>

            <!-- Ảnh nhỏ 2 -->
            <div class=" img-thumbnail text-center"
                 style="border: 1px solid #ccc; border-radius: 10px; max-height: 500px; width: 20%;">
                <label for="fileInput2" style="cursor: pointer;">
                    <img id="previewImage2" src="<%= proDetail.getImageUrl2()%>"
                         style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 465px"
                         alt="Click to change image"
                         title="Click to change image">
                </label>
                <input type="file" name="file2" id="fileInput2" accept="image/*"
                       style="display: none;" onchange="previewSelectedImage(event, 'previewImage2')">
            </div>

            <!-- Ảnh nhỏ 3 -->
            <div class="img-thumbnail text-center"
                 style="border: 1px solid #ccc; border-radius: 10px; max-height: 500px; width: 20%;">
                <label for="fileInput3" style="cursor: pointer;">
                    <img id="previewImage3" src="<%= proDetail.getImageUrl3()%>"
                         style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 465px"
                         alt="Click to change image"
                         title="Click to change image">
                </label>
                <input type="file" name="file3" id="fileInput3" accept="image/*"
                       style="display: none;" onchange="previewSelectedImage(event, 'previewImage3')">
            </div>

            <!-- Ảnh nhỏ 4 -->
            <div class="img-thumbnail text-center"
                 style="border: 1px solid #ccc; border-radius: 10px; max-height: 500px; width: 20%;">
                <label for="fileInput4" style="cursor: pointer;">
                    <img id="previewImage4" src="<%= proDetail.getImageUrl4()%>"
                         style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 465px"
                         alt="Click to change image"
                         title="Click to change image">
                </label>
                <input type="file" name="file4" id="fileInput4" accept="image/*"
                       style="display: none;" onchange="previewSelectedImage(event, 'previewImage4')">
            </div>
        </div>
        <%
            }
        %>
    </body>
</html>
