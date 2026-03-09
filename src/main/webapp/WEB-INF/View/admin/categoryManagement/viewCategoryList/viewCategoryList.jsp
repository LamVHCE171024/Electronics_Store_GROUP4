<html>
    <head>
        <title>Sidebar + Category View</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">

    </head>
    <body>
        <div class="container">
            <!-- Sidebar bên trái -->
            <div class="sidebar">
                <jsp:include page="/sideBar.jsp" />
            </div>

            <div>
                <jsp:include page="/WEB-INF/View/admin/categoryManagement/viewCategoryList/CategoryView.jsp" />
            </div>
        </div>
    </body>
</html>