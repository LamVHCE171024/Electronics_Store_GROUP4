
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .container {
                padding-left: 47px !important;
                padding-right: 47px !important;
            }
        </style>
    </head>
    <body>
        <div style = "display: flex"  >
            <div style = "width: 18.5%;">
                <jsp:include page="/sideBar.jsp" />
            </div>
            
            <div class = "container" style = "flex: 1; margin-left: -12px ">
                <jsp:include page="/WEB-INF/View/admin/productManagement/viewProductList/fillterProductList.jsp" />
                <jsp:include page="/WEB-INF/View/admin/productManagement/viewProductList/productList.jsp" />
            </div>
        </div>
    </body>
</html>