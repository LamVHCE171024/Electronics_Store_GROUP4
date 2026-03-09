<!-- My Orders History (Updated with unified CSS classes) -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Orders History</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/Css/profile.css" rel="stylesheet">

        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                font-size: 15px;
                color: #333;
            }

            th {
                font-weight: 600;
                font-size: 15px;
                color: #333;
            }

            td {
                font-weight: 400;
                font-size: 15px;
                color: #333;
            }

            .profile-body {
                font-family: inherit;
            }

            .btn-update, .btn-cancel {
                font-family: inherit;
                font-size: 14px;
            }

            .badge {
                padding: 10px 12px;
                border-radius: 999px;
                font-size: 14px;
                font-weight: 600;
            }

            .status-1 {
                background-color: #f59e0b;
                color: #fff;
            }
            .status-2 {
                background-color: #0d6efd;
                color: #fff;
            }
            .status-3 {
                background-color: #6366f1;
                color: #fff;
            }
            .status-4 {
                background-color: #22c55e;
                color: #fff;
            }
            .status-5 {
                background-color: #ef4444;
                color: #fff;
            }
            .scrollable-orders {
                max-height: 600px; /* Bạn có thể thay đổi giá trị này tùy theo thiết kế mong muốn */
                overflow-y: auto;
                padding-right: 8px; /* tránh che nội dung bởi scroll bar */
            }
            .scrollable-orders::-webkit-scrollbar {
                width: 8px;
            }

            .scrollable-orders::-webkit-scrollbar-thumb {
                background-color: rgba(0, 0, 0, 0.2);
                border-radius: 8px;
            }

            .btn-feedback {
                background: linear-gradient(135deg, #2980b9 0%, #6dd5fa 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                color: white;
                text-decoration: none;
                transition: all 0.3s ease;
                display: inline-flex;
                align-items: center;
                cursor: pointer;
                font-size: 14px;
            }

            .btn-feedback:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(41, 128, 185, 0.3);
                color: white;
            }

        </style>

    </head>

    <body class="d-flex flex-column min-vh-100">
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="main-account container-fluid">
            <!-- Sidebar -->
            <jsp:include page="/WEB-INF/View/customer/sideBar.jsp" />

            <!-- Main Content -->
            <div class="profile-card flex-grow-1">
                <div class="profile-header">
                    <h4><i class="bi bi-bag-check-fill me-2"></i> My Orders History</h4>
                </div>
                <div class="profile-body scrollable-orders">
                    <c:choose>
                        <c:when test="${not empty orderList}">
                            <c:forEach var="order" items="${orderList}">
                                <div class="mb-4 p-4" style="background: #fff; border-radius: 16px; box-shadow: 0 4px 14px rgba(0,0,0,0.08);">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <div class="fw-semibold fs-5 text-center">
                                            <i class="bi bi-receipt-cutoff me-2"></i>
                                            Order Date: <span class="fw-bold">${fn:substringBefore(order.orderDate, ' ')}</span>
                                        </div>

                                        <span class="badge status-${order.status}">
                                            <c:choose>
                                                <c:when test="${order.status == 1}">Waiting</c:when>
                                                <c:when test="${order.status == 2}">Packaging</c:when>
                                                <c:when test="${order.status == 3}">Waiting for Delivery</c:when>
                                                <c:when test="${order.status == 4}">Delivered</c:when>
                                                <c:otherwise>Cancelled</c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>

                                    <table class="table table-borderless">
                                        <tr>
                                            <th scope="row"><i class="bi bi-calendar-check me-2"></i>Order Date:</th>
                                            <td>${order.orderDate}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><i class="bi bi-clock-history me-2"></i>Last Updated:</th>
                                            <td>${order.updatedDate}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><i class="bi bi-cash-stack me-2"></i>Total Amount:</th>
                                            <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> ₫</td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><i class="bi bi-person-lines-fill me-2"></i>Recipient:</th>
                                            <td>${order.fullName} - ${order.phone}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row"><i class="bi bi-geo-alt-fill me-2"></i>Address:</th>
                                            <td>${order.addressSnapshot}</td>
                                        </tr>
                                    </table>
                                    <div class="d-flex gap-3 mt-3">
                                        <a href="CustomerOrderDetail?orderID=${order.orderID}" class="btn-update">
                                            <i class="bi bi-eye me-1"></i> View Detail
                                        </a>
                                        <c:if test="${order.status == 1 || order.status == 2}">
                                            <form action="CancelOrder" method="POST" class="d-inline-block cancel-form">
                                                <input type="hidden" name="orderID" value="${order.orderID}" />
                                                <button type="button" class="btn-cancel cancel-btn">
                                                    <i class="bi bi-x-circle me-1"></i> Cancel Order
                                                </button>
                                            </form>
                                        </c:if>

                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-info mt-4">
                                <i class="bi bi-info-circle me-2"></i> You haven't placed any orders yet.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- SweetAlert for cancel confirmation -->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.querySelectorAll('.cancel-btn').forEach(function (button) {
                    button.addEventListener('click', function () {
                        const form = button.closest('form');
                        Swal.fire({
                            title: 'Are you sure?',
                            text: "Do you want to cancel this order?",
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#d33',
                            cancelButtonColor: '#3085d6',
                            confirmButtonText: 'Yes, cancel it!',
                            cancelButtonText: 'No'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                form.submit();
                            }
                        });
                    });
                });
            });


        </script>


        <!-- SweetAlert for server response -->
        <c:if test="${not empty success || not empty error}">
            <script>
                window.onload = function () {
                <c:if test="${success == 'cancel'}">
                    Swal.fire({
                        icon: 'success',
                        title: 'Order Cancelled',
                        text: 'Order cancelled successfully.',
                        timer: 3000,
                        confirmButtonText: 'OK'
                    });
                </c:if>
                <c:if test="${error == 'not-cancelable'}">
                    Swal.fire({
                        icon: 'error',
                        title: 'Action Not Allowed',
                        text: 'Cannot cancel the order unless it is in Waiting or Packing status.',
                        timer: 3000,
                        confirmButtonText: 'Close'
                    });
                </c:if>

                    // Remove query params
                    const url = new URL(window.location);
                    url.searchParams.delete('success');
                    url.searchParams.delete('error');
                    window.history.replaceState({}, document.title, url.pathname);
                };
            </script>
        </c:if>
    </body>
</html>