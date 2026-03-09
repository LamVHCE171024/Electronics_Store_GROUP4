<%-- 
    Document   : save-phone
    Created on : Jul 20, 2025, 1:44:06 PM
    Author     : pc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nhập số điện thoại</title>
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

            .welcome-message {
                color: #667eea;
                font-size: 18px;
                font-weight: 500;
                margin-bottom: 15px;
                text-align: center;
            }

            .instruction-text {
                color: #6c757d;
                font-size: 14px;
                margin-bottom: 25px;
                text-align: center;
                line-height: 1.5;
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

            .btn-confirm {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                color: white;
            }

            .btn-confirm:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
                color: white;
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

            .form-label {
                color: #333;
                font-weight: 500;
                margin-bottom: 8px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="login-container">
            <div class="login-card">
                <h1 class="login-title">
                    <i class="bi bi-person-check me-2"></i>
                    Hoàn tất đăng ký
                </h1>

                <div class="welcome-message">
                    <i class="bi bi-emoji-smile me-2"></i>
                    Chào <%= session.getAttribute("name") != null ? session.getAttribute("name") : "bạn"%>!
                </div>

                <div class="instruction-text">
                    Bạn chưa có tài khoản trong hệ thống. Vui lòng nhập số điện thoại để hoàn tất đăng ký:
                </div>

                <form action="LoginGoogle" method="post">
                    <div class="mb-4">
                        <label for="phone" class="form-label">
                            <i class="bi bi-telephone me-2"></i>Số điện thoại
                        </label>
                        <input type="text" 
                               class="form-control" 
                               id="phone" 
                               name="phone" 
                               required 
                               placeholder="Nhập số điện thoại của bạn"
                               maxlength="12">
                    </div>

                    <button type="submit" class="btn btn-confirm">
                        <i class="bi bi-check-circle me-2"></i>
                        Xác nhận
                    </button>
                </form>

                <% if (request.getAttribute("error") != null) {%>
                <div class="error-message">
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    <%= request.getAttribute("error")%>
                </div>
                <% }%>
            </div>
        </div>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>