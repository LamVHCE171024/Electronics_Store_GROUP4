<%-- 
    Document   : imgProduct
    Created on : Jun 24, 2025, 10:43:21 AM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <!-- Khung ảnh cố định không bị nhảy -->
        <div style="
             width: 100%;
             height: 300px;
             border-radius: 15px;
             overflow: hidden;
             background-color: #f8f9fa;
             display: flex;
             justify-content: center;
             align-items: center;
             padding: 10px;
             ">
            <label for="fileInputMain" style="width: 100%; cursor: pointer; margin: 0; display: block;">
                <img id="previewMainImage"
                     src="https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png"
                     style="
                     width: 100%;
                     max-height: 100%;
                     max-width: 100%;
                     object-fit: contain;
                     border-radius: 10px;
                     display: block;
                     "
                     alt="Click to change image"
                     title="Click to change image">
            </label>

            <input type="file" name="fileMain" id="fileInputMain" accept="image/*"
                   style="display: none;" onchange="previewSelectedImage(this, 'previewMainImage')">
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
            const urlParams = new URLSearchParams(window.location.search);
            const success = urlParams.get("success");
            const error = urlParams.get("error");
            if (success === "1") {
                Swal.fire({
                    icon: 'success',
                    title: 'Update Successful!',
                    text: 'The product information has been updated.',
                    timer: 2000,
                    showConfirmButton: false
                });
            }

            if (error === "1") {
                Swal.fire({
                    icon: 'error',
                    title: 'Update Failed!',
                    text: 'Unable to update the product. Please try again.',
                    timer: 2000,
                    showConfirmButton: false
                });
            }
        };

        function toggleDetails(index) {
            const detailGroup = document.getElementById("detailGroup" + index);
            const arrowIcon = document.getElementById("arrow" + index);

            if (detailGroup.classList.contains("hidden")) {
                detailGroup.classList.remove("hidden");
                arrowIcon.innerText = "▲"; // hoặc dùng ▾ nếu thích
            } else {
                detailGroup.classList.add("hidden");
                arrowIcon.innerText = "▼";
            }
        }
    </script>
</html>
