<%-- 
    Document   : reset-password.jsp
    Created on : Jul 9, 2025, 10:55:08 PM
    Author     : pc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            .login-container {
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px 0;
            }

            .login-card {
                background: rgba(255, 255, 255, 0.95);
                backdrop-filter: blur(10px);
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                padding: 40px;
                width: 100%;
                max-width: 500px;
            }

            .login-title {
                color: #333;
                font-weight: 600;
                margin-bottom: 30px;
                text-align: center;
            }

            .form-control {
                border-radius: 10px;
                border: 2px solid #e9ecef;
                padding: 12px 15px;
                transition: all 0.3s ease;
                margin-bottom: 15px;
            }

            .form-control:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
            }

            .form-label {
                font-weight: 500;
                color: #333;
                margin-bottom: 5px;
            }

            .btn-reset {
                background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 15px;
                color: white;
            }

            .btn-reset:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(17, 153, 142, 0.3);
                color: white;
            }

            .btn-login {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 20px;
                color: white;
            }

            .btn-login:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
                color: white;
            }

            .login-link {
                color: #667eea;
                text-decoration: none;
                font-size: 14px;
                transition: all 0.3s ease;
                display: block;
                text-align: center;
            }

            .login-link:hover {
                color: #764ba2;
                text-decoration: underline;
            }

            .divider {
                text-align: center;
                margin: 20px 0;
                position: relative;
                color: #6c757d;
            }

            .divider::before {
                content: '';
                position: absolute;
                top: 50%;
                left: 0;
                right: 0;
                height: 1px;
                background: #e9ecef;
            }

            .divider span {
                background: rgba(255, 255, 255, 0.95);
                padding: 0 15px;
            }

            .error-message {
                background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
                color: white;
                padding: 15px;
                border-radius: 10px;
                margin-bottom: 20px;
                text-align: center;
                font-weight: 500;
            }

            .input-group {
                position: relative;
                margin-bottom: 15px;
            }

            .input-icon {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #6c757d;
                z-index: 5;
            }

            .form-control.with-icon {
                padding-left: 45px;
            }

            .description-text {
                color: #6c757d;
                font-size: 14px;
                text-align: center;
                margin-bottom: 25px;
                line-height: 1.5;
            }

            .password-strength {
                height: 3px;
                background: #e9ecef;
                border-radius: 2px;
                margin-top: 5px;
                overflow: hidden;
            }

            .password-strength-bar {
                height: 100%;
                transition: all 0.3s ease;
                border-radius: 2px;
            }

            .strength-weak {
                background: #ff6b6b;
                width: 33%;
            }
            .strength-medium {
                background: #ffa726;
                width: 66%;
            }
            .strength-strong {
                background: #11998e;
                width: 100%;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="login-container">
            <div class="login-card">
                <h2 class="login-title">
                    <i class="bi bi-shield-lock-fill me-2"></i>
                    Reset Your Password
                </h2>

                <p class="description-text">
                    Enter your new password below. Make sure it's strong and secure.
                </p>

                <% String error = (String) request.getAttribute("error");
                   if (error != null) { %>
                    <div class="error-message">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <%= error %>
                    </div>
                <% } %>

                <form method="post" action="ResetPassword">
                    <div class="input-group">
                        <i class="bi bi-lock-fill input-icon"></i>
                        <input type="password" name="newPassword" class="form-control with-icon"
                               placeholder="Enter new password" required minlength="6" id="newPassword">
                        <div class="password-strength">
                            <div class="password-strength-bar" id="strengthBar"></div>
                        </div>
                    </div>

                    <div class="input-group">
                        <i class="bi bi-shield-lock-fill input-icon"></i>
                        <input type="password" name="confirmPassword" class="form-control with-icon"
                               placeholder="Confirm new password" required minlength="6" id="confirmPassword">
                    </div>

                    <button type="submit" class="btn btn-reset">
                        <i class="bi bi-check-circle-fill me-2"></i>
                        Reset Password
                    </button>

                    <div class="divider">
                        <span>Remember your password?</span>
                    </div>

                    <a href="Login" class="btn btn-login">
                        <i class="bi bi-box-arrow-in-right me-2"></i>
                        Back to Login
                    </a>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Wait for DOM to be fully loaded
            document.addEventListener('DOMContentLoaded', function () {
                // Password strength indicator
                const passwordInput = document.getElementById('newPassword');
                const strengthBar = document.getElementById('strengthBar');

                if (passwordInput && strengthBar) {
                    passwordInput.addEventListener('input', function () {
                        const password = this.value;

                        let strength = 0;
                        if (password.length >= 8)
                            strength++;
                        if (password.match(/[a-z]/))
                            strength++;
                        if (password.match(/[A-Z]/))
                            strength++;
                        if (password.match(/[0-9]/))
                            strength++;
                        if (password.match(/[^a-zA-Z0-9]/))
                            strength++;

                        // Reset classes
                        strengthBar.className = 'password-strength-bar';

                        // Add appropriate strength class
                        if (password.length === 0) {
                            // No password, no bar
                        } else if (strength <= 2) {
                            strengthBar.classList.add('strength-weak');
                        } else if (strength <= 4) {
                            strengthBar.classList.add('strength-medium');
                        } else {
                            strengthBar.classList.add('strength-strong');
                        }
                    });
                }

                // Form validation
                const form = document.querySelector('form');
                if (form) {
                    form.addEventListener('submit', function (e) {
                        const password = document.getElementById('newPassword');
                        const confirmPassword = document.getElementById('confirmPassword');

                        if (password && confirmPassword) {
                            if (password.value !== confirmPassword.value) {
                                e.preventDefault();
                                alert('Passwords do not match!');
                                confirmPassword.focus();
                            }
                        }
                    });
                }

                // Real-time password match validation
                const confirmPasswordInput = document.getElementById('confirmPassword');
                if (confirmPasswordInput && passwordInput) {
                    confirmPasswordInput.addEventListener('input', function () {
                        const password = passwordInput.value;
                        const confirmPassword = this.value;

                        if (confirmPassword.length > 0) {
                            if (password === confirmPassword) {
                                this.style.borderColor = '#28a745';
                            } else {
                                this.style.borderColor = '#dc3545';
                            }
                        } else {
                            this.style.borderColor = '#e9ecef';
                        }
                    });
                }
            });
        </script>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
    </body>
</html>