<%-- 
    Document   : login
    Created on : Jun 11, 2025, 4:10:00 PM
    Author     : pc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            .login-container {
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .login-card {
                background: rgba(255, 255, 255, 0.95);
                backdrop-filter: blur(10px);
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                padding: 40px;
                width: 100%;
                max-width: 450px;

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
            }

            .form-control:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
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
            }

            .btn-login:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
            }

            .btn-google {
                background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
                border: none;
                color: white !important;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 20px;
            }

            .btn-google:hover {
                background: linear-gradient(135deg, #2a5298 0%, #1e3c72 100%);
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(30, 60, 114, 0.3);
                color: white !important;
            }

            .btn-register {
                background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 15px;
            }

            .btn-register:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(17, 153, 142, 0.3);
            }

            .forgot-password {
                color: #667eea;
                text-decoration: none;
                font-size: 14px;
                transition: all 0.3s ease;
                display: block;
                text-align: center;
            }

            .forgot-password:hover {
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
                margin-top: 20px;
                text-align: center;
                font-weight: 500;
            }
            .login-container img{
                margin-right: 200px;
                margin-left: -20px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="login-container">
            <img src="Image/sanpham.jpg"  width="700" height="600">
            <div class="login-card">
                <h1 class="login-title">
                    <i class="bi bi-person-circle me-2"></i>
                    Login 
                </h1>

                <form method="post" action="Login">
                    <div class="mb-3">
                        <label for="email" class="form-label">
                            <i class="bi bi-envelope me-2"></i>Email
                        </label>
                        <input type="text" class="form-control" name="email" id="u_id" required placeholder="Enter your email">
                    </div>

                    <div class="mb-4">
                        <label for="password" class="form-label">
                            <i class="bi bi-lock me-2"></i>Password
                        </label>
                        <input type="password" class="form-control" name="pass" id="u_pwd" required placeholder="Enter your password">
                    </div>

                    <button type="submit" class="btn btn-primary btn-login">
                        <i class="bi bi-box-arrow-in-right me-2"></i>
                        Login
                    </button>
                </form>

                <!-- Google Login Button -->
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile&access_type=online&include_granted_scopes=true&response_type=code&redirect_uri=http://localhost:8080/TMobile/LoginGoogle&client_id=311074511236-tveal4v1lt2euj2o36qpvrseigthsfol.apps.googleusercontent.com"
                   class="btn btn-google">
                    <i class="bi bi-google me-2"></i>
                    Login with Google
                </a>

                <div class="divider">
                    <span>or</span>
                </div>

                <a href="Register" class="btn btn-success btn-register">
                    <i class="bi bi-person-plus me-2"></i>
                    Create Account
                </a>

                <a href="ForgotPassword" class="forgot-password">
                    <i class="bi bi-question-circle me-1"></i>
                    Forgot Password ?
                </a>

                <%
                    String err = (String) request.getAttribute("err");
                    if (err != null) {
                %>
                <div class="error-message">
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    <%= err%>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>