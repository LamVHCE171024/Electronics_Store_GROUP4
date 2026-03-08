<%-- 
    Document   : view-customer-detail.jsp
    Created on : Jun 10, 2025, 2:40:25 PM
    Author     : pc
--%>
<%@page import="java.util.List"%>
<%@page import="model.Customer"%>
<%

    Customer custo = (Customer) request.getAttribute("data");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css" />
    </head>
    <body>
        <div class="container">

            <% if (custo == null) {
                    out.print("<p>There is no customer with that id</p>");
                } else {
            %>
            <jsp:include page="../sideBar.jsp" />   


            <div class="wrapper">
                <main class="main-content">

                    <jsp:include page="../header.jsp" />
                    <div class="container mt-5">
                        <div class="card mx-auto shadow" style="max-width: 700px; margin-left: 50px">
                            <div class="card-header bg-primary text-white">
                                <h4 class="mb-0">Customer Detail</h4>
                            </div>
                            <form method="post" action="CustomerList?action=detail">
                                <div class="card-body">
                                    <div class="mb-3">
                                        <table class="table table-borderless">
                                            <tr>
                                                <th>Customer ID:</th>
                                                <td><%= custo.getId()%></td>
                                            </tr>
                                            <tr>
                                                <th>Full Name:</th>
                                                <td><%= custo.getFullName()%></td>
                                            </tr>
                                            <tr>
                                                <th>Phone Number:</th>
                                                <td><%= custo.getPhone()%></td>
                                            </tr>
                                            <tr>
                                                <th>Email:</th>
                                                <td><%= custo.getEmail()%></td>
                                            </tr>
                                            <tr>
                                                <th>Status:</th>
                                                <td><%= custo.isActive() ? "Active" : "Block"%></td>
                                            </tr>
                                            <tr>
                                                <th>Date of Birth:</th>
                                                <td><%= custo.getBirthDay()%></td>
                                            </tr>
                                            <tr>
                                                <th>Sex:</th>
                                                <td>
                                                    <input type="radio" class="form-check-input" name="sex" value="male"
                                                           <%= ("male".equalsIgnoreCase(custo.getGender()) ? "checked" : "")%> disabled /> Male
                                                    <input type="radio" class="form-check-input ms-3" name="sex" value="female"
                                                           <%= ("female".equalsIgnoreCase(custo.getGender()) ? "checked" : "")%> disabled /> Female
                                                </td>
                                            </tr>
                                        </table>
                                        <a href="CustomerList" class="btn btn-primary" id="back"><i class="bi bi-arrow-return-left"></i>Back to list</a>
                                    </div>
                            </form>
                            <%
                                }
                            %>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
