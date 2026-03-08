<%@page import="model.Customer"%>
<%@page import="model.Account"%>
<%@page import="model.Staff"%>
<%
    Integer accountId = (Integer) session.getAttribute("accountId");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <!-- Import d?ng chung file CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/profile.css">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="main-account container-fluid">

            <jsp:include page="/WEB-INF/View/customer/sideBar.jsp" />
            <div class="profile-card">
                <div class="profile-header">
                    <h4>
                        <i class="bi bi-shield-lock me-2"></i>
                        Change Password
                    </h4>
                </div>
                <div class="profile-body">
                    <form method="post" action="ChangePassword">
                        <div class="form-group">
                            <label for="oldPassword" class="form-label">
                                <i class="bi bi-lock me-2"></i>Old Password
                            </label>
                            <input type="password" class="form-control" name="oldPassword" id="oldPassword" required placeholder="Enter your current password">
                                   
                        </div>
                        <div class="form-group">
                            <label for="newPassword" class="form-label">
                                <i class="bi bi-key me-2"></i>New Password
                            </label>
                            <input type="password" class="form-control" name="newPassword" id="newPassword" required placeholder="Enter your new password!">
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword" class="form-label">
                                <i class="bi bi-key-fill me-2"></i>Confirm New Password
                            </label>
                            <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" required placeholder="Confirm your new password!">
                        </div>
                        <div class="profile-actions">
                            <a href="ViewProfile" class="btn-cancel">
                                <i class="bi bi-arrow-left me-1"></i> Back
                            </a>
                            <button type="submit" class="btn-update">
                                <i class="bi bi-shield-check me-2"></i>
                                Change Password
                            </button>
                        </div>
                    </form>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success" role="alert">
                            ${success}
                        </div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </c:if>

                        </div>
                        </div>

                </div>

        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
