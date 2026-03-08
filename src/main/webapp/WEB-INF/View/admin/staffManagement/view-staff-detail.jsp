<%-- 
    Document   : view-staff-detail
    Created on : Jun 12, 2025, 10:01:59 PM
    Author     : pc
--%>

<%@page import="model.Staff"%>
<%

    Staff sta = (Staff) request.getAttribute("data");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierLists5.css">
    </head>
    <body>
        <% if (sta == null) {
                out.print("<p>There is no customer with that id</p>");
            } else {
        %>
        <div class="container mt-5">
            <div class="card mx-auto shadow" style="max-width: 700px;">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Staff Detail</h4>
                </div>
                <form method="post" action="CustomerList?action=detail">
                    <div class="card-body">
                        <table class="table table-borderless">
                            <tr>
                                <th>Staff ID:</th>
                                <td>
                                    <%= sta.getStaffID()%>
                                </td>
                            </tr>
                            <tr>
                                <th>Full Name:</th>
                                <td>
                                    <%= sta.getFullName()%>
                                </td>
                            </tr>
                            <tr>
                                <th>Phone Number:</th>
                                <td>
                                    <%= sta.getPhone()%>
                                </td>
                            </tr>
                            
                            <tr>
                                <th>Email:</th>
                                <td>
                                    <%= sta.getEmail()%>
                                </td>
                            </tr>

                            <tr>
                                <th>Position:</th>
                                <td>
                                    <%= sta.getPosition()%>
                                </td>
                            </tr>
                            <tr>
                                <th>Date of Birth:</th>
                                <td>
                                    <%= sta.getBirthDay()%>
                                </td>
                            </tr>
                            <tr>
                                <th>Hired Date:</th>
                                <td>
                                    <%= sta.getHiredDate()%>
                                </td>
                            </tr>

                            <tr>
                                <th>Sex:</th>
                                <td>
                                    <input type="radio" class="form-check-input" name="sex" value="male"
                                           <%= ("male".equalsIgnoreCase(sta.getGender()) ? "checked" : "")%> disabled /> Male
                                    <input type="radio" class="form-check-input ms-3" name="sex" value="female"
                                           <%= ("female".equalsIgnoreCase(sta.getGender()) ? "checked" : "")%> disabled /> Female
                                </td>
                            </tr>
                        </table>
                        <div class="d-flex justify-content-between mt-4">
                            <div>
                                <a href="ChangePasswordStaff" class="btn btn-secondary" ><i class="bi bi-tools"></i> Reset Password</a>
                                <a href="StaffList" class="btn btn-outline-primary" id="back"><i class="bi bi-arrow-return-left"></i> Back to list</a>
                            </div>
                            <div>      
                                <a href="UpdateStaffServlet?action=update&id=<%= sta.getStaffID()%>" class="btn btn-warning">Update</a>
                                <a href="DeleteStaffServlet?action=delete&id=<%= sta.getStaffID()%>" class="btn btn-danger">Delete</a>
                            </div>
                        </div>
                </form>
                <%
                    }
                %>
                </body>
                </html>