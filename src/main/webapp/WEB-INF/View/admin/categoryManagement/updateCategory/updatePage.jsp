<%-- 
    Document   : categoryDetail
    Created on : Jun 14, 2025, 9:52:22 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.CategoryDetailGroup"%>
<%@page import="model.CategoryDetail"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<CategoryDetail> categoryDetailList = (List<CategoryDetail>) request.getAttribute("categoryDetailList");
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    List<CategoryDetailGroup> categoryDetailGroup = (List<CategoryDetailGroup>) request.getAttribute("categoryDetaiGrouplList");
    int categoryId = (int) request.getAttribute("categoryId");
    Category category = (Category) request.getAttribute("category");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/categoryDetail.css">
        <link rel="stylesheet" href="Css/updateCategory2.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <form method="post" action="UpdateCategory">
            <!-- Truyền categoryId ẩn -->
            <input type="hidden" name="categoryId" value="<%= category.getCategoryId()%>">
            <div style="display: flex; align-items: center; gap: 10px; margin-left: 21.5%; margin-bottom: 3%;  margin-top: 3%">
                <h1 class = "display-5 fw-bold" style="font-size: 320%; margin: 0;">Category</h1>
                <span style="font-size: 120%; color: gray; margin-top: 4%;">Update Category</span>
            </div>

            <!--            <== Category name==>-->
            <% if (category != null) {%>
            <div class="divCategoryInput">
                <div class="form-row" style = "margin-bottom: 2%">
                    <label for="categoryName_<%= category.getCategoryId()%>">Category Name:</label>
                    <input type="text"
                           id="categoryName"
                           name="categoryName"
                           value="<%= category.getCategoryName()%>"
                           class="input-category" required/>
                </div>

                <div class="form-row">
                    <label for="description_<%= category.getCategoryId()%>">Description:</label>
                    <input type="text"
                           id="description"
                           name="description"
                           value="<%= category.getDescription()%>"
                           class="input-category" required/>
                </div>
            </div>
            <% } %>

            <!--            <== Category name==>-->

            <!--            <== Category detail==>-->
            <!-- Vùng hiển thị tổng -->


            <div class="category-container" style = "width: 75%; margin-top: 1%">
                <h2 style = "font-size: 140%">Technical Specifications</h2>
                <hr>
                <%
                    int groupIndex = -1;
                    if (categoryDetailGroup != null && !categoryDetailGroup.isEmpty()) {
                        groupIndex = 0;
                        for (CategoryDetailGroup cateGroup : categoryDetailGroup) {
                %>

                <!-- Nhóm tiêu đề -->
                <div class="group-header" onclick="toggleDetails(<%= groupIndex%>)">
                    <!-- ID ẩn để gửi về Servlet -->
                    <input type="hidden" name="groupId" value="<%= cateGroup.getCategoryDetailsGroupID()%>" />

                    <!-- Tên nhóm có thể chỉnh sửa -->
                    <input type="text"
                           name="groupName"
                           value="<%= cateGroup.getNameCategoryDetailsGroup()%>"
                           class="group-name-input" required/>
                </div>

                <!-- Chi tiết, ẩn ban đầu -->
                <div class="group-details" id="detailGroup<%= groupIndex%>">
                    <%
                        if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                            for (CategoryDetail cateList : categoryDetailList) {
                                if (cateList.getCategoryDetailsGroupID() == cateGroup.getCategoryDetailsGroupID()) {
                    %>
                    <div class="detail-item">
                        <input type="hidden" name="detailId" value="<%= cateList.getCategoryDetailID()%>" />
                        <input type="text"
                               name="detailName"
                               value="<%= cateList.getCategoryDatailName()%>"
                               class="detail-input" required/>
                    </div>

                    <%
                                }
                            }
                        }
                    %>
                </div>
                <%
                        groupIndex++;
                    }
                %>

                <%
                } else {
                %>
                <p class="no-data-message">No data</p>
                <%
                    }

                %>
            </div>
            <%            if (groupIndex > -1) {
            %>
            <a style = "margin-left: 81%;" href="CategoryView" class="btn btn-secondary" id="back"><i class="bi bi-arrow-return-left"></i> Back</a>
            <button type="submit" class="btn btn-primary" id="submit">
                <i class="fas fa-edit"></i> Save
            </button>

            <%
            } else {
            %>
            <a style = "margin-left: 55%;" href="CategoryView" class="btn btn-secondary" id="back"><i class="bi bi-arrow-return-left"></i> Back</a>
            <%
                }
            %>
        </form>

        <!--            <== Category detail==>-->

    </body>
</html>

<script>
    function toggleDetails(index) {
        var detailDiv = document.getElementById("detailGroup" + index);
        if (detailDiv.style.display === "none") {
            detailDiv.style.display = "block";
        } else {
            detailDiv.style.display = "none";
        }
    }
</script>

<%
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>
<script>
    window.onload = function () {
        <% if ("1".equals(success)) { %>
        Swal.fire({
            icon: 'success',
            title: 'Update Successful!',
            text: 'Your changes have been saved successfully.',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            timer: 3000
        });
        <% } else if ("1".equals(error)) { %>
        Swal.fire({
            icon: 'error',
            title: 'Error Occurred!',
            text: 'The update could not be completed. Please try again later.',
            showConfirmButton: true,
            confirmButtonText: 'Try Again',
            timer: 3000
        });
        <% } %>
    };
</script>



