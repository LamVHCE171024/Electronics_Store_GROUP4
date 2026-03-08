<%-- 
    Document   : viewCategoryList
    Created on : Jun 13, 2025, 11:27:19 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">

    </head>
    <body>
        <div class="wrapper"> 
            <jsp:include page="/WEB-INF/View/admin/categoryManagement/deleteCategory/deleteCategory.jsp" />

            <main class="main-content">

                <h1>
                    Category Management
                </h1>
                <button class="create-btn" onclick="location.href = 'CreateCategory'">Create</button>
                
                <%
                    if (categoryList != null) {
                %>
                <table aria-label="Suppliers table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Category Name</th>
                            <th>Description</th>
                            <th>Created Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>                
                        <%
                            for (Category cate : categoryList) {
                                if (cate.getIsActive()) {
                        %>
                        <tr>
                            <td><%= cate.getCategoryId()%></td>
                            <td><%= cate.getCategoryName()%></td>
                            <td><%= cate.getDescriptionCategory()%></td>
                            <td><%= cate.getCreatedAt()%></td>


                            <td>
                                <a href="CategoryDetail?categoryId=<%= cate.getCategoryId()%>" class="btn btn-primary" style="color: white;"><i class="bi bi-tools"></i> Detail</a>
                                <a href="UpdateCategory?categoryId=<%= cate.getCategoryId()%>" class="btn btn-warning" ><i class="bi bi-tools"></i> Edit</a>
                                <button class="btn btn-danger" onclick="confirmDelete(<%= cate.getCategoryId()%>)">Delete</button>
                            </td>
                        </tr>

                        <%
                                }
                            }
                        %>
                </table>
                <%
                    } else {
                        out.println("No Data!");
                    }
                %>
            </main>
        </div>
    </body>
</html>


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
            text: 'The category has been hidden.',
            timer: 2000
        });
    <% } else if ("1".equals(error)) { %>
        Swal.fire({
            icon: 'error',
            title: 'Failed!',
            text: 'Could not hide the category.',
            timer: 2000
        });
    <% }%>
    };
</script>

