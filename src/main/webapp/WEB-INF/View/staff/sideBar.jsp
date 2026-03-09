<head>
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        
            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                width: 240px;
                background: #1e40af;
                z-index: 1000;
                box-shadow: 2px 0 10px rgba(0,0,0,0.1);
            }
            .sidebar-header {
                padding: 20px;
                text-align: center;
                border-bottom: 1px solid rgba(255,255,255,0.1);
            }
            .sidebar-header h4 {
                color: white;
                margin: 0;
                font-weight: 600;
            }
            .sidebar-menu {
                padding: 20px 0;
            }
            .sidebar-menu a {
                display: block;
                padding: 12px 20px;
                color: rgba(255,255,255,0.8);
                text-decoration: none;
                border-left: 3px solid transparent;
            }
            .sidebar-menu a:hover, .sidebar-menu a.active {
                background: rgba(255,255,255,0.1);
                color: white;
                border-left-color: #fff;
            }
            .sidebar-menu i {
                width: 20px;
                margin-right: 10px;
            }
    </style>
</head>

<!-- Sidebar HTML Structure -->
<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h4>

            TShop
        </h4>
    </div>

   <div class="sidebar-menu">
    <a href="StaffDashboard" class="sidebar-link">
        <i class="fas fa-tachometer-alt"></i> Dashboard
    </a>
    <a href="ViewOrderList" class="sidebar-link">
        <i class="fas fa-shopping-cart"></i> Orders
    </a>
    
    <a href="ImportStatistic" class="sidebar-link">
        <i class="fas fa-warehouse"></i> Stock
    </a>
    <a href="CustomerList" class="sidebar-link">
        <i class="fas fa-users"></i> Customers
    </a>
    <a href="ViewListNewFeedback" class="sidebar-link">
        <i class="fas fa-comments"></i> Feedback
    </a>
</div>

</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {

        const current = window.location.pathname.toLowerCase();
        const sidebarLinks = document.querySelectorAll('.sidebar-link');
        let hasActive = false;

        sidebarLinks.forEach(link => {
            const href = link.getAttribute('href').toLowerCase();
            if (current.includes(href) && href !== "/") {
                link.classList.add('active');
                hasActive = true;
            } else {
                link.classList.remove('active');
            }
        });

        if (!hasActive) {
            sidebarLinks[0].classList.add('active');
        }
    });
</script>
