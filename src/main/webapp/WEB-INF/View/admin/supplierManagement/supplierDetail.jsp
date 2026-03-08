<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Suppliers" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Supplier Detail</title>
    <!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierLists5.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="container mt-5">
    <div class="card mx-auto shadow" style="max-width: 700px;">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Supplier Detail</h4>
        </div>
        <div class="card-body">
            <%
                Suppliers supplier = (Suppliers) request.getAttribute("supplier");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if (supplier != null) {
            %>
            <table class="table table-borderless">
                <tr><th>Tax ID:</th><td><%= supplier.getTaxId() %></td></tr>
                <tr><th>Name:</th><td><%= supplier.getName() %></td></tr>
                <tr><th>Email:</th><td><%= supplier.getEmail() %></td></tr>
                <tr><th>Phone Number:</th><td><%= supplier.getPhoneNumber() %></td></tr>
                <tr><th>Address:</th><td><%= supplier.getAddress() %></td></tr>
                <tr><th>Contact Person:</th><td><%= supplier.getContactPerson() != null ? supplier.getContactPerson() : "N/A" %></td></tr>
                <tr><th>Supply Group:</th><td><%= supplier.getSupplyGroup() != null ? supplier.getSupplyGroup() : "N/A" %></td></tr>
                <tr><th>Description:</th><td><%= supplier.getDescription() != null ? supplier.getDescription() : "N/A" %></td></tr>
                <tr>
                    <th>Status:</th>
                    <td>
                        <% if (supplier.getActivate() == 1) { %>
                            <span class="badge bg-success">Active</span>
                        <% } else { %>
                            <span class="badge bg-secondary">Deactive</span>
                        <% } %>
                    </td>
                </tr>
                <tr>
                    <th>Created Date:</th>
                    <td>
                        <%= supplier.getCreatedDate() != null ? supplier.getCreatedDate().format(formatter) : "N/A" %>
                    </td>
                </tr>
                <tr>
                    <th>Last Modified:</th>
                    <td>
                        <%= supplier.getLastModify() != null ? supplier.getLastModify().format(formatter) : "N/A" %>
                    </td>
                </tr>
            </table>

            <div class="d-flex justify-content-between mt-4">
                <a href="ViewSupplier" class="btn btn-outline-primary">
                    <i class="fa fa-arrow-left"></i> Back to List
                </a>
                <div>
                    <a href="UpdateSupplier?id=<%= supplier.getSupplierID() %>" class="btn btn-warning">Edit</a>
                    <!-- FORM bọc nút xóa -->
                    <form class="delete-form" action="DeleteSupplier" method="post" style="display:inline;">
                        <input type="hidden" name="supplierID" value="<%= supplier.getSupplierID() %>"/>
                        <input type="hidden" name="taxId" value="<%= supplier.getTaxId() %>"/>
                        <button type="button" class="btn btn-danger delete-btn"
                            data-supplier-id="<%= supplier.getSupplierID() %>"
                            data-tax-id="<%= supplier.getTaxId() %>"
                            data-supplier-name="<%= supplier.getName() %>">
                            Delete
                        </button>
                    </form>
                </div>
            </div>
            <%
                } else {
            %>
            <div class="alert alert-danger">Supplier not found!</div>
            <a href="ViewSupplier" class="btn btn-outline-primary">
                <i class="fa fa-arrow-left"></i> Back to List
            </a>
            <% } %>
        </div>
    </div>
</div>
<script>
window.onload = function () {
    // SweetAlert delete confirm giống Supplier List
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(function(btn) {
        btn.onclick = function(e) {
            e.preventDefault();
            const form = btn.closest('form');
            const supplierId = btn.getAttribute('data-supplier-id');
            const taxId = btn.getAttribute('data-tax-id');
            const supplierName = btn.getAttribute('data-supplier-name');
            Swal.fire({
                title: 'Are you sure?',
                html: `Do you want to delete supplier?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    form.submit();
                }
            });
        }
    });
};
</script>
</body>
</html>
