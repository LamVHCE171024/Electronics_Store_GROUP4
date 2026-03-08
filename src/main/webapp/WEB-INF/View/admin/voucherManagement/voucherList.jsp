<%@page import="model.Account"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Voucher" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<%
    Account acc = (Account) session.getAttribute("admin");
    if (acc == null || acc.getRoleID() != 1) {
        response.sendRedirect("LoginAdmin");
        return;
    }
%>
<html lang="en">
    <head>
        <title>Voucher List</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <!-- Bootstrap & FontAwesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Sidebar CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <!-- Reuse Supplier List CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">

        <%
            NumberFormat currencyVN = NumberFormat.getInstance(new Locale("vi", "VN"));
        %>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <h1>Vouchers</h1>
                    <button class="create-btn" onclick="location.href = 'Voucher?action=create'">Create</button>

                    <!-- Search Form -->
                    <form class="search-form" method="get" action="Voucher">
                        <input type="text" name="searchCode" placeholder="Find by code ..." 
                               value="<%= request.getParameter("searchCode") != null ? request.getParameter("searchCode") : ""%>"/>
                        <button type="submit" class="search-btn"> Search</button>
                    </form>

                    <!-- Voucher Table -->
                    <table aria-label="Voucher table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Code</th>
                                <th>Type</th> 
                                <th>Discount (%)</th>
                                <th>Expiry</th>
                                <th>Usage Limit</th>
                                <th>Used</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Voucher> list = (List<Voucher>) request.getAttribute("voucherList");
                                if (list != null && !list.isEmpty()) {
                                    for (Voucher v : list) {
                                        boolean isOverused = v.getUsedCount() >= v.getUsageLimit();
                                        boolean isCurrentlyActive = v.isActive() && !isOverused;
                                        String statusClass = isCurrentlyActive ? "status-active" : "status-inactive";
                                        String statusText = isCurrentlyActive ? "Active" : "Deactive";

                            %>
                            <tr>
                                <td><%= v.getVoucherID()%></td>
                                <td><%= v.getCode()%></td>
                                <td>
                                    <%= v.isIsGlobal()
                                            ? "<span style='color: #20c997;'>Global</span>"
                                            : "<span style='color: #0dcaf0;'>Personal</span>"%>
                                </td>
                                <td><%= v.getDiscountPercent()%></td>
                                <td><%= v.getExpiryDate()%></td>
                                <td><%= v.getUsageLimit()%></td>
                                <td><%= v.getUsedCount()%></td>
                                <td><span class="<%= statusClass%>"><%= statusText%></span></td>
                                <td class="action-col">
                                    <a href="Voucher?action=detail&id=<%= v.getVoucherID()%>" class="btn btn-primary">Detail</a>
                                    <a href="Voucher?action=edit&id=<%= v.getVoucherID()%>" class="btn btn-warning">Edit</a>
                                    <button class="btn btn-danger delete-btn" 
                                            data-delete-url="Voucher?action=delete&id=<%= v.getVoucherID()%>"
                                            data-voucher-id="<%= v.getVoucherID()%>"
                                            data-voucher-code="<%= v.getCode()%>">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="11" class="text-center">No vouchers found!</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </main>

            </div>

        </div>

        <%
            String success = request.getParameter("success");
            String error = request.getParameter("error");
        %>
        <script>
            window.onload = function () {
            <% if ("create".equals(success)) { %>
                Swal.fire({
                    icon: 'success',
                    title: 'Create Successful!',
                    text: 'Voucher has been added.',
                    showConfirmButton: true,
                    confirmButtonText: 'OK',
                    timer: 3000
                });
            <% } else if ("update".equals(success)) { %>
                Swal.fire({
                    icon: 'success',
                    title: 'Update Successful!',
                    text: 'Your changes have been saved successfully.',
                    showConfirmButton: true,
                    confirmButtonText: 'OK',
                    timer: 3000
                });
            <% } else if ("delete".equals(success)) { %>
                Swal.fire({
                    icon: 'success',
                    title: 'Delete Successful!',
                    text: 'Supplier has been deleted.',
                    showConfirmButton: true,
                    confirmButtonText: 'OK',
                    timer: 3000
                });
            <% } else if ("1".equals(error)) { %>
                Swal.fire({
                    icon: 'error',
                    title: 'Error Occurred!',
                    text: 'The update could not be completed. Please try again later.',
                    showConfirmButton: true,
                    confirmButtonText: 'Try Again',
                    timer: 3000
                });
            <% }%>


                const deleteBtns = document.querySelectorAll('.delete-btn');
                deleteBtns.forEach(function (btn) {
                    btn.onclick = function (e) {
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
                    };
                });

                // 🔁 Clean up the URL
                if (window.history.replaceState) {
                    const url = new URL(window.location);
                    url.searchParams.delete('success');
                    url.searchParams.delete('error');
                    window.history.replaceState({}, document.title, url.pathname);
                }


            };
        </script>
    </body>
</html>
