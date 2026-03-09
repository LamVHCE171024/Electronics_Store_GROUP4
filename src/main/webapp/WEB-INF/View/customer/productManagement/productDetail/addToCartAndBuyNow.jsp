<%-- 
    Document   : addToCartAndBuyNow
    Created on : Jul 10, 2025, 12:20:42 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <style>
            .action-wrapper {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 16px;
                margin-bottom: 20px;
            }
            .btn {
                padding: 12px 24px;
                font-size: 16px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }

            .btn-addcartBorder {
                border: 1px solid transparent;
                background: linear-gradient(to top right, #fcfeff, #eff5ff) padding-box, linear-gradient(to top right, #dbe8fe, #609afa) border-box;
                border-radius: 12px;
                width: fit-content;
                text-align: center;
            }

            .btn-addcart {
                color: #3289EC;
            }

            .btn-buy {

                color: #ffffff;
                font-weight: bold;
            }

            .btn-buyBorder {
                background-color: #FC7600;
                border-radius: 12px;
                width: fit-content;
                text-align: center;
            }

            .trust-badges {
                display: flex;
                justify-content: center;
                gap: 24px;
                margin-top: 10px;
                font-size: 14px;
                color: #555;
            }
            .trust-badge {
                display: flex;
                align-items: center;
                gap: 6px;
            }
            .trust-badge img {
                width: 24px;
                height: 24px;
            }

            .oldPrice {
                color: #98a2b3;
                font-size: 16px;
                text-decoration-line: line-through;
                margin: 0 !important;

            }

            .newPrice {
                font-size: 24px;
                color: #d92d20;
                font-weight: bold;
                margin: 0 !important;
            }

            .discount {
                color: #d0021c;
                font-size: 16px;
                margin: 0 !important;
            }

            .price-row {
                display: flex;
                align-items: center; /* Căn giữa theo chiều dọc */
                margin: 0 !important;
                gap: 5px;
            }

            .promotion-box {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 16px;
                margin: 20px auto;
                max-width: 800px;
                background-color: #f9f9f9;
            }

            .promotion-box h3 {
                color: #2e7d32;
                margin-bottom: 12px;
            }

            .promotion-box ul {
                line-height: 1.8;
                font-size: 15px;
                color: #333;
                padding-left: 20px;
            }

            .promotion-box ul ul {
                padding-left: 20px;
                list-style-type: circle;
            }

            .product-price {
                margin-top: 15px;
                border: 1px solid transparent;
                background: linear-gradient(to top right, #fcfeff, #eff5ff) padding-box, linear-gradient(to top right, #dbe8fe, #609afa) border-box;
                border-radius: 12px;
                padding: 14px 24px;
                width: fit-content;
                margin-left: 2%;
            }

            .productPrice {
                color: #1d1d20;
                font-size: 14px;
                font-weight: 500;
                width: -moz-fit-content;
                width: fit-content;
                margin: 0 !important;
            }

            .action-wrapper {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 16px;
                width: 100%;
                margin-bottom: 20px;
            }

        </style>
    </head>

    <body>
        <div>
            <img style = "width: 100%" src = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/1d/b2/1db2c7a4cf2fd229fa0817c2714c6eff.png">
        </div>

        <div style = "margin-top: 1%;">
            <img style = "width: 100%" src = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/79/61/796197adf9f0c2d5cadb6a2c2679358a.png">
        </div>

        <div class="">
            <%
                if (product != null) {
                    BigDecimal oldPrice = product.getPrice();
                    BigDecimal price = product.getPrice();
                    int discount = product.getDiscount();

                    BigDecimal discountRate = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
                    BigDecimal newPrice = price.multiply(BigDecimal.ONE.subtract(discountRate));

                    BigDecimal giaCu = oldPrice;
                    BigDecimal giaMoi = newPrice;
                    BigDecimal giaDaGiam = giaCu.subtract(giaMoi);

                    Locale localeVN = new Locale("vi", "VN");
                    NumberFormat currencyVN = NumberFormat.getInstance(localeVN);

                    String giaCuFormatted = currencyVN.format(oldPrice);
                    String giaMoiFormatted = currencyVN.format(giaMoi);
                    String giamFormatted = currencyVN.format(giaDaGiam);
            %>
            <div class="product-price">
                <div>
                    <p class="productPrice">Product price</p>
                </div>

                <%
                    if (product.getDiscount() > 0) {
                %>
                <div class="price-row">
                    <p class="newPrice"><%= giaMoiFormatted%> đ</p>
                    (<p style="align-items: center" class="oldPrice"><%= giaCuFormatted%> đ</p>
                    <p class="discount">-<%= discount%>%</p>)
                </div>
                <%
                } else {
                %>
                <p class="newPrice"><%= giaMoiFormatted%> đ</p>
                <%
                    }
                %>
            </div>
            <%
                } // đóng if (product != null)
            %>
        </div>


        <!-- Promotion Information -->
        <div style="border: 1px solid #ddd; border-radius: 8px; width: 96%; padding: 14px 24px; margin: 20px auto; max-width: 800px; background-color: #ffffff;">
            <h3 style="color: #2e7d32; font-size: 24px; margin-bottom: 12px;">🎁 Promotions & Special Offers</h3>
            <ul style="line-height: 1.8; font-size: 15px; color: #333; padding-left: 20px;">
                <li><strong>2-year official warranty</strong></li>
                <li>Free installation service</li>
                <li>Choose one of the following gifts (while supplies last):  
                    <ul style="padding-left: 20px; list-style-type: circle;">
                        <li>Rapido standing fan RWF-16MGD or RWF-45PGM</li>
                        <li>Voucher worth 300,000₫ for air conditioners</li>
                        <li>Voucher worth 100,000₫ for rice cookers</li>
                        <li>Voucher worth 300,000₫ for refrigerators or washing machines</li>
                    </ul>
                </li>
                <li>Exclusive VNPAY-QR offer: Use code <strong>VNPAYTGDD3</strong> to get a discount of <strong>80,000₫ – 150,000₫</strong> (depending on order value)</li>
            </ul>

            <!-- Phần tương tác khách hàng -->
            <!-- Nút Add to Cart và Buy Now -->
            <div class="action-wrapper" style="gap: 1%;">
                <div class="btn-addcartBorder" style="width: 48%;">
                    <form class="action-form" action="AddCartServlet?productId=<%=product.getProductId()%>&categoryId=<%=product.getCategoryId()%>" method="post">
                        <input type="hidden" name="productId" value="<%=product.getProductId()%>">
                        <input type="hidden" name="action" value="addcart">
                        <button type="submit" class="btn btn-addcart">Add to cart</button>
                    </form>
                </div>

                <div class="btn-buyBorder" style="width: 48%;">
                    <form class="action-form" action="${pageContext.request.contextPath}/AddCartServlet?productId=<%=product.getProductId()%>&categoryId=<%=product.getCategoryId()%>" method="post">
                        <input type="hidden" name="productId" value="<%=product.getProductId()%>">
                        <input type="hidden" name="action" value="buynow">
                        <button type="submit" class="btn btn-buy">Buy now</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
