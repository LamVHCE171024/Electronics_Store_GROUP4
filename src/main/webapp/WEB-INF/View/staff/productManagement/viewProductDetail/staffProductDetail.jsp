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
            <jsp:include page="/WEB-INF/View/staff/productManagement/viewProductDetail/productTitle.jsp" />

            <div class="container">
                <div class="row">
                    <div class="col-md-5">
                        <jsp:include page="/WEB-INF/View/staff/productManagement/viewProductDetail/imageProductDetail.jsp" />
                    </div>
                    <div class="col-md-7">
                        <jsp:include page="/WEB-INF/View/staff/productManagement/viewProductDetail/productDetail.jsp" />
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
