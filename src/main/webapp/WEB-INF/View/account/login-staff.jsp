<%-- 
    Document   : login-staff
    Created on : Jun 13, 2025, 11:09:47 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Staff Login Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/loginAdmin.css">

    </head>
    <body>
        <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
            <div class="login-container">
                <div class="login-header">
                    <h1><i class="bi bi-shield-lock-fill"></i>Login for Staff</h1>
                    <p>Sign in to access your dashboard</p>
                </div>

                <form method="post" action="LoginStaff" id="loginForm">
                    <div class="form-group">
                        <label for="u_id">Email</label>
                        <input type="email" name="email" id="u_id" placeholder="Enter your email" required>
                    </div>

                    <div class="form-group">
                        <label for="u_pwd">Password</label>
                        <input type="password" name="pass" id="u_pwd" placeholder="Enter your password" required>
                    </div>

                    <button type="submit" class="login-btn" id="loginButton">
                        <i class="bi bi-person-circle"></i>
                        Login
                    </button>
                </form>

                <%
                    String err = (String) request.getAttribute("err");
                    if (err != null && !err.isEmpty()) {
                %>
                <div class="alert error-alert" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <%= err%>
                </div>
                <%
                    }
                %>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>