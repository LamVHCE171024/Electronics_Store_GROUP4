<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product List</title>
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <!-- Fontawesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <!-- Sidebar CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
        <!-- SweetAlert2 CDN -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>
            .create-btn-placeholder {
                height: 38px;
                min-width: 110px;
                margin-bottom: 12px;
                float: right;
                display: inline-block;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <jsp:include page="/WEB-INF/View/staff/header.jsp" />

                    <h1>Product List</h1>
                    <div class="create-btn-placeholder"></div> 

                    <!-- Search Form -->
                    <form class="search-form" action="ProductListForStaff" method="get">
                        <input type="hidden" name="action" value="search">
                        <input type="text" name="keyword" class="form-control" placeholder="Search product by name">
                        <button type="submit" class="search-btn">Search</button>
                    </form>

                    <!-- Product Table -->
                    <table aria-label="Product table">
                        <thead>
                            <tr>
                                <th>Product ID</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Stock</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Product> products = (List<Product>) request.getAttribute("products");
                                if (products != null && !products.isEmpty()) {
                                    for (Product product : products) {
                            %>
                            <tr>
                                <td><%= product.getProductId()%></td>
                                <td><%= product.getProductName()%></td>
                                <td><%= product.getPrice()%></td>
                                <td><%= product.getStock()%></td>
                                <td class="action-col">
                                    <a href="ProductListForStaffDetail?productId=<%= product.getProductId()%>" class="btn btn-primary">Detail</a>

                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="5" class="text-center">No products found!</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>

                    <!-- Optional message -->
                    <%
                        String mes = (String) request.getAttribute("message");
                        if (mes != null) {
                    %>
                    <div class="alert alert-info mt-3"><%= mes%></div>
                    <%
                        }
                    %>
                </main>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>