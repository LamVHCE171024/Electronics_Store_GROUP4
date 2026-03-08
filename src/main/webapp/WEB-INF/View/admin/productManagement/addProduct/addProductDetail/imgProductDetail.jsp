<%-- 
    Document   : imgProductDetail
    Created on : Jun 29, 2025, 4:57:09 PM
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
    </head>
    <body>
        <div style="background-color: #ffffff; padding: 16px; border-radius: 15px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);">

            <!-- Ảnh lớn - KHÔNG CÓ nền trắng -->
            <div class="text-center divAnhLon"
                 style="width: 100%; margin-bottom: 16px; border-radius: 15px; max-height: 400px">
                <label for="fileInputMain" style="cursor: pointer;">
                    <img id="previewMainImage" src="<%= product.getImageUrl()%>"
                         style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 370px"
                         alt="Click to change image"
                         title="Click to change image">
                </label>
                <input type="file" name="fileMain" id="fileInputMain" accept="image/*"
                       style="display: none;" onchange="previewSelectedImage(this, 'previewMainImage')">
            </div>


            <!-- 4 ảnh nhỏ -->
            <div class="d-flex flex-wrap gap-3 row div4AnhNho" style="gap: 1%; display: flex; width: 100%; margin-top: 8px; justify-content: center;">

                <!-- Ảnh nhỏ 1 -->
                <div class="text-center"
                     style="width: 22%; border: 1px solid #ccc; border-radius: 12px; max-height: 200px">
                    <label for="fileInput1" style="cursor: pointer;">
                        <img id="previewImage1" src="https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png"
                             style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 100px"
                             alt="Click to change image"
                             title="Click to change image">
                    </label>
                    <input type="file" name="file1" id="fileInput1" accept="image/*"
                           style="display: none;" onchange="previewSelectedImage(this, 'previewImage1')">
                </div>

                <!-- Ảnh nhỏ 2 -->
                <div class="text-center"
                     style="width: 22%; border: 1px solid #ccc; border-radius: 12px; max-height: 200px">
                    <label for="fileInput2" style="cursor: pointer;">
                        <img id="previewImage2" src="https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png"
                             style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 100px"
                             alt="Click to change image"
                             title="Click to change image">
                    </label>
                    <input type="file" name="file2" id="fileInput2" accept="image/*"
                           style="display: none;" onchange="previewSelectedImage(this, 'previewImage2')">
                </div>

                <!-- Ảnh nhỏ 3 -->
                <div class="text-center"
                     style="width: 22%; border: 1px solid #ccc; border-radius: 12px; max-height: 200px">
                    <label for="fileInput3" style="cursor: pointer;">
                        <img id="previewImage3" src="https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png"
                             style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 100px"
                             alt="Click to change image"
                             title="Click to change image">
                    </label>
                    <input type="file" name="file3" id="fileInput3" accept="image/*"
                           style="display: none;" onchange="previewSelectedImage(this, 'previewImage3')">
                </div>

                <!-- Ảnh nhỏ 4 -->
                <div class="text-center"
                     style="width: 22%; border: 1px solid #ccc; border-radius: 12px; max-height: 200px">
                    <label for="fileInput4" style="cursor: pointer;">
                        <img id="previewImage4" src="https://redthread.uoregon.edu/files/original/affd16fd5264cab9197da4cd1a996f820e601ee4.png"
                             style="width: 100%; object-fit: cover; border-radius: 10px; max-height: 100px"
                             alt="Click to change image"
                             title="Click to change image">
                    </label>
                    <input type="file" name="file4" id="fileInput4" accept="image/*"
                           style="display: none;" onchange="previewSelectedImage(this, 'previewImage4')">
                </div>
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
    </script>
</html>
