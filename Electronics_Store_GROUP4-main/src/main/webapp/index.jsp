<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TShop - Electronics Store</title>
    <meta http-equiv="refresh" content="0;url=<%=request.getContextPath()%>/Home">
</head>
<body>
    <%
        response.sendRedirect(request.getContextPath() + "/Home");
    %>
</body>
</html>
