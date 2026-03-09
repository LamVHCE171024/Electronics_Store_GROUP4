<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Suppliers" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Supplier List</title>
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Fontawesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <!-- Sidebar CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">
        <!-- Dashboard CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <h1>Suppliers</h1>
                    <button class="create-btn" onclick="location.href = 'CreateSupplier'">Create</button>
                    <form class="search-form" method="get" action="ViewSupplier">
                        <input
                            type="text"
                            name="searchName"
                            placeholder="Find by name ..."
                            value="<%= request.getParameter("searchName") != null ? request.getParameter("searchName") : ""%>"
                            />
                        <button type="submit" class="search-btn">Search</button>
                    </form>
                    <table aria-label="Suppliers table">
                        <thead>
                            <tr>
                                <th>Tax ID</th>
                                <th>Name</th>
                                <th>Phone Number</th>
                                <th>Email</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Suppliers> list = (List<Suppliers>) request.getAttribute("supplierList");
                                if (list != null && !list.isEmpty()) {
                                    for (Suppliers s : list) {
                                        String statusClass;
                                        String statusText;

                                        if (s.getActivate() == 1) {
                                            statusClass = "status-active";
                                            statusText = "Active";
                                        } else {
                                            statusClass = "status-inactive";
                                            statusText = "Deactive";
                                        }

                            %>
                            <tr>
                                <td><%= s.getTaxId()%></td>
                                <td><%= s.getName()%></td>
                                <td><%= s.getPhoneNumber()%></td>
                                <td><%= s.getEmail()%></td>
                                <td><span class="<%= statusClass%>"><%= statusText%></span></td>
                                <td class="action-col">
                                    <a href="ViewSupplier?id=<%= s.getSupplierID()%>" class="btn btn-primary">Detail</a>
                                    <a href="UpdateSupplier?id=<%= s.getSupplierID()%>" class="btn btn-warning">Edit</a>
                                    <form class="delete-form" action="DeleteSupplier" method="post" style="display:inline;">
                                        <input type="hidden" name="supplierID" value="<%= s.getSupplierID()%>"/>
                                        <input type="hidden" name="taxId" value="<%= s.getTaxId()%>"/>
                                        <button type="button" class="btn btn-danger delete-btn"
                                                data-supplier-id="<%= s.getSupplierID()%>"
                                                data-tax-id="<%= s.getTaxId()%>"
                                                data-supplier-name="<%= s.getName()%>">
                                            Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="7" class="text-center">No suppliers found!</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </main>
            </div>
        </div>
    </body>
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
                text: 'Supplier has been added.',
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

            // SweetAlert delete confirm with supplier ID & Tax ID
            const deleteBtns = document.querySelectorAll('.delete-btn');
            deleteBtns.forEach(function (btn) {
                btn.onclick = function (e) {
                    e.preventDefault();
                    const form = btn.closest('form');

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
</html>
