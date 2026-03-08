<%-- 
    Document   : error
    Created on : Aug 3, 2025, 5:02:04 PM
    Author     : Tai
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error - Page Not Found</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #fff;
            margin: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        header {
            background: #e53935;
            color: #fff;
            padding: 18px 0;
            font-size: 1.7rem;
            font-weight: bold;
            letter-spacing: 1px;
            box-shadow: 0 1px 4px #eee;
        }
        footer {
            background: #f4f4f4;
            color: #666;
            text-align: center;
            padding: 16px 0 10px 0;
            margin-top: auto;
            font-size: 1rem;
            border-top: 1px solid #eee;
        }
        .container {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .img-err {
            width: 240px;
            max-width: 90vw;
            margin-bottom: 32px;
        }
        .title {
            font-size: 2.2rem;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .subtitle {
            font-size: 1.1rem;
            margin-bottom: 30px;
        }
        .hotline {
            color: #e53935;
            font-weight: bold;
        }
        .btn {
            display: inline-block;
            padding: 12px 32px;
            font-size: 1.1rem;
            background: #e53935;
            color: #fff;
            border: none;
            border-radius: 30px;
            text-decoration: none;
            font-weight: bold;
            transition: background 0.2s;
            margin-top: 6px;
        }
        .btn:hover {
            background: #b71c1c;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />

    
    <!-- Main content -->
    <div class="container">
        <img src="https://www.freeiconspng.com/thumbs/error-icon/error-icon-32.png" alt="error" class="img-err">
        <div class="title">The link has expired or does not exist</div>
        <div class="subtitle">
            You can contact our free hotline <span class="hotline">1800 6601</span> for support.
        </div>
        <a href="${pageContext.request.contextPath}/Home" class="btn">Back to Home</a>
    </div>
    
   
   <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
 
</body>
</html>
