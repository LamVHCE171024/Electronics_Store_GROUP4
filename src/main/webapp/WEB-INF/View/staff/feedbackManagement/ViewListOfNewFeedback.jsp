<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Feedback</title>
        <!-- Bootstrap & FontAwesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

        <style>
            body {
                background: #f4f6fb;
                font-family: 'Segoe UI', sans-serif;
            }

            .wrapper {
                width: 100%;
                max-width: 100%;
                margin: 0 auto;
                background: transparent;
            }

            main.main-content {
                flex: 1;
                margin-left: 227.5px;
                min-height: 100vh;
                box-sizing: border-box;
            }

            h1 {
                color: #232946;
                margin-top: 24px;
                font-weight: 800;
                font-size: 2.5rem;
                letter-spacing: 1px;
                margin-bottom: 20px;
            }

            /* Table */
            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                font-size: 15px;
                background: #fff;
                border-radius: 14px;
                box-shadow: 0 4px 18px rgba(34,40,85,0.07);
                overflow: hidden;
                margin-top: 10px;
                clear: both;
            }

            th, td {
                padding: 12px 10px;
                border-bottom: 1px solid #ececec;
                text-align: center;
            }

            th {
                background: #2563eb;
                color: #fff;
                font-weight: 700;
                font-size: 14px;
            }

            tbody tr:nth-child(even) {
                background: #f4f8fb;
            }

            tbody tr:hover {
                background: #f0f8ff;
            }

            .badge {
                padding: 6px 12px;
                border-radius: 999px;
                font-weight: 600;
                color: #fff;
                font-size: 14px;
            }

            .bg-success {
                background-color: #22c55e !important;
            }

            .bg-secondary {
                background-color: #6b7280 !important;
            }

            .star-rating i {
                color: #FFD700;
                margin-right: 2px;
            }

            .btn-sm {
                padding: 6px 16px;
                border-radius: 8px;
                font-weight: 600;
                font-size: 14px;
            }

            .btn-primary {
                background-color: #2563eb;
                border-color: #2563eb;
            }

            .btn-primary:hover {
                background-color: #164bb8;
                border-color: #164bb8;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                <jsp:include page="../header.jsp" />

                    <h1>Customer Feedback</h1>
                    <c:if test="${empty ProductRating}">
                        <div class="alert alert-warning text-center">No feedback available.</div>
                    </c:if>

                    <c:if test="${not empty ProductRating}">
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Customer Name</th>
                                    <th>Status</th>
                                    <th>Star</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${ProductRating}" var="rate" varStatus="loop">
                                    <tr class="${!rate.isRead ? 'table-success' : ''}">
                                        <td>${loop.index + 1}</td>
                                        <td>${rate.fullName}</td>
                                        <td>
                                            <span class="badge ${!rate.isRead ? 'bg-success' : 'bg-secondary'}">
                                                ${!rate.isRead ? 'New' : 'Seen'}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="star-rating">
                                                <c:forEach begin="1" end="${rate.star}" var="i">
                                                    <i class="fas fa-star"></i>
                                                </c:forEach>
                                                <c:forEach begin="${rate.star + 1}" end="5" var="i">
                                                    <i class="far fa-star"></i>
                                                </c:forEach>
                                            </div>
                                        </td>
                                        <td>
                                            <a href="ViewFeedBackForStaff?rateID=${rate.rateID}" class="btn btn-primary btn-sm">
                                                <i class="fas fa-eye me-1"></i> View Details
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </main>
            </div>
        </div>
    </body>
</html>
