<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Address" %>
<%
    List<Address> addressList = (List<Address>) request.getAttribute("addressList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Addresses</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/profile.css">
    <style>
        .address-row {
            padding: 18px 0 14px 0;
            border-bottom: 1px solid #eee;
            font-size: 17px;
        }
        .address-row:last-child { border-bottom: none; }
        .address-actions {
            display: flex;
            gap: 10px;
            margin-top: 6px;
        }
        .default-label {
            color: #f44336;
            font-weight: 600;
            font-size: 15px;
            margin-top: 4px;
            margin-left: 2px;
        }
        .set-default-link {
            background: none;
            border: none;
            color: #888;
            font-size: 15px;
            font-weight: 600;
            padding: 0 8px;
            cursor: pointer;
            border-radius: 8px;
            display: flex;
            align-items: center;
        }
        /* Nút nhỏ */
        .btn-update, .btn-cancel {
            padding: 7px 20px;
            font-size: 15px;
            min-width: unset;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />

<div class="main-account container-fluid">
    <jsp:include page="/WEB-INF/View/customer/sideBar.jsp" />

    <div class="profile-card">
        <div class="profile-header d-flex justify-content-between align-items-center">
            <h4 style="display:inline; margin-bottom:0;">
                <i class="bi bi-geo-alt-fill me-2"></i>
                My Addresses
            </h4>
            <a href="AddAddress" class="btn-update" style="font-size:15px;">
                <i class="bi bi-plus-lg me-1"></i> Add Address
            </a>
        </div>
        <div class="profile-body">
            <h6 style="color:#333; margin-bottom:20px; margin-top:6px; font-weight:500;">Your Saved Addresses</h6>
            <div>
                <% if (addressList != null && addressList.size() > 0) {
                    for (Address addr : addressList) { %>
                    <div class="address-row">
                        <div>
                            <strong><%= addr.getAddressDetails()%></strong>,
                            <%= addr.getWardName()%>,
                            <%= addr.getDistrictName()%>,
                            <%= addr.getProvinceName()%>
                        </div>
                        <% if (addr.isDefault()) { %>
                            <div class="default-label">Default</div>
                            <div class="address-actions">
                                <a class="btn-update" href="UpdateAddress?id=<%=addr.getAddressId()%>">
                                    <i class="bi bi-pencil-square"></i> Update
                                </a>
                            </div>
                        <% } else { %>
                            <div class="address-actions">
                                <a class="btn-update" href="UpdateAddress?id=<%=addr.getAddressId()%>">
                                    <i class="bi bi-pencil-square"></i> Update
                                </a>
                                <button type="button" class="btn-cancel delete-btn"
                                   data-address-id="<%=addr.getAddressId()%>"
                                   data-address-detail="<%=addr.getAddressDetails()%>">
                                    <i class="bi bi-trash"></i> Delete
                                </button>
                                <button type="button" class="set-default-link"
                                    onclick="window.location = 'SetDefaultAddress?id=<%=addr.getAddressId()%>'">
                                    <i class="bi bi-star"></i> Set as default
                                </button>
                            </div>
                        <% } %>
                    </div>
                <% }
                } else { %>
                    <div class="no-address-found" style="margin:30px 0 0 10px;">
                        <i class="bi bi-emoji-frown"></i> No address found. Please add your address!
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
window.onload = function () {
    document.querySelectorAll('.delete-btn').forEach(function(btn) {
        btn.onclick = function(e) {
            e.preventDefault();
            const addressId = btn.getAttribute('data-address-id');
            const detail = btn.getAttribute('data-address-detail');
            Swal.fire({
                title: 'Delete Address?',
                html: `<b>${detail}</b><br>Are you sure to delete this address?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch('DeleteAddress?id=' + addressId, { method: 'POST' })
                    .then(res => {
                        if (res.ok) {
                            btn.closest('.address-row').remove();
                            Swal.fire('Deleted!', 'Address has been deleted.', 'success');
                        } else {
                            Swal.fire('Error', 'Failed to delete address.', 'error');
                        }
                    }).catch(() => {
                        Swal.fire('Error', 'Something went wrong.', 'error');
                    });
                }
            });
        };
    });
};
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
