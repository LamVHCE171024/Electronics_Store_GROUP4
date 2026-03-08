<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Import Stock Detail</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                                        <jsp:include page="/WEB-INF/View/staff/header.jsp" />

                    <h1 class="mb-4">Import Stock Detail</h1>
                    <div class="d-flex justify-content-end mb-4 btn-group-custom">
                        <!-- Không có nút, nhưng vẫn chiếm chỗ -->
                    </div>
                    <form class="search-form mb-4" method="get" style="min-height: 52px;">
                    </form>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <div class="table-container mb-4" style="max-width: 1100px; margin: 0 auto;">
                        <table aria-label="Suppliers table">
                            <thead>
                                <tr>
                                    <th>Import ID</th>
                                    <th>Staff ID</th>
                                    <th>Staff Name</th>
                                    <th>Date</th>
                                    <th>Supplier</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${importStock.ioid}</td>
                                    <td>${importStock.staffId}</td>
                                    <td>${importStock.fullName}</td>
                                    <td>
                                        <fmt:formatDate value="${importStock.importDate}" pattern="yyyy-MM-dd HH:mm" />
                                    </td>
                                    <td>${importStock.supplier.name}</td>
                                    <td class="text-center">
                                        <fmt:formatNumber value="${importStock.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true" minFractionDigits="0"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="table-container mb-4" style="max-width: 1100px; margin: 0 auto;">
                        <h3 style="margin-bottom: 18px;">Details</h3>
                        <table aria-label="Suppliers table">
                            <thead>
                                <tr>
                                    <th>Product ID</th>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Import Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${details}" var="d">
                                    <tr>
                                        <td>${d.product.productId}</td>
                                        <td>${d.product.productName}</td>
                                        <td>${d.quantity}</td>
                                        <td class="text-center">
                                            <fmt:formatNumber value="${d.unitPrice}" type="currency" currencySymbol="₫" groupingUsed="true" minFractionDigits="0"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="text-end" style="max-width: 1100px; margin: 0 auto;">
                        <a href="ImportStockHistory" class="btn btn-secondary">Back</a>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
