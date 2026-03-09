<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create New Staff</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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

        .btn-primary {
            background-color: #28a745; /* Nền xanh lá cố định */
            border: none;
            color: white; /* Chữ trắng */
        }

        /* Đảm bảo màu xanh lá trong mọi trạng thái */
        .btn-primary:focus,
        .btn-primary:active,
        .btn-primary:hover {
            background-color: #28a745 !important; /* Giữ nguyên màu xanh lá */
            border: none !important;
            color: white !important;
            box-shadow: none !important; /* Loại bỏ bóng mặc định của Bootstrap */
        }

        .btn-secondary {
            background-color: #ced4da; /* Nền xám đậm */
            border-color: #ced4da;
            color: white; /* Chữ trắng */
        }

        .btn-secondary:hover {
            background-color: #adb5bd; /* Xám đậm hơn khi hover */
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
<h4 class="mb-0">Create New Staff</h4>
            </div>
            <div class="card-body">
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                %>
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i><%= errorMessage%>
                </div>
                <%
                    }
                %>
                <form action="CreateStaffServlet" method="post" class="row g-3">
                    <!-- Account Information -->
                    <div class="col-12 form-section">
                        <h5 class="section-title"><i class="fas fa-user-circle me-2"></i>Account Information</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <label for="email" class="form-label">Email:</label>
                                <input type="email" id="email" name="email" class="form-control" required>
                                <div id="emailError" class="text-danger small mt-1"></div>
                            </div>

                            <div class="col-md-6">
                                <label for="password" class="form-label">Password:</label>
                                <input type="password" id="password" name="password" class="form-control" required>
                            </div>

                            <div class="col-md-12">
                                
                            </div>
                        </div>
                    </div>

                    <!-- Staff Information -->
                    <div class="col-12 form-section">
                        <h5 class="section-title"><i class="fas fa-id-card me-2"></i>Staff Information</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <label for="fullName" class="form-label">Full Name:</label>
                                <input type="text" id="fullName" name="fullName" class="form-control" required>
                                <div id="fullNameError" class="text-danger small mt-1"></div> <!-- Sử dụng div trực tiếp -->
                            </div>

                            <div class="col-md-6">
                                <label for="phoneNumber" class="form-label">Phone Number:</label>
                                <input type="text" id="phoneNumber" name="phoneNumber" class="form-control">
                                <div id="phoneError" class="text-danger small mt-1"></div>
                            </div>

                            <div class="col-md-6">
<label for="birthDate" class="form-label">Birth Date:</label>
                                <input type="date" id="birthDate" name="birthDate" class="form-control">
                                <div id="birthDateError" class="text-danger small mt-1"></div>
                            </div>

                            <div class="col-md-6">
                                <label for="gender" class="form-label">Gender:</label>
                                <select id="gender" name="gender" class="form-select">
                                    <option value="">Select Gender</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="position" class="form-label">Position:</label>
                                <input type="text" id="position" name="position" class="form-control" required>
                            </div>

                            <div class="col-md-6">
                                <label for="hiredDate" class="form-label">Hired Date:</label>
                                <input type="date" id="hiredDate" name="hiredDate" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 mt-4 text-end">
                        <button type="submit" class="btn btn-primary me-2">Create</button>
                        <a href="StaffList" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const emailInput = document.getElementById("email");
        const emailError = document.getElementById("emailError");
        const submitBtn = document.querySelector("button[type='submit']");

        emailInput.addEventListener("blur", function () {
            const email = emailInput.value.trim();
            const emailPattern = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
            emailError.textContent = "";
            submitBtn.disabled = false;

            if (!emailPattern.test(email)) {
                emailError.textContent = "Email must be a valid Gmail address.";
                submitBtn.disabled = true;
                return;
            }

            fetch("CheckEmailServlet?email=" + encodeURIComponent(email))
                .then(response => response.text())
                .then(data => {
                    if (data === "EXISTS") {
                        emailError.textContent = "This email already exists.";
                        submitBtn.disabled = true;
}
                })
                .catch(error => {
                    emailError.textContent = "Error checking email.";
                    submitBtn.disabled = true;
                    console.error("Error:", error);
                });
        });

        emailInput.addEventListener("input", function () {
            emailError.textContent = "";
            submitBtn.disabled = false;
        });

        const phoneInput = document.getElementById("phoneNumber");
        const phoneError = document.getElementById("phoneError");

        phoneInput.addEventListener("blur", function () {
            const phone = phoneInput.value.trim();
            phoneError.textContent = "";
            submitBtn.disabled = false;

            const phonePattern = /^0\d{9}$/;
            if (!phonePattern.test(phone)) {
                phoneError.textContent = "Phone must start with 0 and have exactly 10 digits.";
                submitBtn.disabled = true;
                return;
            }

            fetch("CheckPhoneServlet?phone=" + encodeURIComponent(phone))
                .then(response => response.text())
                .then(data => {
                    if (data === "EXISTS") {
                        phoneError.textContent = "Phone number already exists.";
                        submitBtn.disabled = true;
                    }
                })
                .catch(error => {
                    phoneError.textContent = "Error checking phone.";
                    submitBtn.disabled = true;
                    console.error("Error:", error);
                });
        });

        phoneInput.addEventListener("input", function () {
            phoneError.textContent = "";
            submitBtn.disabled = false;
        });

        const birthInput = document.getElementById("birthDate");
        const birthError = document.getElementById("birthDateError");

        birthInput.addEventListener("blur", function () {
            const birthDate = new Date(this.value);
            const today = new Date();

            birthError.textContent = "";
            submitBtn.disabled = false;

            if (isNaN(birthDate.getTime())) return;

            let age = today.getFullYear() - birthDate.getFullYear();
            const m = today.getMonth() - birthDate.getMonth();

            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }

            if (age < 18) {
                birthError.textContent = "Staff must be at least 18 years old.";
                submitBtn.disabled = true;
            }
        });

        const fullNameInput = document.getElementById("fullName");
        const fullNameError = document.getElementById("fullNameError"); // Sử dụng div trực tiếp

        fullNameInput.addEventListener("blur", function () {
            let name = fullNameInput.value.trim();
            fullNameError.textContent = "";
            submitBtn.disabled = false;
name = name.replace(/\s+/g, " ");
            fullNameInput.value = name;

            const namePattern = /^([A-ZÀ-Ỹ][a-zà-ỹ]+)(\s[A-ZÀ-Ỹ][a-zà-ỹ]+)*$/u;

            if (!namePattern.test(name)) {
                fullNameError.textContent = "Names must be initialed, contain no numbers or special characters, and have no spaces.";
                submitBtn.disabled = true;
            }
        });

        fullNameInput.addEventListener("input", function () {
            fullNameError.textContent = "";
            submitBtn.disabled = false;
        });

        function checkDuplicateEmail(email) {
            fetch("CheckEmailServlet?email=" + encodeURIComponent(email))
                .then(response => response.text())
                .then(data => {
                    if (data === "EXISTS") {
                        alert("Email already exists.");
                        document.getElementById("email").focus();
                    }
                });
        }
    </script>
</body>
</html>