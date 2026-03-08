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
                min-height: 100vh;
            }

            .container {
                padding: 20px;
            }

            .row {
                display: flex;
                gap: 20px;
            }

            .col-left {
                width: 40%;
            }

            .col-right {
                width: 60%;
            }

            .header-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .btn-back {
                color: #fff;
                background-color: #6c757d;
                border: 1px solid #6c757d;
                padding: 8px 16px;
                border-radius: 6px;
                font-size: 14px;
                cursor: pointer;
                text-decoration: none;
                transition: background-color 0.3s ease, border-color 0.3s ease;
            }

            .btn-back:hover {
                background-color: #5c636a;
                border-color: #565e64;
                color: #fff;
            }

            .checkbox-container {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                align-items: center;
                margin-top: 10px;
                margin-bottom: 20px;
            }

            .form-check {
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .form-check-input.rounded-circle {
                width: 20px;
                height: 20px;
                cursor: pointer;
                border: 2px solid #6c757d;
                transition: border-color 0.3s ease, background-color 0.3s ease;
            }

            .form-check-input.rounded-circle:checked {
                background-color: #007bff;
                border-color: #007bff;
            }

            .form-check-label {
                font-size: 14px;
                color: #333;
                cursor: pointer;
            }
        </style>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <div class="divAll">
            <div class="container">
                <div>
                    <jsp:include page="/WEB-INF/View/staff/header.jsp" />

                    <jsp:include page="/WEB-INF/View/staff/productManagement/viewProductDetail/productTitle.jsp" />
                </div>
                <%@page import="model.Product"%>
                <%
                    Product product = (Product) request.getAttribute("product");
                %>
                <div class="header-section">
                    <h4></h4>
                    <a href="ProductListForStaff" class="btn-back">Back</a>
                </div>
                <div class="checkbox-container">
                    <div class="form-check">
                        <input class="form-check-input rounded-circle" type="checkbox" id="isFeatured" name="isFeatured" <%= product != null && product.isIsFeatured() ? "checked" : ""%>>
                        <label class="form-check-label" for="isFeatured">Is Featured</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input rounded-circle" type="checkbox" id="isNew" name="isNew" <%= product != null && product.isIsNew() ? "checked" : ""%>>
                        <label class="form-check-label" for="isNew">Is New</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input rounded-circle" type="checkbox" id="isBestSeller" name="isBestSeller" <%= product != null && product.isIsBestSeller() ? "checked" : ""%>>
                        <label class="form-check-label" for="isBestSeller">Is Best Seller</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-left">
                        <jsp:include page="/WEB-INF/View/staff/productManagement/viewProductDetail/imageProductDetail.jsp" />
                    </div>
                    <div class="col-right">
                        <jsp:include page="/WEB-INF/View/staff/productManagement/viewProductDetail/productDetail.jsp" />
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>