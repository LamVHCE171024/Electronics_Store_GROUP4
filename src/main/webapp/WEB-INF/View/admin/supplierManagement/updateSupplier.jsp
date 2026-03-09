<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Suppliers" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Supplier</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierLists5.css">

    </head>
    <body>
        <div class="container mt-5">
            <div class="card mx-auto shadow" style="max-width: 700px;">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Edit Supplier</h4>
                </div>
                <div class="card-body">
                    <%
                        String errorMessage = (String) request.getAttribute("errorMessage");
                        String successMessage = (String) request.getAttribute("successMessage");
                        Suppliers supplier = (Suppliers) request.getAttribute("supplier");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        if (errorMessage != null) {
                    %>
                    <div class="alert alert-danger"><%= errorMessage%></div>
                    <% }
                        if (successMessage != null) {
                    %>
                    <div class="alert alert-success"><%= successMessage%></div>
                    <% }
                        if (supplier != null) {
                    %>

                    <form method="post" action="UpdateSupplier">
                        <input type="hidden" name="supplierID" value="<%= supplier.getSupplierID()%>">

                        <table class="table table-borderless">
                            <tr>
                                <th>Tax ID:</th>
                                <td><input type="text" name="taxId" class="form-control" value="<%= supplier.getTaxId()%>" readonly></td>
                            </tr>
                            <tr>
                                <th>Name:</th>
                                <td><input type="text" name="name" class="form-control" value="<%= supplier.getName()%>" required></td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td><input type="email" name="email" class="form-control" value="<%= supplier.getEmail() != null ? supplier.getEmail() : ""%>"></td>
                            </tr>
                            <tr>
                                <th>Phone Number:</th>
                                <td><input type="text" name="phoneNumber" class="form-control" value="<%= supplier.getPhoneNumber() != null ? supplier.getPhoneNumber() : ""%>"></td>
                            </tr>
                            <tr>
                                <th>Address:</th>
                                <td><input type="text" name="address" class="form-control" value="<%= supplier.getAddress() != null ? supplier.getAddress() : ""%>"></td>
                            </tr>
                            <tr>
                                <th>Contact Person:</th>
                                <td><input type="text" name="contactPerson" class="form-control" value="<%= supplier.getContactPerson() != null ? supplier.getContactPerson() : ""%>"></td>
                            </tr>
                            <tr>
                                <th>Supply Group:</th>
                                <td>
                                    <%
                                        String supplyGroup = supplier.getSupplyGroup() != null ? supplier.getSupplyGroup() : "";
                                    %>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Air Conditioners"
                                               <%= supplyGroup.contains("Air Conditioners") ? "checked" : ""%>>
                                        <label class="form-check-label">Air Conditioners</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Washing Machines"
                                               <%= supplyGroup.contains("Washing Machines") ? "checked" : ""%>>
                                        <label class="form-check-label">Washing Machines</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Rice Cookers"
                                               <%= supplyGroup.contains("Rice Cookers") ? "checked" : ""%>>
                                        <label class="form-check-label">Rice Cookers</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Refrigerators"
                                               <%= supplyGroup.contains("Refrigerators") ? "checked" : ""%>>
                                        <label class="form-check-label">Refrigerators</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="TVs"
                                               <%= supplyGroup.contains("TVs") ? "checked" : ""%>>
                                        <label class="form-check-label">TVs</label>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>Description:</th>
                                <td><textarea name="description" class="form-control"><%= supplier.getDescription() != null ? supplier.getDescription() : ""%></textarea></td>
                            </tr>
                            <tr>
                                <th>Status:</th>
                                <td>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="activate" value="1"
                                               <%= supplier.getActivate() == 1 ? "checked" : ""%>> <label class="form-check-label">Activate</label>
                                    </div>
                                    <div class="form-check form-check-inline ms-3">
                                        <input class="form-check-input" type="radio" name="activate" value="0"
                                               <%= supplier.getActivate() == 0 ? "checked" : ""%>> <label class="form-check-label">Deactivate</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>Created Date:</th>
                                <td><%= supplier.getCreatedDate() != null ? supplier.getCreatedDate().format(formatter) : "N/A"%></td>
                            </tr>
                            <tr>
                                <th>Last Modified:</th>
                                <td><%= supplier.getLastModify() != null ? supplier.getLastModify().format(formatter) : "N/A"%></td>
                            </tr>
                        </table>

                        <div class="text-end">
                            <button type="submit" class="btn btn-success me-2">Save</button>
                            <a href="ViewSupplier" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>

                    <% } else { %>
                    <div class="alert alert-danger">Supplier not found!</div>
                    <a href="ViewSupplier" class="btn btn-outline-primary">Back to List</a>
                    <% }%>
                </div>
            </div>
        </div>

    </body>
</html>



