<%@page import="model.Account"%>
<%@page import="model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Customer sta = (Customer) request.getAttribute("cus");
    session.setAttribute("customer", sta);

    Account acc = (Account) session.getAttribute("user");
    boolean hasPassword = false;
    if (acc.getPasswordHash() != null && !acc.getPasswordHash().isEmpty()) {
        hasPassword = true;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile - TShop</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/profile.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />

        <div class="main-account container-fluid">
            <!-- SIDEBAR -->
            <jsp:include page="/WEB-INF/View/customer/sideBar.jsp" />

            <!-- PROFILE -->
            <div class="profile-card">
                <div class="profile-header">
                    <h4>
                        <i class="bi bi-person-circle me-2"></i>
                        Profile Information
                    </h4>
                </div>
                <form method="get" action="ViewProfile?action=list">
                    <div class="profile-body">
                        <table class="table profile-table">
                            <tr>
                                <th><i class="bi bi-hash profile-icon"></i> ID:</th>
                                <td class="profile-value"><%= sta.getId()%></td>
                            </tr>
                            <tr>
                                <th><i class="bi bi-person profile-icon"></i> Full Name:</th>
                                <td class="profile-value"><%= sta.getFullName()%></td>
                            </tr>
                            <tr>
                                <th><i class="bi bi-telephone profile-icon"></i> Phone Number:</th>
                                <td>
                                    <%= (sta.getPhone() == null || sta.getPhone().isEmpty())
                                            ? "<span class='error-message'><i class='bi bi-exclamation-triangle me-1'></i>Please enter your phone number</span>"
                                            : "<span class='profile-value'>" + sta.getPhone() + "</span>"%>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="bi bi-envelope profile-icon"></i> Email:</th>
                                <td class="profile-value"><%= sta.getEmail()%></td>
                            </tr>
                            <tr>
                                <th><i class="bi bi-calendar profile-icon"></i> Date of Birth:</th>
                                <td>
                                    <%
                                        String birth = sta.getBirthDay();
                                        String birthFormatted = "";
                                        if (birth != null && !birth.isEmpty()) {
                                            try {
                                                java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                                java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
                                                java.util.Date birthDate = inputFormat.parse(birth);
                                                birthFormatted = outputFormat.format(birthDate);
                                            } catch (Exception e) {
                                                birthFormatted = "Your birthday is invalid";
                                            }
                                        }
                                    %>
                                    <%= (birth == null || birth.isEmpty())
                                            ? "<span class='error-message'><i class='bi bi-exclamation-triangle me-1'></i>Please enter your date of birth</span>"
                                            : "<span class='profile-value'>" + birthFormatted + "</span>"%>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="bi bi-gender-ambiguous profile-icon"></i> Sex:</th>
                                <td>
                                    <div class="gender-options">
                                        <div class="gender-option">
                                            <input type="radio" class="form-check-input" name="sex" value="male"
                                                   <%= ("male".equalsIgnoreCase(sta.getGender()) ? "checked" : "")%> disabled />
                                            <span>Male</span>
                                        </div>
                                        <div class="gender-option">
                                            <input type="radio" class="form-check-input" name="sex" value="female"
                                                   <%= ("female".equalsIgnoreCase(sta.getGender()) ? "checked" : "")%> disabled />
                                            <span>Female</span>
                                        </div>
                                    </div>
                                    <% if (sta.getGender() == null || sta.getGender().trim().isEmpty()) { %>
                                    <div class="mt-2">
                                        <span class="error-message">
                                            <i class="bi bi-exclamation-triangle me-1"></i>
                                            Please enter your gender
                                        </span>
                                    </div>
                                    <% }%> 
                                </td>
                            </tr>
                        </table>
                        <div class="profile-actions">
                            <% if (hasPassword) { %>
                            <a href="ChangePassword" class="btn-cancel">
                                <i class="bi bi-shield-lock me-2"></i>
                                Change Password
                            </a>
                            <% }%>
                            <a href="UpdateProfile" class="btn-update">
                                <i class="bi bi-pencil-square me-2"></i>
                                Update Profile
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>