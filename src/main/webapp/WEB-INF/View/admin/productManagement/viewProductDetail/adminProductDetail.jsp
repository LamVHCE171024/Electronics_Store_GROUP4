<html>
    <head>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            html, body {
                height: 100%;
                margin: 0;
                padding: 0;
                background-color: #F2F4F7;
            }

            .divAll {
                background-color: #F2F4F7;
                margin: 0;
                padding: 0;
                min-height: 100vh; /* ??m b?o div cao b?ng toàn trang */
            }
        </style>
    </head>
    <body>
        <div class="divAll">
            <div class="container">
                <div class="row">
                    <div>
                        <jsp:include page="/WEB-INF/View/admin/productManagement/viewProductDetail/productTitle.jsp" />
                    </div>

                    <div class="" style = "width: 40%">

                        <jsp:include page="/WEB-INF/View/admin/productManagement/viewProductDetail/imageProductDetail.jsp" />
                    </div>
                    <div class="" style = "width: 60%">
                        <div style = "display: flex">
                            <h4>
                                Technical specifications
                            </h4>

                            <div style = "display: flex; margin-left: auto;">
                                <a href="AdminProduct" class="btn btn-back" style = "text-decoration: none">Back</a>
                            </div>
                        </div>
                        <jsp:include page="/WEB-INF/View/admin/productManagement/viewProductDetail/productDetail.jsp" />
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
