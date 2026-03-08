<%-- 
    Document   : viewCategoryList
    Created on : Jun 13, 2025, 11:27:19 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body></body>
    <script>
        function confirmDelete(productId) {
            Swal.fire({
                title: 'Are you sure?',
                text: "This product will be hidden from view.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Delete',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Chuyển sang servlet xử lý
                    window.location.href = 'StaffDeleteProduct?productId=' + productId;
                }
            });
        }
    </script>
</html>



