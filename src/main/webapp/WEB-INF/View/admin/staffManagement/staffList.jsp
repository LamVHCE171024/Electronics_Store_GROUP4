<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.Staff"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Staff List</title>
        <!-- Bootstrap CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <!-- Fontawesome CDN -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
        <!-- Sidebar CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/sideBar.css">

        <!-- Dashboard CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierList5.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <div class="wrapper">
                <main class="main-content">
                    <h1>Staff List</h1>
                    <button class="create-btn" onclick="location.href = 'CreateStaffServlet'">Create</button>


                    <!-- Search Form -->
                    <form class="search-form" action="StaffList" method="get">
                        <input type="hidden" name="action" value="search">
                        <input type="text" name="keyword" class="form-control" placeholder="Search staff by name">
                        <button type="submit" class="search-btn">Search</button>
                    </form>

                    <!-- Staff Table -->
                    <table aria-label="Staff table">
                        <thead>
                            <tr>
                                <th>Staff ID</th>
                                <th>Email</th>
                                <th>Full Name</th>
                                <th>Hired Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Staff> staList = (List<Staff>) request.getAttribute("staff");
                                if (staList != null && !staList.isEmpty()) {
                                    for (Staff sta : staList) {
                            %>
                            <tr>
                                <td><%= sta.getStaffID()%></td>
                                <td><%= sta.getEmail()%></td>
                                <td><%= sta.getFullName()%></td>
                                <td><%= sta.getHiredDate()%></td>
                                <td class="action-col">
<a href="StaffList?action=detail&id=<%= sta.getStaffID()%>" class="btn btn-primary">Detail</a>
                                    <a href="UpdateStaffServlet?action=update&id=<%= sta.getStaffID()%>" class="btn btn-warning">Edit</a>
                                    <button class="btn btn-danger" onclick="confirmDeleteStaff(<%= sta.getStaffID()%>)">Delete</button>

                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="5" class="text-center">No staff found!</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>

                    <!-- Optional message -->
                    <%
                        String mes = (String) request.getAttribute("message");
                        if (mes != null) {
                    %>
                    <div class="alert alert-info mt-3"><%= mes%></div>
                    <%
                        }
                    %>
                </main>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
<% String successdelete = request.getParameter("successdelete"); %>
<% String errordelete = request.getParameter("errordelete"); %>
<% String successedit = request.getParameter("successedit"); %>
<% String erroredit = request.getParameter("erroredit"); %>
<% String successcreate = request.getParameter("successcreate"); %>
<% String errorcreate = request.getParameter("errorcreate"); %>



<script>



                                        function confirmDeleteStaff(staffID) {
                                            Swal.fire({
                                                title: 'Are you sure?',
                                                text: "This staff will be deleted.",
                                                icon: 'warning',
                                                showCancelButton: true,
                                                confirmButtonColor: '#d33',
                                                cancelButtonColor: '#3085d6',
                                                confirmButtonText: 'Delete',
                                                cancelButtonText: 'Cancel'
                                            }).then((result) => {
                                                if (result.isConfirmed) {
                                                    window.location.href = 'DeleteStaffServlet?action=delete&id=' + staffID;
                                                }
                                            });
}
                                        window.onload = function () {
    <% if ("1".equals(successdelete)) { %>
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Deleted!',
                                                text: 'The staff has been deleted.',
                                                timer: 2000
                                            });
    <% } else if ("1".equals(errordelete)) { %>
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Failed!',
                                                text: 'Could not delete the staff.',
                                                timer: 2000
                                            });
    <% }%>
    <% if ("1".equals(successedit)) { %>
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Edited!',
                                                text: 'The staff has been edited.',
                                                timer: 2000
                                            });
    <% } else if ("1".equals(erroredit)) { %>
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Failed!',
                                                text: 'Could not edit the staff.',
                                                timer: 2000
                                            });
    <% }%>    <% if ("1".equals(successcreate)) { %>
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Created!',
                                                text: 'The staff has been created.',
                                                timer: 2000
                                            });
    <% } else if ("1".equals(errorcreate)) { %>
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Failed!',
                                                text: 'Could not created the staff.',
                                                timer: 2000
                                            });
    <% }%>
                                        };
</script>