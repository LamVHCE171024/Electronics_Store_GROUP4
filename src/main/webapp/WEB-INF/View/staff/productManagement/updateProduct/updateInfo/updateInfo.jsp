<body>
    <div class="sidebar-wrapper">
        <jsp:include page="/WEB-INF/View/staff/sideBar.jsp" />
    </div>

    <div class="main-content">
        <jsp:include page="/WEB-INF/View/staff/productManagement/updateProduct/updateInfo/productTitle.jsp" />
        <jsp:include page="/WEB-INF/View/staff/productManagement/updateProduct/updateInfo/update.jsp" />
    </div>

    <style>
        .sidebar-wrapper {
            width: 220px;
            position: fixed;
            top: 0;
            left: 0;
            height: 100vh;
            background-color: #232946;
            z-index: 10;
        }

        .main-content {
            margin-left: 220px; /* Phù h?p v?i chi?u r?ng sidebar */
            padding: 30px;
            min-height: 100vh;
            background-color: #f8f9fa;
            overflow-x: hidden;
        }

    </style>
</body>
