<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%@ page import="model.Brand" %>
<%@ page import="model.Product" %>
<%@ page import="dao.CategoryDAO" %>
<%@ page import="dao.BrandDAO" %>
<%@ page import="dao.ProductDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Promotion</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Fontawesome CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Dashboard CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
    <!-- SweetAlert2 CDN -->
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
            min-height: 70vh;
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
            min-height: 60vh;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
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

        .btn-primary {
            background-color: #28a745;
            border: none;
            color: white;
        }

        .btn-primary:focus,
        .btn-primary:active,
        .btn-primary:hover {
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

        .spacer {
            flex-grow: 1;
            height: 20vh;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="card mx-auto shadow">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Add Promotion</h4>
            </div>
            <div class="card-body">
                <!-- Optional message -->
                <% if (request.getAttribute("message") != null) { %>
                <div class="alert alert-success" role="alert">
                    <i class="fas fa-check-circle me-2"></i><%= request.getAttribute("message") %>
                </div>
                <% } %>
                <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i><%= request.getAttribute("error") %>
                </div>
                <% } %>

                <!-- Form -->
                <form id="promotionForm" action="AddPromotionServlet" method="POST" class="row g-3 needs-validation" novalidate>
                    <div class="col-12 form-section">
                        <h5 class="section-title"><i class="fas fa-tags me-2"></i>Promotion Details</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <label for="targetType" class="form-label">Target Type:</label>
                                <select name="targetType" id="targetType" class="form-select" required onchange="updateTargetOptions()">
                                    <option value="">-- Select --</option>
                                    <option value="BRAND">Brand</option>
                                    <option value="CATEGORY">Category</option>
                                    <option value="PRODUCT">Product</option>
                                </select>
                                <div class="invalid-feedback">Please select a target type.</div>
                            </div>

                            <div class="col-md-6">
                                <label for="targetId" class="form-label">Select Target:</label>
                                <select name="targetId" id="targetId" class="form-select" required>
                                    <option value="">-- Select type first --</option>
                                </select>
                                <div class="invalid-feedback">Please select a target.</div>
                            </div>

                            <div class="col-md-12">
                                <label for="name" class="form-label">Promotion Name:</label>
                                <input type="text" name="name" id="name" class="form-control" required>
                                <div class="invalid-feedback">Please enter a promotion name.</div>
                            </div>

                            <div class="col-md-6">
                                <label for="discount" class="form-label">Discount Percentage (1-100):</label>
                                <input type="number" name="discount" id="discount" class="form-control" min="1" max="100" required>
                                <div class="invalid-feedback">Discount percentage must be between 1 and 100.</div>
                            </div>

                            <div class="col-md-6">
                                <label for="startDate" class="form-label">Start Date:</label>
                                <input type="datetime-local" name="startDate" id="startDate" class="form-control" required>
                                <div class="invalid-feedback" id="startDateFeedback">Start date must be today or later.</div>
                            </div>

                            <div class="col-md-6">
                                <label for="endDate" class="form-label">End Date:</label>
                                <input type="datetime-local" name="endDate" id="endDate" class="form-control" required>
                                <div class="invalid-feedback" id="endDateFeedback">End date must be after start date.</div>
                            </div>

                            <div class="spacer"></div>
                        </div>
                    </div>

                    <div class="col-12 mt-4 text-end">
                        <button type="submit" class="btn btn-primary me-2">Add Promotion</button>
                        <a href="#" class="btn btn-secondary" onclick="window.history.back(); return false;">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Bootstrap form validation
        (function () {
            'use strict';
            const forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity() || !validateDates()) {
                        event.preventDefault();
                        event.stopPropagation();
                        Swal.fire({
                            icon: 'error',
                            title: 'Validation Error',
                            text: 'Please correct the errors in the form!',
                        });
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        })();

        // Update target options via AJAX
        function updateTargetOptions() {
            const targetType = document.getElementById("targetType").value;
            const targetSelect = document.getElementById("targetId");
            targetSelect.innerHTML = '<option value="">-- Loading --</option>';

            if (targetType) {
                fetch('GetTargetOptionsServlet?targetType=' + targetType)
                    .then(response => response.text())
                    .then(html => {
                        targetSelect.innerHTML = html;
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        targetSelect.innerHTML = '<option value="">-- Error loading data --</option>';
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Failed to load target list!',
                        });
                    });
            } else {
                targetSelect.innerHTML = '<option value="">-- Select type first --</option>';
            }
        }

        // Date validation
        function validateDates() {
            const startDateInput = document.getElementById('startDate');
            const endDateInput = document.getElementById('endDate');
            const startDateFeedback = document.getElementById('startDateFeedback');
            const endDateFeedback = document.getElementById('endDateFeedback');
            const today = new Date();
            today.setHours(0, 0, 0, 0); // Reset to start of day

            const startDate = new Date(startDateInput.value);
            if (startDate < today) {
                startDateInput.classList.add('is-invalid');
                startDateFeedback.textContent = 'Start date must be today or later.';
                return false;
            } else {
                startDateInput.classList.remove('is-invalid');
            }

            const endDate = new Date(endDateInput.value);
            if (endDate <= startDate) {
                endDateInput.classList.add('is-invalid');
                endDateFeedback.textContent = 'End date must be after start date.';
                return false;
            } else {
                endDateInput.classList.remove('is-invalid');
            }

            return true;
        }

        // Real-time validation on input change
        document.getElementById('startDate').addEventListener('change', function () {
            validateDates();
        });
        document.getElementById('endDate').addEventListener('change', function () {
            validateDates();
        });

        // Form submission with SweetAlert2
        document.getElementById('promotionForm').addEventListener('submit', function (event) {
            if (!validateDates()) {
                event.preventDefault();
                event.stopPropagation();
                Swal.fire({
                    icon: 'error',
                    title: 'Date Error',
                    text: 'Please ensure start date is today or later and end date is after start date!',
                });
            } else if (!this.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Error',
                    text: 'Please fill in all required fields correctly!',
                });
            }
            this.classList.add('was-validated');
        });
    </script>
</body>
</html>