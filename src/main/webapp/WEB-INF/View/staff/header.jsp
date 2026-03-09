<%@ page import="model.Account" %>
<%@ page import="model.Staff" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    Account acc = (Account) session.getAttribute("user");
    Staff staff = (Staff) session.getAttribute("staff");
    if (acc == null || acc.getRoleID() != 2 || staff == null) {
        response.sendRedirect("LoginStaff");
        return;
    }
%>

<style>
    .header-container {
        background: white;
        border-bottom: 1px solid #e9ecef;
        padding: 16px 24px;
        margin-bottom: 24px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    }

    .header-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
        max-width: 1200px;
        margin: 0 auto;
    }

    .user-info {
        display: flex;
        align-items: center;
        gap: 12px;
    }

    .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: #6c757d;
        color: white;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 16px;
        font-weight: 500;
    }

    .user-details h5 {
        margin: 0;
        font-size: 16px;
        font-weight: 500;
        color: #212529;
    }
.header-container {
    position: fixed;
    top: 0;
    left: 250px;
    width: calc(100% - 250px);
    background: white;
    border-bottom: 1px solid #e9ecef;
    padding: 16px 24px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
    z-index: 1000;
}
main.main-content {
    margin-left: 250px;
    padding: 30px;
    padding-top: 100px; /* tránh b? header che */
    width: calc(100% - 250px);
}

    .user-details small {
        color: #6c757d;
        font-size: 13px;
    }

    .header-actions {
        display: flex;
        align-items: center;
        gap: 12px;
    }

    .notification-btn {
        position: relative;
        background: none;
        border: none;
        color: #6c757d;
        font-size: 18px;
        cursor: pointer;
        padding: 8px;
        border-radius: 6px;
        transition: all 0.2s ease;
        text-decoration: none;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .notification-btn:hover {
        background: #f8f9fa;
        color: #495057;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }

    .notification-badge {
        position: absolute;
        top: 2px;
        right: 2px;
        background: #dc3545;
        color: white;
        border-radius: 50%;
        width: 18px;
        height: 18px;
        font-size: 11px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: 500;
    }

    .logout-btn {
        background: #dc3545;
        color: white;
        border: none;
        padding: 8px 16px;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
        display: flex;
        align-items: center;
        gap: 6px;
        transition: all 0.2s ease;
        box-shadow: 0 2px 4px rgba(220, 53, 69, 0.2);
    }

    .logout-btn:hover {
        background: #c82333;
        box-shadow: 0 4px 8px rgba(220, 53, 69, 0.3);
        transform: translateY(-1px);
    }

    .logout-btn:active {
        transform: translateY(0);
        box-shadow: 0 2px 4px rgba(220, 53, 69, 0.2);
    }

    .logout-btn i {
        font-size: 13px;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .header-content {
            flex-direction: column;
            gap: 16px;
        }

        .user-info {
            order: 1;
        }

        .header-actions {
            order: 2;
        }
    }

    @media (max-width: 480px) {
        .header-container {
            padding: 12px 16px;
        }

        .user-details h5 {
            font-size: 15px;
        }

        .user-details small {
            font-size: 12px;
        }
    }
</style>

<div class="header-container">
    <div class="header-content">
        <div class="user-info">
            <div class="user-avatar">
                ${not empty staff.fullName ? fn:substring(staff.fullName, 0, 1) : 'S'}
            </div>
            <div class="user-details">
                <h5>${staff.fullName}</h5>
                <small>${staff.position}</small>
            </div>
        </div>
        
        <div class="header-actions">           
            <form action="${pageContext.request.contextPath}/Logout" method="get" style="margin: 0;">
                <button type="submit" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i>
                    Logout
                </button>
            </form>
        </div>
    </div>
</div>