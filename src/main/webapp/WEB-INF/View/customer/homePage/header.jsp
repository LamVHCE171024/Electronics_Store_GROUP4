<%-- 
    Document   : header
    Created on : Jun 13, 2025, 3:27:43 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Account"%>
<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    List<Brand> brandList = (List<Brand>) request.getAttribute("brandList");
    Account user = (Account) session.getAttribute("user");
%>
<style>
    .user-dropdown {
        position: relative;
        display: inline-block;
    }

    .user-dropdown .btn {
        background-color: #f8f9fa;
        border: 1px solid #ced4da;
        color: #333;
        transition: all 0.3s ease;
    }

    .user-dropdown .btn:hover {
        background-color: #e2e6ea;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .user-dropdown-menu {
        display: none;
        position: absolute;
        top: 100%;
        right: 0;
        background: white;
        border: 1px solid #e9ecef;
        border-radius: 8px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        min-width: 200px;
        padding: 8px 0;
        z-index: 1000;
        list-style: none;
        margin: 0;
    }

    .user-dropdown:hover .user-dropdown-menu {
        display: block;
    }

    .user-dropdown-menu li {
        margin: 0;
        padding: 0;
        list-style: none;
    }

    .user-dropdown-item {
        display: flex;
        align-items: center;
        gap: 12px; /* Khoảng cách giữa icon và text */
        width: 100%;
        padding: 12px 16px;
        color: #333;
        text-decoration: none;
        transition: all 0.2s ease;
        font-size: 14px;
        line-height: 1.4;
    }

    .user-dropdown-item:hover {
        background-color: #f8f9fa;
        text-decoration: none;
        color: #333;
        padding-left: 20px; /* Hiệu ứng trượt khi hover */
    }

    .user-dropdown-item i {
        font-size: 16px;
        width: 20px; /* Cố định width cho icon để align đều */
        text-align: center;
        flex-shrink: 0; /* Icon không bị co lại */
    }

    .user-dropdown-item span {
        flex: 1; /* Text chiếm hết phần còn lại */
        white-space: nowrap;
    }

    .user-dropdown-item.text-danger {
        color: #dc3545 !important;
    }

    .user-dropdown-item.text-danger:hover {
        background-color: #dc3545;
        color: white !important;
    }

    .user-dropdown-item.text-danger i {
        color: inherit;
    }

    .user-dropdown-divider {
        height: 0;
        margin: 8px 0;
        border: none;
        border-top: 1px solid #e9ecef;
    }
    .user-dropdown-item .bi-person-circle{
        margin-left: 50px;
    }
    .user-dropdown-item .bi-cart{
        margin-left: 50px;
    }
    .user-dropdown-item .bi-box-arrow-right{
        margin-left: 60px;
    }
    .user-dropdown-item .bi-geo-alt{
        margin-left: 60px;
    }
.user-dropdown-item .bi-ticket-perforated{
        margin-left: 60px;
    }
    
</style>
<!DOCTYPE html>
<html>
    <head class="header-red">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Genuine Phones, Laptops, Watches, and Accessories</title>
        <!-- Bootstrap 5 CSS & Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"> <!-- bootstrap css -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.5/font/bootstrap-icons.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/header.css">
    </head>

    <body>        
        <div class="header-top d-flex align-items-center justify-content-between py-2 px-3" style="background-color: #55C3FC;">
            <!-- (1) TRÁI: LOGO & NÚT DANH MỤC -->
            <div class="d-flex align-items-center">
                <!-- Logo -->
                <a style = "margin-left: 25%" href="${pageContext.request.contextPath}/Home" class="me-3">
                    <img src="https://res.cloudinary.com/dgnyskpc3/image/upload/v1750919684/Logo_nl7ahl.png" 

                         class="header-logo" 
                         style="height: 40px; object-fit: contain;" />
                </a>

                <!-- Danh mục: Dropdown -->
                <div class="dropdown" style="margin-left: 20%;">
                    <button class="category-btn"
                            type="button"
                            id="dropdownMenuButton"
                            data-bs-toggle="dropdown"
                            aria-expanded="false">
                        <i class="bi bi-list"></i> Categories
                    </button>

                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">

                        <% if (categoryList != null) {
                                for (Category cate : categoryList) {
                                    if (cate.getIsActive()) {
                        %>
                        <li style="flex: 0 0 20%; text-align: center; list-style: none;">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/FilterProduct?categoryId=<%= cate.getCategoryId()%>">
                                <img src="<%= cate.getImgUrlLogo()%>" style="width: 50px; height: 50px; object-fit: contain;">
                                <p><%= cate.getCategoryName()%></p>
                            </a>
                        </li>
                        <% }
                                }
                            } %>
                    </ul>
                </div>

            </div>

            <!-- (2) GIỮA: THANH TÌM KIẾM -->
            <div class="search-wrapper mx-3 position-relative" style="flex: 0 0 30%;">
                <form action="SearchProduct" method="get" class="search-bar position-relative">
                    <input type="text"
                           name="keyword"
                           class="form-control"

                           style="padding-right: 40px;">
                    <button type="submit"
                            class="search-btn btn btn-outline-secondary position-absolute top-50 end-0 translate-middle-y">
                        <i class="bi bi-search"></i>
                    </button>
                </form>
            </div>

            <!-- (3) PHẢI: TÀI KHOẢN & GIỎ HÀNG -->
            <div style = "width: 15%; margin-right: 5%" class="header-right d-flex align-items-center">
                <% if (user == null) { %>
                <a style = "border-radius: 15px;" href="${pageContext.request.contextPath}/Login" class="btn btn-outline-dark me-2" title="Tài khoản">
                    <i class="bi bi-person"></i>
                </a>
                <% } else {%>
                <!-- Đã đăng nhập -->
                <div class="user-dropdown">
                    <a style="border-radius: 15px;" class="btn btn-outline-dark me-2" title="Tài khoản">
                        <i class="bi bi-person"></i>
                    </a>
                    <ul class="user-dropdown-menu" aria-labelledby="userDropdown">
                        <li>
                            <a class="user-dropdown-item" href="ViewProfile">
                                <i class="bi bi-person-circle"></i>
                                <span>Profile</span>
                            </a>
                        </li>
                        <li>
                            <a class="user-dropdown-item" href="${pageContext.request.contextPath}/ViewOrderOfCustomer">
                                <i class="bi bi-cart"></i>
                                <span>Order</span>
                            </a>
                        </li>
                        <li>
                            <a class="user-dropdown-item" href="ViewShippingAddress">
                                <i class="bi bi-geo-alt"></i>
                                <span>Address</span>
                            </a>
                        </li>
                        <li>
                            <a class="user-dropdown-item" href="ViewCustomerVoucher">
                                <i class="bi bi-ticket-perforated"></i>
                                <span>Voucher</span>
                            </a>
                        </li>

                        <li><hr class="user-dropdown-divider"></li>
                        <li>
                            <a class="user-dropdown-item text-danger" href="Logout">
                                <i class="bi bi-box-arrow-right"></i>
                                <span>Logout</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <% }%>

                <a style="width: 60%; border-radius: 15px;" href="${pageContext.request.contextPath}/CartList?accountId=<%= user != null ? user.getAccountID() : 0 %>" class="btn btn-outline-dark" title="Giỏ hàng">
                    <i class="bi bi-cart"></i> Cart
                </a>
            </div>
        </div>



        <!-- ================== GÓI JS, JQUERY, POPPER, BOOTSTRAP ================== -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- JQuery -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script> <!-- Bootstrap JS Bundle(dropdown) -->
    </body>
</html>

<style>




</style>