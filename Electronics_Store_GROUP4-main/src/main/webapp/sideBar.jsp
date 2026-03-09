<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Supplier Detail</title>
    <!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/supplierLists.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />

    <style> .sidebar {
    width: 220px;
    background: #232946;
    color: #fff;
    min-height: 100vh;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 10;
}
.sidebar-header {
    padding: 20px;
    font-size: 1.3rem;
    font-weight: 700;
    letter-spacing: 1px;
    border-bottom: 1px solid #2e3652;
    display: flex;
    align-items: center;
    gap: 10px;
}
.nav-menu {
    list-style: none;
    padding: 0;
    margin: 0;
}
.nav-item {
    margin: 0;
}
.nav-link {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 14px 25px;
    color: #ffffff;
    text-decoration: none;
    font-size: 1rem;
    border-left: 4px solid transparent;
    transition: background .2s, border-left .2s, color .2s;
}
.nav-link:hover,
.nav-link.active {
    background: #35377e;
    color: #fff;
    border-left: 4px solid #eebbc3;
}
</style>
</head>
 




<div class="sidebar-overlay" id="sidebarOverlay"></div>
<nav class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h2><i class="fas fa-store"></i> <span>TShop</span></h2>
    </div>
    <ul class="nav-menu">
        <li class="nav-item">
            <a href="AdminDashboard" class="nav-link">
                <i class="fas fa-tachometer-alt"></i> <span>Dashboard</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="StaffList" class="nav-link">
                <i class="fas fa-user-tie"></i> <span>Staff Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="AdminProduct" class="nav-link">
                <i class="fas fa-box"></i> <span>Product Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="CategoryView" class="nav-link">
                <i class="fas fa-tags"></i> <span>Category Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="Voucher" class="nav-link">
                <i class="fas fa-ticket-alt"></i> <span>Voucher Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="ViewSupplier" class="nav-link">
                <i class="fas fa-truck"></i> <span>Supplier Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="ManageStatistic" class="nav-link">
                <i class="fa fa-bar-chart"></i> <span>Manage Statistics</span>
            </a>
        </li>
    </ul>
</nav>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const path = window.location.pathname.toLowerCase();
        const navLinks = document.querySelectorAll(".nav-link");

        navLinks.forEach(link => {
            const href = link.getAttribute("href").toLowerCase();
            if (path.includes(href)) {
                document.querySelectorAll(".nav-link").forEach(l => l.classList.remove("active"));
                link.classList.add("active");
            }
        });
    });
</script>
