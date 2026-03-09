<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Suppliers" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Supplier</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierForm.css">
    </head>
    <body>
        <div class="container mt-5">
            <div class="card mx-auto shadow" style="max-width: 700px;">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Add Supplier</h4>
                </div>
                <div class="card-body">
                    <% if (request.getAttribute("errorMsg") != null) {%>
                    <div class="alert alert-danger"><%= request.getAttribute("errorMsg")%></div>
                    <% }%>
                    <form method="post" action="CreateSupplier">
                        <table class="table table-borderless supplier-form-table">
                            <tr>
                                <th class="supplier-label">Tax ID:</th>
                                <td><input type="text" name="taxId" class="form-control supplier-input" required></td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Company Name:</th>
                                <td><input type="text" name="name" class="form-control supplier-input" required></td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Email:</th>
                                <td><input type="email" name="email" class="form-control supplier-input"></td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Phone Number:</th>
                                <td><input type="text" name="phoneNumber" class="form-control supplier-input"></td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Address:</th>
                                <td><input type="text" name="address" class="form-control supplier-input"></td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Contact Person:</th>
                                <td><input type="text" name="contactPerson" class="form-control supplier-input"></td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Supply Group:</th>
                                <td>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Air Conditioners" id="sgAC">
                                        <label class="form-check-label" for="sgAC">Air Conditioners</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Washing Machines" id="sgWM">
                                        <label class="form-check-label" for="sgWM">Washing Machines</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Rice Cookers" id="sgRC">
                                        <label class="form-check-label" for="sgRC">Rice Cookers</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="Refrigerators" id="sgRF">
                                        <label class="form-check-label" for="sgRF">Refrigerators</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="supplyGroup" value="TVs" id="sgTV">
                                        <label class="form-check-label" for="sgTV">TVs</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Description:</th>
                                <td>
                                    <textarea name="description" class="form-control supplier-input" rows="4"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th class="supplier-label">Status:</th>
                                <td>
                                    <div class="form-check form-check-inline">
                                        <input type="radio" name="activate" id="active" value="1" class="form-check-input" checked>
                                        <label for="active" class="form-check-label">Activate</label>
                                    </div>
                                    <div class="form-check form-check-inline ms-3">
                                        <input type="radio" name="activate" id="inactive" value="0" class="form-check-input">
                                        <label for="inactive" class="form-check-label">Deactivate</label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div class="text-end">
                            <button type="submit" class="btn btn-success me-2">Create</button>
                            <a href="ViewSupplier" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
