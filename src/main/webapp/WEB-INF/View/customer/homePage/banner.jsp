<%-- 
    Document   : banner
    Created on : Jun 16, 2025, 11:48:37 AM
    Author     : HP - Gia Khiêm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <!-- Banner -->
        <div class="col-md-12 category-banner-full2" style = "margin-top: 1%">
            <div class="blue-overlay"></div>
            <div class="banner-text">
                <p class="welcom">Welcome to TShop!</p>
            <p class="Intro">Explore our premium selection of TVs, Refrigerators, Air Conditioners & more</p>

            </div>
        </div>

    </body>
</html>


<style>
    .category-banner-full2 {
        position: relative;
        border-radius: 15px;
        margin: 0;
        padding: 0;
        max-height: 350px;
        overflow: hidden;
    }

    .category-banner-full2 img {
        width: 99%;
        height: auto;
        display: block;
        border-radius: 15px;
    }

    .blue-overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(to right, #B2CFDE, #B8C9D0);
        opacity: 0.6; /* điều chỉnh độ mờ */
        z-index: 1;
        border-radius: 15px;
    }

    .banner-text {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 2;
        text-align: center;
        
    }

    .welcom {
        font-size: 4rem;
        font-weight: 500;
        text-transform: uppercase;
        color: #000000;
        text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
        letter-spacing: 2px;
        white-space: nowrap;
    }
    
    .Intro{
        font-size: 1rem;
        font-weight: 100;
        text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
        letter-spacing: 2px;
        white-space: nowrap;
    }

</style>
