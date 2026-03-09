<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String createSuccess = (String) request.getAttribute("createSuccess");
    String createError = (String) request.getAttribute("createError");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Category</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }

        .card {
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border: none;
            max-width: 800px;
            margin: 0 auto;
        }

        .card-header {
            border-radius: 10px 10px 0 0 !important;
            padding: 0.75rem 1.5rem;
            background-color: #0d6efd;
            color: white;
            font-size: 1.25rem;
            font-weight: 600;
        }

        .form-section {
            margin-bottom: 2rem;
        }

        .section-title {
            color: #000000;
            border-bottom: 1px solid #dee2e6;
            padding-bottom: 0.5rem;
            margin-bottom: 1.5rem;
            font-weight: 600;
        }

        .form-label {
            font-weight: 600;
            color: #000000;
            margin-bottom: 0.25rem;
        }

        .form-control, .form-select {
            border-radius: 5px;
            padding: 0.5rem 0.75rem;
            border: 1px solid #ced4da;
        }

        .form-control:focus, .form-select:focus {
            border-color: #80bdff;
            box-shadow: 0 0 0 0.25rem rgba(0, 123, 255, 0.25);
        }

        .btn {
            border-radius: 5px;
            padding: 0.5rem 1.5rem;
            font-weight: 500;
        }

        .btn-success {
            background-color: #28a745;
            border: none;
            color: white;
        }

        .btn-success:focus,
        .btn-success:active,
        .btn-success:hover {
            background-color: #28a745 !important;
            border: none !important;
            color: white !important;
            box-shadow: none !important;
        }

        .btn-secondary {
            background-color: #ced4da;
            border-color: #ced4da;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #adb5bd;
            color: white;
        }

        .text-danger {
            font-size: 0.85rem;
        }

        .alert {
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="card mx-auto shadow">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Add New Category</h4>
            </div>
            <div class="card-body">
                <% if ("1".equals(createError)) { %>
                    <div class="alert alert-danger"><%= request.getAttribute("createError") %></div>
                <% } %>
                <form action="CreateCategory" method="post">
                    <div class="form-section">
                        <h5 class="section-title">Category Name</h5>
                        <div class="row">
                            <div class="col-md-12">
                                <input type="text" id="categoryName" name="categoryName" class="form-control supplier-input" required>
                            </div>
                        </div>
                    </div>

                    <div class="form-section">
                        <h5 class="section-title">Description</h5>
                        <div class="row">
                            <div class="col-md-12">
                                <input type="text" id="description" name="description" class="form-control supplier-input" required>
                            </div>
                        </div>
                    </div>

                    <div class="form-section">
                        <h5 class="section-title">Category Details</h5>
                        <div class="row">
                            <div class="col-md-12">
                                <div id="groupWrapper"></div>
                                <button type="button" onclick="addGroup()" class="btn btn-secondary">+ Add Detail Group</button>
                            </div>
                        </div>
                    </div>

                    <div class="text-end mt-4">
                        <button type="submit" class="btn btn-success me-2">Add Category</button>
                        <a href="CategoryView" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        let groupCount = 0;

        function addGroup() {
            groupCount++;
            const groupDiv = document.createElement('div');
            groupDiv.classList.add('group-container');

            groupDiv.innerHTML = `
                <div style="margin-bottom: 8px;">
                    <label>Detail Group Name:</label>
                    <input type="text" name="groups[${groupCount}][name]" class="form-control supplier-input" required maxlength="500" />
                    <button type="button" onclick="addDetail(this, ${groupCount})" class="btn btn-primary">+ Add Detail</button>
                    <button type="button" onclick="this.parentElement.parentElement.remove()" class="btn btn-danger">❌ Remove Group</button>
                </div>
                <div class="detail-list"></div>
            `;
            document.getElementById('groupWrapper').appendChild(groupDiv);
        }

        function addDetail(button, groupIndex) {
            const detailDiv = document.createElement('div');
            detailDiv.classList.add('detail-input');

            detailDiv.innerHTML = `
                <input type="text" name="groups[${groupIndex}][details][]" class="form-control supplier-input" required maxlength="500"/>
                <button type="button" onclick="this.parentElement.remove()" class="btn btn-danger">❌ Remove Detail</button>
            `;
            button.parentElement.parentElement.querySelector('.detail-list').appendChild(detailDiv);
        }

        window.onload = function () {
            <% if ("1".equals(createSuccess)) { %>
                Swal.fire({
                    icon: 'success',
                    title: 'Success!',
                    text: 'Category has been added.',
                    timer: 2000,
                    showConfirmButton: false
                });
            <% } else if ("1".equals(createError)) { %>
                Swal.fire({
                    icon: 'error',
                    title: 'Failed!',
                    text: 'Could not add category.',
                    timer: 2000,
                    showConfirmButton: false
                });
            <% } %>
        };
    </script>
</body>
</html>
