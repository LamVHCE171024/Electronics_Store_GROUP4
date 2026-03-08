<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Account, model.Staff" %>
<%
    Account account = (Account) request.getAttribute("account");
    Staff staff = (Staff) request.getAttribute("staff");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Staff</title>
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
            padding: 0.75rem 1.5rem; /* Giảm chiều cao để vừa với chữ */
            background-color: #0d6efd; /* Màu xanh giống Supplier Detail */
            color: white;
            font-size: 1.25rem;
            font-weight: 600;
        }

        .form-section {
            margin-bottom: 2rem;
        }

        .section-title {
            color: #000000; /* Chữ đen đậm giống Supplier Detail */
            border-bottom: 1px solid #dee2e6;
            padding-bottom: 0.5rem;
            margin-bottom: 1.5rem;
            font-weight: 600;
        }

        .form-label {
            font-weight: 600; /* Chữ đậm */
            color: #000000; /* Chữ đen */
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
            background-color: #28a745; /* Nền xanh lá */
            border: none;
            color: white; /* Chữ trắng */
        }

        /* Giữ màu xanh lá khi nhấn hoặc focus */
        .btn-primary:focus,
        .btn-primary:active,
        .btn-primary:hover {
            background-color: #28a745 !important; /* Giữ nguyên màu xanh lá */
            border: none !important;
            color: white !important;
            box-shadow: none !important; /* Loại bỏ bóng mặc định của Bootstrap */
        }

        .btn-secondary {
            background-color: #ced4da; /* Nền xám đậm hơn */
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
                <h4 class="mb-0">Edit Staff</h4>
            </div>
            <div class="card-body">
                <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i><%= request.getAttribute("errorMessage") %>
                </div>
                <% } %>

                <form action="UpdateStaffServlet" method="post" class="row g-3">
                    <!-- Hidden IDs -->
                    <input type="hidden" name="accountID" value="<%= account.getAccountID() %>">
                    <input type="hidden" name="staffID" value="<%= staff.getStaffID() %>">

                    <!-- Account Information -->
                    <div class="col-12 form-section">
                        <h5 class="section-title"><i class="fas fa-user-circle me-2"></i>Account Information</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <label for="email" class="form-label">Email:</label>
                                <input type="email" id="email" name="email" class="form-control" value="<%= account.getEmail() %>" required>
                                <div id="emailError" class="text-danger small mt-1"></div>
                                <input type="hidden" id="currentEmail" value="<%= account.getEmail() %>">
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
                                <input type="text" id="fullName" name="fullName" class="form-control" value="<%= staff.getFullName() %>" required>
                                <div id="fullNameError" class="text-danger small mt-1"></div>
                            </div>

                            <div class="col-md-6">
                                 <label for="phoneNumber" class="form-label">Phone Number:</label>
                                <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="<%= staff.getPhone() %>">
                                <div id="phoneError" class="text-danger small mt-1"></div>
                                <input type="hidden" id="currentPhone" value="<%= staff.getPhone() %>">
                            </div>

                            <div class="col-md-6">
                                <label for="birthDate" class="form-label">Birth Date:</label>
                                <input type="date" id="birthDate" name="birthDate" class="form-control" value="<%= staff.getBirthDay() != null ? staff.getBirthDay().toString() : "" %>">
                                <div id="birthDateError" class="text-danger small mt-1"></div>
                            </div>

                            <div class="col-md-6">
                                <label for="gender" class="form-label">Gender:</label>
                                <select id="gender" name="gender" class="form-select">
                                    <option value="">Select Gender</option>
                                    <option value="Male" <%= "Male".equals(staff.getGender()) ? "selected" : "" %>>Male</option>
                                    <option value="Female" <%= "Female".equals(staff.getGender()) ? "selected" : "" %>>Female</option>
                                    <option value="Other" <%= "Other".equals(staff.getGender()) ? "selected" : "" %>>Other</option>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label for="position" class="form-label">Position:</label>
                                <input type="text" id="position" name="position" class="form-control" value="<%= staff.getPosition() %>" required>
                            </div>

                            <div class="col-md-6">
                                <label for="hiredDate" class="form-label">Hired Date:</label>
                                <input type="date" id="hiredDate" name="hiredDate" class="form-control" value="<%= staff.getHiredDate() != null ? staff.getHiredDate().toString() : "" %>" required>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 mt-4 text-end">
                        <button type="submit" class="btn btn-primary me-2">Edit</button>
                        <a href="StaffList" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const submitBtn = document.querySelector("button[type='submit']");
const currentEmail = document.getElementById("currentEmail").value;
        const currentPhone = document.getElementById("currentPhone").value;

        // ===== EMAIL =====
        const emailInput = document.getElementById("email");
        const emailError = document.getElementById("emailError");

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

            // Chỉ kiểm tra trùng nếu người dùng thay đổi email
            if (email !== currentEmail) {
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
                    });
            }
        });

        emailInput.addEventListener("input", function () {
            emailError.textContent = "";
            submitBtn.disabled = false;
        });

        // ===== PHONE =====
        const phoneInput = document.getElementById("phoneNumber");
        const phoneError = document.getElementById("phoneError");

        phoneInput.addEventListener("blur", function () {
            const phone = phoneInput.value.trim();
            const phonePattern = /^0\d{9}$/;
            phoneError.textContent = "";
            submitBtn.disabled = false;

            if (!phonePattern.test(phone)) {
                phoneError.textContent = "Phone must start with 0 and have exactly 10 digits.";
                submitBtn.disabled = true;
                return;
            }

            // Chỉ kiểm tra nếu thay đổi số điện thoại
            if (phone !== currentPhone) {
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
                    });
            }
});

        phoneInput.addEventListener("input", function () {
            phoneError.textContent = "";
            submitBtn.disabled = false;
        });

        // ===== BIRTHDATE (check >= 18 tuổi) =====
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

        // ===== FULLNAME =====
        const fullNameInput = document.getElementById("fullName");
        const fullNameError = document.getElementById("fullNameError");

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
    </script>
</body>
</html>