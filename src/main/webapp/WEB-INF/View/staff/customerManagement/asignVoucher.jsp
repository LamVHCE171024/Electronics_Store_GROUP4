<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Voucher" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Integer customerId = null;
    try {
        customerId = Integer.parseInt(request.getParameter("customerId"));
    } catch (Exception e) {
        customerId = null;
    }
    String customerName = (String) request.getAttribute("customerName");
    List<Voucher> personalVouchers = (List<Voucher>) request.getAttribute("personalVouchers");
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date today = new java.util.Date();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Assign Voucher to Customer</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css" />
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        .card-header { font-size: 1.3rem; font-weight: bold; letter-spacing: 0.5px; }
        .form-label { font-weight: 500; }
        .form-control, .form-select { border-radius: 8px; }
        .btn-success, .btn-secondary { min-width: 90px; }
        .alert { margin-bottom: 18px; }
        .mb-3 label { margin-bottom: 6px; }
    </style>
</head>
<body>

<jsp:include page="../sideBar.jsp" />

<div class="wrapper">
    <main class="main-content">
        <jsp:include page="../header.jsp" />

        <div class="container mt-5">
            <div class="card mx-auto shadow" style="max-width: 700px; margin-left: 50px;">
                <div class="card-header bg-primary text-white">Assign Voucher to Customer</div>
                <div class="card-body px-4">
                    <% if (error != null) { %>
                        <div class="alert alert-danger"><%= error %></div>
                    <% } %>
                    <% if (success != null) { %>
                        <script>
                            Swal.fire({
                                icon: 'success',
                                title: 'Success!',
                                text: '<%= success %>',
                                confirmButtonText: 'OK'
                            });
                        </script>
                    <% } %>
                    <% if (customerId == null) { %>
                        <div class="alert alert-danger">Missing or invalid customer ID.</div>
                    <% } else if (personalVouchers == null || personalVouchers.isEmpty()) { %>
                        <div class="alert alert-warning">No available personal vouchers to assign!</div>
                    <% } else { %>

                    <form method="post" action="AssignVoucher">
                        <input type="hidden" name="customerId" value="<%= customerId %>"/>
                        <table class="table table-borderless mb-4">
                            <tr>
                                <th style="width: 160px;">Customer:</th>
                                <td>
                                    <input type="text" class="form-control" value="<%= customerName != null ? customerName : customerId %>" disabled>
                                </td>
                            </tr>
                            <tr>
                                <th>Voucher:</th>
                                <td>
                                    <select class="form-select" name="voucherId" id="voucherSelect" required>
                                        <option value="">-- Select Personal Voucher --</option>
                                        <% 
                                            for (Voucher v : personalVouchers) { 
                                                if (v.getExpiryDate().after(today)) {
                                        %>
                                            <option 
                                                value="<%= v.getVoucherID() %>" 
                                                data-expiry="<%= sdf.format(v.getExpiryDate()) %>">
                                                <%= v.getCode() %>
                                                <% if (v.getDescription() != null && !v.getDescription().isEmpty()) { %>
                                                    - <%= v.getDescription() %>
                                                <% } %>
                                                (Expiry: <%= sdf.format(v.getExpiryDate()) %>)
                                            </option>
                                        <% 
                                                }
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    Expiration Date<br>
                                    <span style="font-weight:400;font-size:13px;">(for this customer)</span>:
                                </th>
                                <td>
                                    <input type="date" class="form-control" name="expirationDate" id="expirationDateInput" required
                                        min="<%= sdf.format(today) %>">
                                </td>
                            </tr>
                            <tr>
                                <th>Quantity:</th>
                                <td>
                                    <input type="number" class="form-control" name="quantity" min="1" value="1" required>
                                </td>
                            </tr>
                        </table>
                        <div class="text-end">
                            <button type="submit" class="btn btn-success me-2"><i class=""></i> Assign</button>
                            <a href="CustomerList" class="btn btn-secondary"><i class=""></i> Cancel</a>
                        </div>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const voucherSelect = document.getElementById('voucherSelect');
        const expirationDateInput = document.getElementById('expirationDateInput');
        function updateMaxDate() {
            const selected = voucherSelect.options[voucherSelect.selectedIndex];
            const maxDate = selected.getAttribute('data-expiry');
            if (maxDate) {
                expirationDateInput.max = maxDate;
            } else {
                expirationDateInput.removeAttribute('max');
            }
        }
        voucherSelect.addEventListener('change', updateMaxDate);
        updateMaxDate(); 
    });
</script>
</body>
</html>
