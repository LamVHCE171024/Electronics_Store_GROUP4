<%-- 
    Document   : productList
    Created on : Jun 18, 2025, 1:04:12 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Product> productList = (List<Product>) request.getAttribute("productList");

//    Category category = (Category) request.getAttribute("category");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/staffProductList.css">

    </head>
    <body>
        <jsp:include page="/WEB-INF/View/staff/productManagement/deleteProduct/staffDeleteProduct.jsp" />
        <div class = "" style = "width: 80%; margin-left: 18.9%">

            <%                if (productList != null) {
            %>
            <div style="overflow-x: auto; width: 100%;">
                <table class="table table-striped table-hover">
                    <tr class = "tableRow">
                        <td class ="tieuDe" style = "text-align: center">ID</td>
                        <td class ="tieuDe" style = "text-align: center">Product Name</td>
                        <td class ="tieuDe" style = "text-align: center">Price</td>
                        <td class ="tieuDe" style = "text-align: center">Discount</td>
                        <td class ="tieuDe" style = "text-align: center">status</td>
                        <td class ="tieuDe" style = "text-align: center">Category</td>
                        <td class ="tieuDe" style = "text-align: center">Brand</td>
                        <td class ="tieuDe" style = "text-align: center">Active</td>
                        <td class ="tieuDe" style = "text-align: center">Image</td>
                        <td class ="tieuDe" style = "text-align: center">Action</td>
                    </tr>
                    <%
                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat currencyVN = NumberFormat.getInstance(localeVN);

                        for (Product product : productList) {

                            String giaFormatted = currencyVN.format(product.getPrice());
                    %>
                    <tr>
                        <td><%= product.getProductId()%></td>
                        <td><%= product.getProductName()%></td>
                        <td><%=giaFormatted%></td>
                        <td><%= product.getDiscount()%>%</td>
                        <td><%= product.getStatus()%></td>

                        <td><%= product.getCategoryName()%></td>
                        <td><%= product.getBrandName()%></td>

                        <td>
                            <div class="pretty-checkbox">
                                <input type="checkbox" id="active-<%= product.getProductId()%>" disabled <%= product.isIsActive() ? "checked" : ""%> />
                                <label for="active-<%= product.getProductId()%>"></label>
                            </div>
                        </td>


                        <%
                            String img = product.getImageUrl();
                        %>
                        <td>
                            <img style = "width: 80%" src = "<%= (img != null) ? img : ""%>">
                        </td>

                        <td>
                            <div class="d-flex gap-1">
                                <a style = "text-align: center" href="StaffViewProductDetail?productId=<%= product.getProductId()%>" class="btn btn-warning" style="color: white;"><i class="bi bi-tools"></i> Detail</a>
                                <a style = "text-align: center" href="StaffUpdateInfo?productId=<%= product.getProductId()%>" class="btn btn-primary" ><i class="bi bi-tools"></i> Edit</a>
                                <button style = "text-align: center" class="btn btn-danger" onclick="confirmDelete(<%= product.getProductId()%>)">Delete</button>
                            </div>
                        </td>
                    </tr>

                    <%

                        }
                    %>
                </table>
                <%
                    } else {
                        out.println("No Data!");
                    }
                %>
            </div>
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
            text: 'The product has been hidden.',
            timer: 2000
        });
    <% } else if ("1".equals(error)) { %>
        Swal.fire({
            icon: 'error',
            title: 'Failed!',
            text: 'Could not hide the product.',
            timer: 2000
        });
    <% }%>
    };
</script>

