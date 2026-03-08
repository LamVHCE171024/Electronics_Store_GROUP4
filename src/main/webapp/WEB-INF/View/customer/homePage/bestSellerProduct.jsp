<%-- 
    Document   : newProduct
    Created on : Jun 16, 2025, 12:58:19 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Product> productListBestSeller = (List<Product>) request.getAttribute("productListBestSeller");
    BigDecimal oldPrice;
    BigDecimal newPrice;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sản phẩm mới</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/newProduct1.css">
        <style>
            #product-scroll-best {
                display: flex;
                overflow-x: auto;
                scroll-behavior: smooth;
                padding-bottom: 10px;
                width: 100%;
                max-width: 100%;
                scroll-snap-type: x mandatory;
                gap: 10px;
            }

            .product-scroll-wrapper {
                position: relative;
                margin-left: 2%;
                margin-right: 2%;
                overflow: visible; /* Nếu bạn muốn nút nhô ra ngoài một chút */
                width: 100%;
                min-height: 300px; /* hoặc chiều cao vừa đủ chứa sản phẩm + nút */
            }

            .scroll-btn {
                background-color: white;
                border: 1px solid #ccc;
                padding: 8px;
                width: 40px;
                height: 40px;
                cursor: pointer;
                border-radius: 50%;
                font-size: 20px;
                font-weight: bold;
                color: #333;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                position: absolute;
                top: 40%;
                z-index: 10;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .scroll-btn:hover {
                background-color: #f0f0f0;
                box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
                transform: scale(1.1);
            }

            .scroll-left-best {
                left: 0;
            }

            .scroll-right-best {
                right: 0;
            }

            #product-scroll-best {
                display: flex;
                overflow-x: auto;
                scroll-behavior: smooth;
                padding-bottom: 10px;
                gap: 0.25% !important;
            }


            .sanPhamMoi {
                box-sizing: border-box;

                border-radius: 12px;                 /* bo góc */
                padding: 10px;
                background-color: #fff;              /* nền trắng (nếu cần) */
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3); /* đậm hơn */
                transition: transform 0.2s ease, box-shadow 0.2s ease;

                flex-shrink: 0;
                scroll-snap-align: start;
            }

            .sanPhamMoi:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);  /* hiệu ứng khi hover */
            }

            #product-scroll-best {
                scrollbar-width: none; /* Firefox */
                -ms-overflow-style: none; /* IE/Edge */
            }

            #product-scroll-best::-webkit-scrollbar {
                display: none; /* Chrome, Safari */
            }

        </style>
    </head>
    <body>
        <div class="" style="width: 100%; border-radius: 15px; margin-top: 1%; background-color: #fff; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);">
            <p class="new-product-label">Best Seller product</p>

            <div style="display: flex; border-radius: 10px;">
                <div class = "col-md-4" style="border-radius: 5px; margin-bottom: 1%">
                    <img style="width: 100%; height: auto" src="https://res.cloudinary.com/dgnyskpc3/image/upload/v1750919683/Bestseller_r69lpv.png">
                </div>

                <!-- PHẦN SẢN PHẨM CUỘN NGANG -->
                <div class="product-scroll-wrapper">
                    <!-- Nút trái -->
                    <button style = "" onclick="scrollLeft()" class="scroll-btn scroll-left-best">←</button>

                    <!-- Danh sách sản phẩm -->
                    <div id="product-scroll-best" style = "">
                        <% if (productListBestSeller != null) {

                                for (Product pro : productListBestSeller) {
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
                        <div class="sanPhamMoi">
                            <a href="<%= request.getContextPath()%>/ProductDetail?productId=<%= pro.getProductId()%>&categoryId=<%= pro.getCategoryId()%>" style="text-decoration: none; color: inherit; display: block;">
                                <div class="divHinh">
                                    <img style="width: 98%" src="<%= pro.getImageUrl()%>" alt="anhDienThoai" class="anhDienThoaiDocQuyen">
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
                        <%
                        } else {
                            oldPrice = pro.getPrice();
                            Locale localeVN = new Locale("vi", "VN");
                            NumberFormat currencyVN = NumberFormat.getInstance(localeVN);
                            String giaCuFormatted = currencyVN.format(oldPrice);
                        %>
                        <div class="sanPhamMoi">
                            <a href="<%= request.getContextPath()%>/ProductDetail?productId=<%= pro.getProductId()%>&categoryId=<%= pro.getCategoryId()%>" style="text-decoration: none; color: inherit; display: block;">
                                <div class="divHinh">
                                    <img style="width: 98%" src="<%= pro.getImageUrl()%>" alt="anhDienThoai" class="anhDienThoaiDocQuyen">
                                </div>
                                <div class="divTraGop">
                                    <p class="traGop">Trả góp 0%</p>
                                </div>
                                <p class="productName"><%= pro.getProductName()%></p>
                                <p class="giaMoi"><%= giaCuFormatted%> đ</p>
                            </a>
                        </div>
                        <%
                                    } // end if discount
                                } // end for
                            }
                        } else { %>
                        <p>null</p>
                        <% }%>
                    </div>

                    <!-- Nút phải -->
                    <button style = "margin-left: 2%;" onclick="scrollRight()" class="scroll-btn scroll-right-best">→</button>
                </div>
            </div>
        </div>

        <!-- JS scroll ngang -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const container = document.getElementById('product-scroll-best');
                const scrollAmount = container.clientWidth;

                document.querySelector('.scroll-left-best').addEventListener('click', () => {
                    container.scrollBy({left: -scrollAmount, behavior: 'smooth'});
                });

                document.querySelector('.scroll-right-best').addEventListener('click', () => {
                    container.scrollBy({left: scrollAmount, behavior: 'smooth'});
                });
            });
        </script>
    </body>
</html>

