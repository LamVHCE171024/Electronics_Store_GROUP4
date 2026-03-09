<%-- 
    Document   : forgot-password.jsp
    Created on : Jul 9, 2025, 10:47:36 PM
    Author     : pc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
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

            .btn-primary {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 15px;
                color: white;
            }

            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
                color: white;
            }

            .btn-secondary {
                background: linear-gradient(135deg, #6c757d 0%, #5a6268 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 20px;
                color: white;
            }

            .btn-secondary:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(108, 117, 125, 0.3);
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

            .success-message {
                background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
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
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="login-container">
            <div class="login-card">
                <h2 class="login-title">
                    <i class="bi bi-key-fill me-2"></i>
                    Forgot Password
                </h2>

                <p class="description-text">
                    Enter your email address and we'll send you an OTP code to reset your password.
                </p>

                <% String error = (String) request.getAttribute("error");
                   if (error != null) { %>
                    <div class="error-message">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <%= error %>
                    </div>
                <% } %>

                <% String message = (String) request.getAttribute("message");
                   if (message != null) { %>
                    <div class="success-message">
                        <i class="bi bi-check-circle-fill me-2"></i>
                        <%= message %>
                    </div>
                <% } %>

                <form method="post" action="ForgotPassword">
                    <div class="input-group">
                        <i class="bi bi-envelope-fill input-icon"></i>
                        <input type="email" name="email" class="form-control with-icon"
                               placeholder="Enter your email address" required
                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : ""%>">
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-send-fill me-2"></i>
                        Send OTP
                    </button>

                    <div class="divider">
                        <span>Remember your password?</span>
                    </div>

                    <a href="Login" class="btn btn-secondary">
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
                // Email validation
                const emailInput = document.querySelector('input[name="email"]');
                if (emailInput) {
                    emailInput.addEventListener('input', function () {
                        const email = this.value;
                        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                        
                        if (email.length > 0) {
                            if (emailRegex.test(email)) {
                                this.style.borderColor = '#28a745';
                            } else {
                                this.style.borderColor = '#dc3545';
                            }
                        } else {
                            this.style.borderColor = '#e9ecef';
                        }
                    });
                }

                // Form submission with loading state
                const form = document.querySelector('form');
                const submitBtn = document.querySelector('button[type="submit"]');
                
                if (form && submitBtn) {
                    form.addEventListener('submit', function (e) {
                        // Add loading state
                        submitBtn.disabled = true;
                        submitBtn.innerHTML = '<i class="bi bi-hourglass-split me-2"></i>Sending...';
                        
                        // Re-enable button after 5 seconds in case of network issues
                        setTimeout(() => {
                            submitBtn.disabled = false;
                            submitBtn.innerHTML = '<i class="bi bi-send-fill me-2"></i>Send OTP';
                        }, 5000);
                    });
                }
            });
        </script>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
    </body>
</html>