<%@page import="model.Account"%>
<%@page import="java.util.Locale"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Voucher" %>
<%@ page import="java.text.NumberFormat" %>
<%
    Account acc = (Account) session.getAttribute("admin");
    if (acc == null || acc.getRoleID() != 1) {
        response.sendRedirect("LoginAdmin");
        return;
    }
    Voucher v = (Voucher) request.getAttribute("voucher");
    NumberFormat currencyVN = NumberFormat.getInstance(new Locale("vi", "VN"));
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <title>Voucher Detail</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <div class="card mx-auto shadow" style="max-width: 700px;">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Voucher Detail</h4>
                </div>
                <div class="card-body">
                    <% if (v != null) {%>
                    <table class="table table-borderless">
                        <tr><th>ID:</th><td><%= v.getVoucherID()%></td></tr>
                        <tr><th>Code:</th><td><%= v.getCode()%></td></tr>
                        <tr>
                            <th>Type:</th>
                            <td>
                                <%= v.isIsGlobal()
                                        ? "<span class='badge bg-success'>Global</span>"
                                        : "<span class='badge bg-info text-dark'>Personal</span>"%>
                            </td>
                        </tr>
                        <tr><th>Discount (%):</th><td><%= v.getDiscountPercent()%></td></tr>
                        <tr><th>Expiry Date:</th><td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(v.getExpiryDate())%></td></tr>
                        <tr><th>Min Order:</th><td><%= currencyVN.format(v.getMinOrderAmount()) + " ₫"%></td></tr>
                        <tr><th>Max Discount:</th><td><%= currencyVN.format(v.getMaxDiscountAmount()) + " ₫"%></td></tr>
                        <tr><th>Usage Limit:</th><td><%= v.getUsageLimit()%></td></tr>
                        <tr><th>Used:</th><td><%= v.getUsedCount()%></td></tr>
                                <%
                                    boolean isOverused = v.getUsedCount() >= v.getUsageLimit();
                                    boolean isCurrentlyActive = v.isActive() && !isOverused;
                                    String statusClass = isCurrentlyActive ? "status-active" : "status-inactive";
                                    String statusText = isCurrentlyActive ? "Active" : "Deactive";
                                %>
                        <tr>
                            <th>Status</th>
                            <td>
                                <span class="badge <%= statusClass%>"><%= statusText%>
                                    <% if (isOverused) { %>
                                    <i class="fa-solid fa-triangle-exclamation text-warning" title="Usage limit reached"></i>
                                    <% }%>
                                </span>
                            </td>
                        </tr>


                        <tr><th>Description:</th><td><%= v.getDescription() != null ? v.getDescription() : "N/A"%></td></tr>
                    </table>

                    <div class="d-flex justify-content-between mt-4">
                        <a href="Voucher" class="btn btn-outline-primary">
                            <i class="fa fa-arrow-left"></i> Back to List
                        </a>
                        <div>
                            <a href="Voucher?action=edit&id=<%= v.getVoucherID()%>" class="btn btn-warning">Edit</a>
                            <button class="btn btn-danger delete-btn"
                                    data-delete-url="Voucher?action=delete&id=<%= v.getVoucherID()%>"
                                    data-voucher-id="<%= v.getVoucherID()%>"
                                    data-voucher-code="<%= v.getCode()%>">
                                Delete
                            </button>
                        </div>
                    </div>
                    <% } else { %>
                    <div class="alert alert-danger">Voucher not found!</div>
                    <a href="Voucher" class="btn btn-outline-primary">
                        <i class="fa fa-arrow-left"></i> Back to List
                    </a>
                    <% }%>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const btn = document.querySelector('.delete-btn');
                if (btn) {
                    btn.addEventListener('click', function (e) {
                        e.preventDefault();
                        const deleteUrl = btn.getAttribute('data-delete-url');
                        const voucherId = btn.getAttribute('data-voucher-id');
                        const voucherCode = btn.getAttribute('data-voucher-code');

                        Swal.fire({
                            title: 'Are you sure?',
                            html: `Do you want to delete voucher <b>${voucherCode}</b> (ID: <b>${voucherId}</b>)?`,
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#d33',
                            cancelButtonColor: '#3085d6',
                            confirmButtonText: 'Yes, delete it!',
                            cancelButtonText: 'Cancel'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                window.location.href = deleteUrl;
                            }
                        });
                    });
                }
            });
        </script>
    </body>
</html>
