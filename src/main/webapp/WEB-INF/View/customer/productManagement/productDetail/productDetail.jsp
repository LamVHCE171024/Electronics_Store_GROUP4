<%-- 
    Document   : productDetail
    Created on : Jul 10, 2025, 12:18:54 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= product.getProductName()%> - Product Detail</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/customerProductDetail.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>
            body {
                background-color: #F2F4F7;
            }
            .fa-star.checked {
                color: #f5a623;
            }
        </style>
    </head>

    <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />

    <body>
        <div style="display: flex; gap: 1%;">

            <div style="width: 59%;">
                <div style="display: flex; gap: 4px; font-size: 14px; color: #555; margin-top: 2%; margin-bottom: 2%;">
                    <a href="/" style="text-decoration: none; color: #007bff;">Home</a> >
                    <a href="#" style="text-decoration: none; color: #007bff;"><%= product.getCategoryName()%></a> >
                    <a href="#" style="text-decoration: none; color: #000;"><%= product.getProductName()%></a>
                </div>

                <h1 style="font-size: 20px"><%= product.getProductName()%></h1>

                <div class="customerDivImageDetail">
                    <jsp:include page="/WEB-INF/View/customer/productManagement/productDetail/imageProduct.jsp" />
                </div>

                <div class="customerDivCommitted">
                    <jsp:include page="/WEB-INF/View/customer/productManagement/productDetail/committed.jsp" />
                </div>

                <div class="customerDivinfomationDetail" style="margin-top: 15px;">
                    <jsp:include page="/WEB-INF/View/customer/productManagement/productDetail/infomationDetail.jsp" />
                </div>

                <%-- ⭐ Feedback Section Start --%>
                <div class="customerFeedbackSection" style="margin-top: 30px; background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 0 8px rgba(0,0,0,0.05);">
                    <div style="text-align: center; margin-bottom: 20px; position: relative;">
                        <hr style="border: none; height: 1px; background: #ddd;">
                        <h2 style="font-size: 20px; font-weight: bold; margin: -15px auto 0; background: #fff; display: inline-block; padding: 0 15px; color: #333;">
                            Customer Feedback
                        </h2>
                    </div>
                    <c:if test="${empty productRatings}">
                        <p style="text-align: center; color: gray;">No feedback available for this product.</p>
                    </c:if>

                    <c:forEach var="rating" items="${productRatings}">
                        <div style="border: 1px solid #e1e1e1; border-radius: 8px; padding: 15px; margin-bottom: 20px; background: #fafafa;">
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <strong>${rating.fullName}</strong> 

                                <small style="color: gray;">${rating.createdDate}</small>
                            </div>

                            <div style="color: #f5a623; margin: 5px 0;">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="fa fa-star <c:if test='${i <= rating.star}'>checked</c:if>'"></i>
                                </c:forEach>
                            </div>

                            <p style="margin-top: 8px; font-size: 15px;">${rating.comment}</p>

                            <%-- ✅ Nếu có phản hồi từ nhân viên --%>
                            <c:if test="${not empty rating.replies}">
                                <div style="margin-top: 15px; padding: 10px 15px; border: 1px solid #3399ff; border-radius: 6px; background-color: #f0f8ff;">

                                    <c:forEach var="reply" items="${rating.replies}">
                                        <div style="margin-top: 15px; padding: 10px 15px; background: #eaf4ff; border-left: 4px solid #3399ff; border-radius: 6px;">
                                            <strong style="color: #004a99;">Staff Response:</strong>
                                            <p style="margin: 6px 0; font-size: 14px; color: #333;">${reply.answer}</p>
                                        </div>
                                    </c:forEach>

                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
                <%-- ⭐ Feedback Section End --%>


            </div>

            <div class="customerDivaddToCartAndBuyNow" style="width: 40%; margin-top: 6.8%; max-height: 820px;">
                <jsp:include page="/WEB-INF/View/customer/productManagement/productDetail/addToCartAndBuyNow.jsp" />
            </div>
        </div>
    </body>

    <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
    <% String successcreate = request.getParameter("successcreate"); %>
    <% String checkquantity = request.getParameter("checkquantity");%> 



    <script>
        window.onload = function () {
            var success = '<%= successcreate%>';
            var error = '<%= checkquantity%>';

            if (success === '1') {
                Swal.fire({
                    icon: 'success',
                    title: 'Success!',
                    text: 'Product has been added to cart.',
                    timer: 2000
                });
            } else if (error === '1') {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed!',
                    text: 'The quantity of product in stock is not enough to order.',
                    timer: 2000
                });
            }
        };
    </script>


</html>
