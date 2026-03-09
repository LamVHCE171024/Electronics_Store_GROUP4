<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Vouchers - TShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/profile.css">
    <style>
        .voucher-input-form {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 20px auto;
            max-width: 500px;
            gap: 10px;
            flex-wrap: wrap;
        }
        .voucher-input-form input.voucher-input {
            flex: 1;
            min-width: 220px;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.2s;
        }
        .voucher-input-form input.voucher-input:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 3px rgba(0,123,255,0.2);
        }
        .voucher-input-form .btn-apply {
            background-color: #007bff;
            color: white;
            padding: 10px 16px;
            font-size: 1rem;
            border: none;
            border-radius: 8px;
            transition: background-color 0.2s;
            white-space: nowrap;
        }
        .voucher-input-form .btn-apply:hover {
            background-color: #0056b3;
        }
        .voucher-card {
            display: flex;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(80,80,80,0.13);
            overflow: hidden;
            background: #fff;
            margin-bottom: 20px;
            min-width: 300px;
            max-width: 100%;
        }
        .voucher-badge {
            background: #2196f3;
            color: #fff;
            font-weight: bold;
            font-size: 1.1rem;
            min-width: 95px;
            max-width: 95px;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 15px 8px;
            text-align: center;
            flex-shrink: 0;
        }
        .voucher-content {
            flex: 1;
            padding: 15px 15px 12px 18px;
            position: relative;
            min-width: 0;
        }
        .voucher-qty-badge {
            position: absolute;
            top: 8px;
            right: 12px;
            background: #dc3545;
            color: #fff;
            border-radius: 6px;
            padding: 2px 8px;
            font-size: 0.85rem;
            font-weight: 600;
        }
        .voucher-title {
            color: #007bff;
            font-weight: 700;
            font-size: 1.15rem;
            margin-bottom: 4px;
            word-wrap: break-word;
        }
        .voucher-desc {
            color: #333;
            font-size: 0.95rem;
            margin-bottom: 6px;
            line-height: 1.4;
            word-wrap: break-word;
        }
        .voucher-expiry {
            color: #222;
            font-size: 0.9rem;
            line-height: 1.3;
        }
        .voucher-list {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px 18px;
            margin-top: 16px;
            padding: 0 10px;
        }
        @media (max-width: 1200px) {
            .voucher-list {
                gap: 18px 15px;
            }
            .voucher-card {
                min-width: 280px;
            }
        }
        @media (max-width: 900px) {
            .voucher-list {
                grid-template-columns: 1fr;
                gap: 15px;
                padding: 0 5px;
            }
            .voucher-card {
                min-width: 250px;
            }
        }
        .main-account {
            min-height: 600px;
            overflow-x: hidden;
        }
        .voucher-card:hover {
            box-shadow: 0 4px 16px rgba(35,115,225,0.11);
            transition: box-shadow .18s;
        }
        .voucher-badge strong {
            font-size: 1.1rem;
            line-height: 1.2;
            word-wrap: break-word;
        }
        .profile-card {
            overflow-x: hidden;
        }
        .profile-body {
            overflow-x: hidden;
        }
        .alert-warning {
            background: linear-gradient(135deg, rgba(255, 193, 7, 0.1) 0%, rgba(255, 159, 64, 0.1) 100%);
            border-left: 5px solid #ffc107;
            color: #2c3e50;
        }
        .alert-danger {
            background: linear-gradient(135deg, rgba(220, 53, 69, 0.1) 0%, rgba(200, 35, 51, 0.1) 100%);
            border-left: 5px solid #dc3545;
            color: #2c3e50;
        }
        .alert-success {
            background: linear-gradient(135deg, rgba(40, 167, 69, 0.1) 0%, rgba(20, 147, 49, 0.1) 100%);
            border-left: 5px solid #28a745;
            color: #2c3e50;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
<div class="main-account container-fluid" style="margin-bottom: 20px">
    <div class="profile-card">
        <div class="profile-header">
            <h4>
                <i class="bi bi-ticket-perforated me-2"></i>
                My Vouchers
            </h4>
        </div>
        <div class="profile-body">
            <!-- Message if no cart items selected -->
            <c:if test="${empty sessionScope.selectedCartItemIds}">
                <div class="alert alert-warning text-center">
                    Please select items from your cart before applying a voucher.
                    <a href="${pageContext.request.contextPath}/CartList" class="btn btn-primary">Go to Cart</a>
                </div>
            </c:if>
            <!-- Success message -->
            <c:if test="${not empty sessionScope.message}">
               
            </c:if>
            <!-- Error message -->
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger text-center">
                    ${sessionScope.errorMessage}
                </div>
            </c:if>
            <!-- Voucher code input form -->
            <form class="voucher-input-form" action="${pageContext.request.contextPath}/CheckoutServlet" method="get">
                <input type="hidden" name="action" value="voucher">
                <input type="text" class="voucher-input" name="voucherCode" placeholder="Enter voucher code" required>
                <input type="hidden" name="selectedCartItemIds" value="${sessionScope.selectedCartItemIds}">
                <button type="submit" class="btn btn-apply">Apply</button>
            </form>
            <c:choose>
                <c:when test="${empty voucherList}">
                    <div class="alert alert-info text-center">
                        <i class="bi bi-ticket-perforated me-2"></i>
                        You don't have any vouchers yet.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="voucher-list">
                        <c:forEach var="cv" items="${voucherList}">
                            <c:set var="v" value="${cv.voucher}" />
                            <div class="voucher-card">
                                <div class="voucher-badge">
                                    <strong>
                                        <c:choose>
                                            <c:when test="${v.discountPercent > 0}">
                                                ${v.discountPercent}% OFF
                                            </c:when>
                                            <c:when test="${fn:containsIgnoreCase(v.code, 'ship') or fn:containsIgnoreCase(v.description, 'shipping')}">
                                                <fmt:formatNumber value="${v.maxDiscountAmount}" pattern="#,###"/> VND OFF
                                            </c:when>
                                            <c:when test="${v.maxDiscountAmount > 0}">
                                                <fmt:formatNumber value="${v.maxDiscountAmount}" pattern="#,###"/> VND OFF
                                            </c:when>
                                            <c:otherwise>
                                                SALE OFF
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </div>
                                <div class="voucher-content">
                                    <c:if test="${cv.quantity > 0}">
                                        <span class="voucher-qty-badge">x${cv.quantity}</span>
                                    </c:if>
                                    <div class="voucher-title">${v.code}</div>
                                    <div class="voucher-desc">${v.description}</div>
                                    <div class="voucher-expiry">
                                        Expiry Date:
                                        <fmt:formatDate value="${v.expiryDate}" pattern="dd/MM/yyyy"/>
                                    </div>
                                    <c:if test="${cv.expirationDate != null}">
                                        <div style="font-size:0.96rem;color:#666;">
                                            (Personal voucher valid until:
                                            <fmt:formatDate value="${cv.expirationDate}" pattern="dd/MM/yyyy"/>)
                                        </div>
                                    </c:if>
                                    <form action="${pageContext.request.contextPath}/CheckoutServlet" method="get" class="mt-2">
                                        <input type="hidden" name="action" value="voucher">
                                        <input type="hidden" name="voucherId" value="${v.voucherID}">
                                        <input type="hidden" name="selectedCartItemIds" value="${sessionScope.selectedCartItemIds}">
                                        <button type="submit" class="btn btn-sm btn-primary">Apply</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>