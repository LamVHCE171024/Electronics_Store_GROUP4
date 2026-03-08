<%@page import="java.util.List"%>
<%@page import="model.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <title>Address List - TShop</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                background: linear-gradient(135deg, #b2dfdb 0%, #80cbc4 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
            }
            .container {
                margin: 30px auto;
                max-width: 800px;
                background: rgba(255, 255, 255, 0.95);
                backdrop-filter: blur(12px);
                border-radius: 20px;
                padding: 30px;
                box-shadow: 0 20px 40px rgba(0, 105, 92, 0.25);
                border: 2px solid #00695c;
            }
            h2 {
                color: #004d40;
                font-weight: 700;
                margin-bottom: 30px;
                text-align: center;
                position: relative;
            }
            h2::after {
                content: '';
                position: absolute;
                bottom: -10px;
                left: 50%;
                transform: translateX(-50%);
                width: 100px;
                height: 4px;
                background: linear-gradient(90deg, #009688, #26a69a);
                border-radius: 2px;
            }
            .address-card {
                background: white;
                border-radius: 15px;
                padding: 20px;
                margin-bottom: 20px;
                box-shadow: 0 10px 30px rgba(0, 150, 136, 0.2);
                border: 1px solid #009688;
                transition: all 0.3s ease;
            }
            .address-card:hover {
                transform: translateY(-3px);
                box-shadow: 0 15px 40px rgba(0, 150, 136, 0.25);
            }
            .btn-select, .btn-add {
                background: linear-gradient(135deg, #00897b 0%, #26a69a 100%);
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 25px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                transition: all 0.3s ease;
                box-shadow: 0 10px 30px rgba(0, 137, 123, 0.3);
            }
            .btn-select:hover, .btn-add:hover {
                transform: translateY(-2px);
                box-shadow: 0 15px 40px rgba(0, 137, 123, 0.4);
            }
            .alert {
                border: none;
                border-radius: 15px;
                padding: 20px;
                margin-bottom: 25px;
                box-shadow: 0 10px 30px rgba(0, 150, 136, 0.15);
            }
            .alert-info {
                background: linear-gradient(135deg, rgba(0, 150, 136, 0.1) 0%, rgba(38, 166, 154, 0.1) 100%);
                border-left: 5px solid #0097a7;
                color: #004d40;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="container">
            <h2 class="mb-4">Select Address</h2>

            <!-- Display notification -->
            <%
                String message = (String) session.getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-info text-center">
                <%= message%>
            </div>
            <%
                    session.removeAttribute("message");
                }
            %>

            <%
                List<Address> addresses = (List<Address>) request.getAttribute("addresses");
                if (addresses != null && !addresses.isEmpty()) {
            %>
            <div class="mb-3">
                
            </div>
            <%
                for (Address address : addresses) {
                    String addressStr = address.getProvinceName() + ", "
                            + address.getDistrictName() + ", "
                            + address.getWardName() + ", "
                            + address.getAddressDetails();
            %>
            <div class="address-card">
                <p><strong>Address:</strong> <%= addressStr%></p>
                <p><strong>Default:</strong> <%= address.isDefault() ? "Yes" : "No"%></p>
                <form action="${pageContext.request.contextPath}/AddressListServlet" method="post">
                    <input type="hidden" name="addressId" value="<%= address.getAddressId()%>">
                    <input type="hidden" name="fromCheckout" value="<%= request.getAttribute("fromCheckout")%>">
                    <input type="hidden" name="selectedCartItemIds" value="<%= request.getAttribute("selectedCartItemIds") != null ? request.getAttribute("selectedCartItemIds") : ""%>">
                    <button type="submit" class="btn-select">Select This Address</button>
                </form>
            </div>
            <%
                }
            } else {
            %>
            <div class="alert alert-info text-center">
                <p>You have no addresses. Please add a new address to proceed.</p>
                <a href="${pageContext.request.contextPath}/AddAddress?fromCheckout=<%= request.getAttribute("fromCheckout")%>&selectedCartItemIds=<%= request.getAttribute("selectedCartItemIds") != null ? request.getAttribute("selectedCartItemIds") : ""%>" class="btn-add">Add New Address</a>
            </div>
            <%
                }
            %>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>