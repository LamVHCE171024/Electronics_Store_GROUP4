<%-- 
    Document   : productList
    Created on : Jul 12, 2025, 6:04:34 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Product> productList = (List<Product>) request.getAttribute("productList");
    BigDecimal oldPrice;
    BigDecimal newPrice;
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/filterProduct.css">
    </head>
    <body>
        <!-- Danh sách sản phẩm -->
        <div style = "gap: 0.25%; display: flex; width: 100%; flex-wrap: wrap;">
            <% if (productList != null) {
                    for (Product pro : productList) {
                        if (pro.isIsActive() == true) {
                            if (pro.getDiscount() != 0) {
                                oldPrice = pro.getPrice();
                                BigDecimal price = pro.getPrice();
                                int discount = pro.getDiscount();

                                BigDecimal discountRate = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
                                newPrice = price.multiply(BigDecimal.ONE.subtract(discountRate));

                                BigDecimal giaCu = oldPrice;
                                BigDecimal giaMoi = newPrice;
                                BigDecimal giaDaGiam = giaCu.subtract(giaMoi);

                                Locale localeVN = new Locale("vi", "VN");
                                NumberFormat currencyVN = NumberFormat.getInstance(localeVN);

                                String giaCuFormatted = currencyVN.format(oldPrice);
                                String giaMoiFormatted = currencyVN.format(giaMoi);
                                String giamFormatted = currencyVN.format(giaDaGiam);
            %>
            <div class="divProduct" style = "width: 18.9%">
                <a href="<%= request.getContextPath()%>/ProductDetail?productId=<%= pro.getProductId()%>&categoryId=<%= pro.getCategoryId()%>" style="text-decoration: none; color: inherit; display: block;">
                    <div class="divHinh">
                        <img style = "width: 100%" src="<%= pro.getImageUrl()%>" alt="anhDienThoai" class="anhDienThoaiDocQuyen">
                    </div>
                    <div class="divTraGop">
                        <p class="traGop">Trả góp 0%</p>
                    </div>
                    <p class="productName"><%= pro.getProductName()%></p>
                    <p class="giaCu">
                        <s><%= giaCuFormatted%></s> 
                        <span style="color: red">-<%= discount%>%</span>
                    </p>
                    <p class="giaMoi"><%= giaMoiFormatted%> đ</p>
                    <p class="giam">Giảm <%= giamFormatted%> đ</p>
                </a>
            </div>
            <% } else {
                oldPrice = pro.getPrice();
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getInstance(localeVN);
                String giaCuFormatted = currencyVN.format(oldPrice);
            %>
            <div class="divProduct" style = "width: 18.9%">
                <a href="<%= request.getContextPath()%>/ProductDetail?productId=<%= pro.getProductId()%>&categoryId=<%= pro.getCategoryId()%>" style="text-decoration: none; color: inherit; display: block;">
                    <div class="divHinh">
                        <img style = "width: 100%" style="width: 98%" src="<%= pro.getImageUrl()%>" alt="anhDienThoai" class="anhDienThoaiDocQuyen">
                    </div>
                    <div class="divTraGop">
                        <p class="traGop">Trả góp 0%</p>
                    </div>
                    <p class="productName"><%= pro.getProductName()%></p>
                    <p class="giaMoi"><%= giaCuFormatted%> đ</p>
                </a>
            </div>
            <% } // end if discount
                    }
                }// end for
            } else { %>
            <p>null</p>
            <% }%>
        </div>

    </body>
</html>
