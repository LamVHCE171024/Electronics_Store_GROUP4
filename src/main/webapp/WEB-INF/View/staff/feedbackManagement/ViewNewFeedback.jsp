<%-- 
    Document   : ViewNewFeedback
    Created on : Mar 2, 2025, 10:17:23 AM
    Author     : Vinhntce181630
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product Reviews</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">


        <!-- SweetAlert2 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>
            body {
                background-color: #f4f6fb;
                font-family: 'Segoe UI', sans-serif;
            }
            h1 {
                color: #232946;
                margin-top: 24px;
                font-weight: 800;
                font-size: 2.5rem;
                letter-spacing: 1px;
                margin-bottom: 20px;
            }

            .main-content {
                margin-left: 270px;
                padding: 30px;
                width: calc(100% - 270px);
            }

            .banner h2 {
                font-weight: 700;
                color: #333;
            }

            .banner h3 {
                font-size: 20px;
                color: #666;
                margin-bottom: 30px;
            }

            .review-card {
                background-color: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.06);
                padding: 20px;
                margin-bottom: 20px;
            }

            .star-rating i {
                color: #ffc107;
                font-size: 18px;
            }

            .btn-toggle,
            .reply-btn,
            .update-btn,
            .delete-btn {
                border-radius: 8px;
                font-weight: 600;
            }

            .btn-toggle {
                padding: 6px 12px;
            }

            .hidden-feedback {
                color: #888;
                font-style: italic;
            }

            .reply-container {
                margin-left: 30px;
                padding: 12px 15px;
                border-left: 4px solid #0d6efd;
                background-color: #f1f5f9;
                border-radius: 8px;
                margin-top: 10px;
            }

            .reply-container strong {
                color: #0d6efd;
            }

            .reply-form {
                display: none;
                margin-top: 15px;
            }

            .reply-form textarea {
                border-radius: 8px;
                min-height: 100px;
                resize: vertical;
            }

            .reply-form button {
                border-radius: 8px;
                font-weight: 600;
            }

            .badge-status {
                padding: 6px 12px;
                border-radius: 999px;
                font-weight: 600;
                color: #fff;
                font-size: 14px;
            }

            .modal-content {
                border-radius: 10px;
            }

            .modal-body {
                padding: 20px;
            }

            .fixed-header {
                position: fixed;
                top: 0;
                left: 250px;
                width: calc(100% - 250px);
                z-index: 1050;
                padding: 10px 20px;
                background-color: white;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.05);
            }

            .btn-primary {
                background-color: #0d6efd;
                border: none;
            }

            .btn-primary:hover {
                background-color: #0b5ed7;
            }

            .btn-danger {
                background-color: #dc3545;
            }

            .btn-danger:hover {
                background-color: #bb2d3b;
            }

            .btn-warning {
                background-color: #f59e0b;
                color: #fff;
            }

            .btn-warning:hover {
                background-color: #d97706;
            }

            .btn-success {
                background-color: #22c55e;
                color: #fff;
            }

            .btn-success:hover {
                background-color: #16a34a;
            }
            main.main-content {
                flex: 1;
                margin-left: 227.5px;
                min-height: 100vh;
                box-sizing: border-box;
            }

        </style>

    </head>
    <body>

        <div class="container">
            <jsp:include page="../sideBar.jsp" />
            <jsp:include page="../header.jsp" />
            <div class="wrapper">
                <main class="main-content">

                    <div class="banner">
                        <h1>Customer Reviews</h1>              
                    </div>
                    <%--<c:forEach var="rate" items="${dataRating}">--%>
                    <div class="review-card">
                        <h3>[Product ID: ${Product.productId}] "${Product.productName}" - 
                            <fmt:formatNumber value="${Product.price}" type="number" groupingUsed="true" />đ</h3>

                        <p class="text-muted">Reviewed by: <strong>${cus.fullName}</strong></p>
                        <div class="star-rating">
                            <c:forEach var="i" begin="1" end="5">
                                <c:choose>
                                    <c:when test="${i <= rate.star}">
                                        <i class="fa fa-star"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fa fa-star text-muted"></i>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>

                        <!-- Comment -->
                        <p id="comment-${rate.rateID}" data-original="${rate.comment}" class="${rate.isDeleted ? 'hidden-feedback' : ''}">
                            ${rate.isDeleted ? "This feedback was hidden for some reason." : rate.comment}
                        </p>

                        <!-- Toggle Ẩn/Hiện -->
                        <button id="toggle-btn-${rate.rateID}" class="btn btn-toggle ${rate.isDeleted ? 'btn-warning' : 'btn-success'} btn-sm" onclick="toggleVisibility(${rate.rateID}, ${rate.isDeleted ? 1 : 0})">
                            <i class="fa ${rate.isDeleted ? 'fa-eye' : 'fa-eye-slash'}"></i>
                            ${rate.isDeleted ? "Show" : "Hidden"}
                        </button>

                        <!-- Reply Button -->
                        <button class="reply-btn btn-sm" onclick="toggleReplyForm(${rate.rateID})">
                            <i class="fa fa-reply"></i> Reply
                        </button>

                        <!-- Reply List -->
                        <c:forEach var="reply" items="${dataReplies}">
                            <c:if test="${reply.rateID == rate.rateID}">
                                <div id="reply-container-${reply.replyID}" class="reply-container">
                                    <strong>Staff</strong>
                                    <p>${reply.answer}</p>

                                    <button class="update-btn btn btn-primary btn-sm" onclick="openUpdateModal(${reply.replyID}, '${reply.answer}', ${rate.rateID})">
                                        <i class="fa fa-edit"></i> Update
                                    </button>

                                    <button class="delete-btn btn btn-danger btn-sm" onclick="openDeleteModal(${reply.replyID})">
                                        <i class="bi bi-trash"></i> Delete
                                    </button>
                                </div>
                            </c:if>
                        </c:forEach>


                        <!-- Reply Form -->
                        <div id="replyForm-${rate.rateID}" class="reply-form">
                            <form method="POST" action="ReplyFeedback">
                                <input type="hidden" name="rateID" value="${rate.rateID}">
                                <textarea required="true" name="Answer" class="form-control" placeholder="Write your reply..."></textarea>
                                <button type="submit" class="btn btn-primary btn-sm mt-2">Submit Reply</button>
                            </form>
                        </div>
                    </div>
                    <%--</c:forEach>--%>
            </div>



            <!-- Success Modal -->
            <div id="successModal" class="modal">
                <div class="modal-content">
                    <h3>Action Successful!</h3>
                    <p>You will be redirected shortly...</p>
                </div>
            </div>



            <script>
                // Hàm hiển thị popup thành công và tự động chuyển hướng sau 2 giây
                function showSuccessAndRedirect() {
                    // Hiển thị modal thành công
                    var successModal = new bootstrap.Modal(document.getElementById('successModal'));
                    successModal.show();

                    // Chuyển hướng sau 2 giây
                    setTimeout(function () {
                        window.location.href = "ViewListFeedback"; // Cập nhật URL nếu cần
                    }, 2000);
                }



                // Hàm xử lý khi submit trả lời
                function submitReply(rateID) {
                    let replyText = document.querySelector("#replyForm-" + rateID + " textarea").value;
                    let xhr = new XMLHttpRequest();
                    xhr.open("POST", "ReplyFeedbackServlet", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            let response = xhr.responseText.trim();
                            if (response === "Success") {
                                showSuccessAndRedirect(); // Hiển thị popup thành công và chuyển hướng
                            } else {
                                alert("Failed to submit the reply. Please try again.");
                            }
                        }
                    };
                    xhr.send("rateID=" + rateID + "&Answer=" + encodeURIComponent(replyText));
                }



                function toggleReplyForm(rateID) {
                    let form = document.getElementById("replyForm-" + rateID);
                    form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
                }

                function toggleVisibility(rateID, currentStatus) {
                    let newStatus = currentStatus === 1 ? 0 : 1;

                    let xhr = new XMLHttpRequest();
                    xhr.open("POST", "UpdateStatusComment", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            let btn = document.getElementById("toggle-btn-" + rateID);
                            let comment = document.getElementById("comment-" + rateID);

                            if (newStatus === 1) {
                                btn.innerHTML = '<i class="fa fa-eye"></i> Show';
                                btn.classList.remove("btn-success");
                                btn.classList.add("btn-warning");

                                comment.setAttribute("data-original", comment.innerHTML);
                                comment.innerHTML = "This feedback was hidden for some reason.";
                                comment.classList.add("hidden-feedback");
                            } else {
                                btn.innerHTML = '<i class="fa fa-eye-slash"></i> Hidden';
                                btn.classList.remove("btn-warning");
                                btn.classList.add("btn-success");

                                let originalContent = comment.getAttribute("data-original");
                                if (originalContent) {
                                    comment.innerHTML = originalContent;
                                }
                                comment.classList.remove("hidden-feedback");
                            }

                            btn.setAttribute("onclick", "toggleVisibility(" + rateID + ", " + newStatus + ")");
                        } else {
                            console.error("error form server:", xhr.status, xhr.responseText);
                        }
                    };

                    xhr.send("rateID=" + rateID + "&isDeleted=" + newStatus);
                }

                function openDeleteModal(replyID) {
                    Swal.fire({
                        title: 'Are you sure?',
                        text: "You won't be able to revert this reply!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#6c757d',
                        confirmButtonText: 'Yes, delete it!'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Gửi request xoá nếu xác nhận
                            let xhr = new XMLHttpRequest();
                            xhr.open("POST", "DeleteReply", true);
                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                            xhr.onreadystatechange = function () {
                                if (xhr.readyState === 4) {
                                    if (xhr.status === 200 && xhr.responseText.trim() === "Success") {
                                        Swal.fire({
                                            icon: 'success',
                                            title: 'Deleted!',
                                            text: 'The reply has been deleted.',
                                            timer: 1500,
                                            showConfirmButton: false
                                        }).then(() => {
                                            location.reload(); // Reload lại trang
                                        });
                                    } else {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Failed',
                                            text: 'Something went wrong. Please try again.'
                                        });
                                    }
                                }
                            };

                            xhr.send("replyID=" + replyID);
                        }
                    });
                }
                function openUpdateModal(replyID, currentText, rateID) {
                    if (!replyID) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Invalid reply ID'
                        });
                        return;
                    }
                    Swal.fire({
                        title: 'Update Reply',
                        input: 'textarea',
                        inputLabel: 'Your reply',
                        inputValue: currentText,
                        showCancelButton: true,
                        confirmButtonText: 'Save',
                        cancelButtonText: 'Cancel',
                        inputValidator: (value) => {
                            if (!value.trim()) {
                                return 'Reply cannot be empty!';
                            }
                        }
                    }).then((result) => {
                        if (result.isConfirmed) {
                            const updatedText = result.value.trim();
                            let xhr = new XMLHttpRequest();
                            xhr.open("POST", "UpdateReply", true);
                            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                            xhr.onreadystatechange = function () {
                                if (xhr.readyState === 4) {
                                    console.log("UpdateReply response:", xhr.responseText, "Status:", xhr.status);
                                    if (xhr.status === 200 && xhr.responseText.trim() === "Success") {
                                        Swal.fire({
                                            icon: 'success',
                                            title: 'Updated!',
                                            text: 'The reply has been updated.',
                                            timer: 1500,
                                            showConfirmButton: false
                                        }).then(() => {
                                            location.reload();
                                        });
                                    } else {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Failed',
                                            text: 'Failed to update reply: ' + xhr.responseText
                                        });
                                    }
                                }
                            };
                            xhr.send("replyID=" + encodeURIComponent(replyID) + "&answer=" + encodeURIComponent(updatedText));
                        }
                    });
                }
                <% String success = request.getParameter("success");
                    String rateID = request.getParameter("rateID"); // lấy từ URL
                %>
                window.onload = function () {
                <% if ("success".equals(success)) {%>
                    Swal.fire({
                        icon: 'success',
                        title: 'Reply Submitted!',
                        text: 'Your reply was sent successfully.',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        window.location.href = '/TMobile/ViewFeedBackForStaff?rateID=<%=rateID%>';
                    });
                <% } else if ("failed".equals(success)) { %>
                    Swal.fire({
                        icon: 'error',
                        title: 'Reply Failed!',
                        text: 'Failed to submit your reply.',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        location.reload();
                    });
                <% } else if ("error".equals(success)) {%>
                    Swal.fire({
                        icon: 'error',
                        title: 'Server Error!',
                        text: 'Something went wrong while processing your reply.',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        window.location.href = '/TMobile/ViewFeedBackForStaff?rateID=<%=rateID%>';
                    });
                <% } else if ("nostaff".equals(success)) {%>
                    Swal.fire({
                        icon: 'warning',
                        title: 'Permission Denied!',
                        text: 'Staff ID not found for this account.',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        window.location.href = '/TMobile/ViewFeedBackForStaff?rateID=<%=rateID%>';
                    });
                <% }%>
                };

            </script>

    </body>
</html>

