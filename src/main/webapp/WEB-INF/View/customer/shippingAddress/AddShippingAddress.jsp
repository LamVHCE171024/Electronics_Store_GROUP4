<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.Customer"%>
<%
    Customer user = (Customer) session.getAttribute("customer");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Add Shipping Address</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/profile.css">
</head>
<body>
<jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />

<div class="main-account container-fluid">
    <jsp:include page="/WEB-INF/View/customer/sideBar.jsp" />

    <div class="profile-card" style="margin-bottom:20px">
        <div class="profile-header">
            <h4>
                <i class="bi bi-plus-lg me-2"></i>
                Add New Address
            </h4>
        </div>
        <div class="profile-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form method="post" action="AddAddress" id="formAddress">
                <div class="form-group">
                    <label for="province" class="form-label">Province</label>
                    <select name="province" id="province" class="form-control" required>
                        <option value="" selected>Select Province</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="district" class="form-label">District</label>
                    <select name="district" id="district" class="form-control" required>
                        <option value="" selected>Select District</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="ward" class="form-label">Ward</label>
                    <select name="ward" id="ward" class="form-control" required>
                        <option value="" selected>Select Ward</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="addressDetails" class="form-label">Detailed Address</label>
                    <input type="text" name="addressDetails" id="addressDetails" class="form-control" required minlength="5">
                    <small id="error-message" class="form-text text-danger"></small>
                </div>
                <c:choose>
                    <c:when test="${not empty hasDefault && hasDefault}">
                        <div class="form-group d-flex align-items-center" style="gap:10px;">
                            <input class="form-check-input" name="isDefault" type="checkbox" role="switch" id="isDefaultSwitch">
                            <label class="form-check-label" for="isDefaultSwitch" style="margin-bottom:0;">Set as default</label>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="isDefault" value="1">
                        <div class="form-group d-flex align-items-center" style="gap:10px;">
                            <input class="form-check-input" type="checkbox" id="isDefaultSwitch" checked disabled>
                            <label class="form-check-label" for="isDefaultSwitch" style="margin-bottom:0;">Set as default (auto for first address)</label>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="profile-actions" style="justify-content: flex-end;">
                    <a href="ViewShippingAddress" class="btn-cancel me-2">Cancel</a>
                    <button type="submit" class="btn-update">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
<script>
    document.getElementById('formAddress').addEventListener('submit', function (event) {
        const addressInput = document.getElementById('addressDetails');
        const errorMessage = document.getElementById('error-message');
        errorMessage.textContent = '';
        if (addressInput.value.trim().length < 5) {
            errorMessage.textContent = 'Detailed Address must be at least 5 characters.';
            addressInput.focus();
            event.preventDefault();
            setTimeout(() => { errorMessage.textContent = ''; }, 3000);
        }
    });

    let allData = [];
    let provinceSel = document.getElementById("province");
    let districtSel = document.getElementById("district");
    let wardSel = document.getElementById("ward");

    axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
        .then(function (result) {
            allData = result.data;
            renderProvinces();
        });

    function renderProvinces() {
        allData.forEach(function (p) {
            provinceSel.options[provinceSel.options.length] = new Option(p.Name, p.Name);
        });
    }
    provinceSel.onchange = function () {
        districtSel.length = 1;
        wardSel.length = 1;
        if (this.value) {
            let obj = allData.find(x => x.Name === this.value);
            obj.Districts.forEach(function (d) {
                districtSel.options[districtSel.options.length] = new Option(d.Name, d.Name);
            });
        }
    };
    districtSel.onchange = function () {
        wardSel.length = 1;
        let p = allData.find(x => x.Name === provinceSel.value);
        if (p && this.value) {
            let d = p.Districts.find(x => x.Name === this.value);
            d.Wards.forEach(function (w) {
                wardSel.options[wardSel.options.length] = new Option(w.Name, w.Name);
            });
        }
    };
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
