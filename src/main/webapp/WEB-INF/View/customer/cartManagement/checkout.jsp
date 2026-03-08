<%@page import="model.Voucher"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="model.CartItem"%>
<%@page import="model.Product"%>
<%@page import="model.Account"%>
<%@page import="model.Address"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String selectedCartItemIds = (String) session.getAttribute("selectedCartItemIds");
    // Nếu không có trong session, thử lấy từ request parameter
    if (selectedCartItemIds == null || selectedCartItemIds.trim().isEmpty()) {
        selectedCartItemIds = request.getParameter("selectedCartItemIds");
    }
    Address defaultAddress = (Address) request.getAttribute("defaultAddress");
    Long totalAmount = (Long) session.getAttribute("totalAmount");
    Long discountAmount = (Long) session.getAttribute("discountAmount");
    Long finalTotalAmount = (Long) session.getAttribute("finalTotalAmount");
    if (totalAmount == null) {
        totalAmount = 0L;
    }
    if (discountAmount == null) {
        discountAmount = 0L;
    }
    if (finalTotalAmount == null) {
        finalTotalAmount = totalAmount;
    }
%>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <title>Checkout - TShop</title>
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
                max-width: 1200px;
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
            h4 {
                color: #004d40;
                font-weight: 600;
                margin-bottom: 20px;
            }
            .product-section, .orderer-section, .address-section, .info-section {
                margin-bottom: 20px;
                padding: 20px;
                background: white;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 150, 136, 0.2);
                border: 1px solid #009688;
            }
            .cart-table {
                background: white;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 10px 30px rgba(0, 150, 136, 0.2);
                border: 1px solid #009688;
            }
            .cart-table thead {
                background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
                color: white;
            }
            .cart-table thead th {
                border: none;
                padding: 15px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                font-size: 0.9rem;
            }
            .cart-table tbody tr {
                transition: all 0.3s ease;
                border-bottom: 1px solid #b2dfdb;
            }
            .cart-table tbody tr:hover {
                background: linear-gradient(135deg, rgba(0, 150, 136, 0.05) 0%, rgba(77, 182, 172, 0.05) 100%);
                transform: translateY(-2px);
            }
            .cart-table tbody td {
                padding: 15px;
                vertical-align: middle;
                border: none;
            }
            .cart-table img {
                width: 80px;
                height: 80px;
                object-fit: contain;
                border-radius: 10px;
                transition: transform 0.3s ease;
                background: #f5f5f5;
                padding: 5px;
            }
            .cart-table img:hover {
                transform: scale(1.1);
            }
            .product-details {
                display: flex;
                align-items: center;
                gap: 15px;
            }
            .price {
                white-space: nowrap;
                font-weight: 600;
                color: #00897b;
                font-size: 1.1rem;
            }
            .product-link {
                text-decoration: none;
                color: inherit;
                display: block;
                transition: all 0.3s ease;
            }
            .product-name {
                font-weight: 600;
                color: #004d40;
                transition: color 0.3s ease;
            }
            .product-link:hover .product-name {
                color: #009688;
            }
            .btn-order, .btn-address {
                background: linear-gradient(135deg, #00897b 0%, #26a69a 100%);
                color: white;
                padding: 12px 30px;
                border: none;
                border-radius: 25px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                transition: all 0.3s ease;
                box-shadow: 0 10px 30px rgba(0, 137, 123, 0.3);
            }
            .btn-address {
                background: linear-gradient(135deg, #009688 0%, #4db6ac 100%);
            }
            .btn-order:hover, .btn-address:hover {
                transform: translateY(-3px);
                box-shadow: 0 15px 40px rgba(0, 137, 123, 0.4);
            }
            .form-control {
                border-radius: 10px;
                border: 2px solid #b2dfdb;
                padding: 10px;
                transition: all 0.3s ease;
            }
            .form-control:focus {
                border-color: #009688;
                box-shadow: 0 0 0 3px rgba(0, 150, 136, 0.2);
                outline: none;
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
            @media (max-width: 768px) {
                .container {
                    margin: 15px;
                    padding: 20px;
                }
                .cart-table {
                    font-size: 0.9rem;
                }
                .cart-table img {
                    width: 60px;
                    height: 60px;
                }
                .product-details {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }
            }
            .error-message {
                color: #dc3545;
                font-size: 0.9rem;
                margin-top: 5px;
                display: none;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="container">
            <h2 class="mb-4">Checkout</h2>
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
            <form action="${pageContext.request.contextPath}/VoucherOrder" class="mb-3 d-flex">
                <input type="hidden" name="selectedCartItemIds" value="<%= selectedCartItemIds != null ? selectedCartItemIds : request.getParameter("selectedCartItemIds") != null ? request.getParameter("selectedCartItemIds") : ""%>">
                <button type="submit" class="btn btn-outline-primary">Apply Voucher</button>
            </form>
            <form action="${pageContext.request.contextPath}/CheckoutServlet" method="post">
                <input type="hidden" name="selectedCartItemIds" value="<%= selectedCartItemIds != null ? selectedCartItemIds : request.getParameter("selectedCartItemIds") != null ? request.getParameter("selectedCartItemIds") : ""%>">
                <!-- Product Section -->
                <div class="product-section">
                    <h4>Product</h4>
                    <%
                        List<CartItem> cartItems = (List<CartItem>) request.getAttribute("selectedItems");
                        if (cartItems != null && !cartItems.isEmpty()) {
                    %>
                    <table class="table cart-table">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (CartItem item : cartItems) {
                                    Product product = item.getProduct();
                                    if (product != null) {
                                        BigDecimal unitPrice = product.getPrice();
                                        BigDecimal discount = BigDecimal.valueOf(product.getDiscount());
                                        BigDecimal discountFactor = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                                        BigDecimal discountedPrice = unitPrice.multiply(discountFactor);
                                        BigDecimal itemTotal = discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                            %>
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/ProductDetail?productId=<%= product.getProductId()%>&categoryId=<%= product.getCategoryId()%>" class="product-link">
                                        <div class="product-details">
                                            <img src="<%= product.getImageUrl() != null ? product.getImageUrl() : "https://via.placeholder.com/80"%>" alt="<%= product.getProductName()%>">
                                            <div class="product-name"><%= product.getProductName()%></div>
                                        </div>
                                    </a>
                                </td>
                                <td class="price">
                                    <%= String.format("%,d", discountedPrice.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())%> VND
                                    <% if (discount.compareTo(BigDecimal.ZERO) > 0) {%>
                                    <small class="text-muted"><del><%= String.format("%,d", unitPrice.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())%> VND</del></small>
                                    <% }%>
                                </td>
                                <td><%= item.getQuantity()%></td>
                                <td class="price"><%= String.format("%,d", itemTotal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())%> VND</td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                    <%
                    } else {
                    %>
                    <div class="alert alert-info text-center">
                        <h4>Your cart is empty</h4>
                        <p class="mb-3">Looks like you haven't added any items to your cart yet.</p>
                        <a href="${pageContext.request.contextPath}/Home" class="btn btn-order">Start Shopping!</a>
                    </div>
                    <%
                        }
                    %>
                    <input type="hidden" name="totalAmount" value="<%= totalAmount%>">
                </div>
                <!-- Orderer Section -->
                <div class="orderer-section">
                    <h4>Orderer</h4>
                    <div class="mb-3">
                        <input type="text" name="fullName" id="fullName" class="form-control" placeholder="Full name" required>
                        <div id="fullNameError" class="error-message"></div>
                    </div>
                    <div class="mb-3">
                        <input type="text" name="phone" id="phone" class="form-control" placeholder="Phone number" required>
                        <div id="phoneError" class="error-message"></div>
                    </div>
                </div>
                <!-- Address Section -->
                <div class="address-section">
                    <h4>Address</h4>
                    <%
                        if (defaultAddress != null) {
                            String addressStr = defaultAddress.getProvinceName() + ", "
                                    + defaultAddress.getDistrictName() + ", "
                                    + defaultAddress.getWardName() + ", "
                                    + defaultAddress.getAddressDetails();
                    %>
                    <div class="mb-3">
                        <p><strong>Selected Address:</strong> <%= addressStr%></p>
                        <input type="hidden" name="addressId" value="<%= defaultAddress.getAddressId()%>">
                    </div>
                    <%
                    } else {
                    %>
                    <div class="alert alert-info text-center">
                        <p>No address selected. Please create or select an address to proceed with checkout.</p>
                        <a href="${pageContext.request.contextPath}/AddAddress?fromCheckout=true&selectedCartItemIds=<%= selectedCartItemIds != null ? selectedCartItemIds : request.getParameter("selectedCartItemIds") != null ? request.getParameter("selectedCartItemIds") : ""%>" class="btn btn-address">Create New Address</a>
                    </div>
                    <%
                        }
                    %>
                    <div class="mb-3">
                        <a href="${pageContext.request.contextPath}/AddressListServlet?fromCheckout=true&selectedCartItemIds=<%= selectedCartItemIds != null ? selectedCartItemIds : request.getParameter("selectedCartItemIds") != null ? request.getParameter("selectedCartItemIds") : ""%>" class="btn-address">Select Address</a>
                    </div>
                </div>
                <!-- Information Order Section -->
                <div class="info-section">
                    <h4>Order Summary</h4>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Total amount:</span>
                        <span class="price"><%= String.format("%,d", totalAmount)%> VND</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Total discount:</span>
                        <span class="price"><%= String.format("%,d", discountAmount)%> VND</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>Total after discount:</span>
                        <span class="price"><%= String.format("%,d", totalAmount - discountAmount)%> VND</span>
                    </div>
                    <div class="d-flex justify-content-between mb-2">
                        <span>VAT (8%):</span>
                        <span class="price"><%= String.format("%,d", (finalTotalAmount - (totalAmount - discountAmount)))%> VND</span>
                    </div>
                    <div class="d-flex justify-content-between mt-3">
                        <strong>Payment required:</strong>
                        <strong class="price"><%= String.format("%,d", finalTotalAmount)%> VND</strong>
                    </div>
                    <p><small>*Total includes 8% VAT</small></p>
                    <input type="hidden" name="totalAmount" value="<%= totalAmount%>">
                    <input type="hidden" name="totalPromotion" value="<%= discountAmount%>">
                    <input type="hidden" name="selectedCartItemIds" value="<%= selectedCartItemIds != null ? selectedCartItemIds : request.getParameter("selectedCartItemIds") != null ? request.getParameter("selectedCartItemIds") : ""%>">
                    <button type="submit" id="submitBtn" class="btn-order mt-3" disabled>Place Order</button>
                </div>
            </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
        <script>
            // Validation for Full Name
            const fullNameInput = document.getElementById("fullName");
            const fullNameError = document.getElementById("fullNameError");
            const submitBtn = document.getElementById("submitBtn");
            fullNameInput.addEventListener("blur", function () {
                let name = fullNameInput.value.trim();
                fullNameError.style.display = "none";
                submitBtn.disabled = false;
                // Replace multiple spaces with single space
                name = name.replace(/\s+/g, " ");
                fullNameInput.value = name;
                // Regex: Each word starts with uppercase, no numbers or special characters
                const namePattern = /^([A-ZÀ-Ỹ][a-zà-ỹ]+)(\s[A-ZÀ-Ỹ][a-zà-ỹ]+)*$/u;
                if (!namePattern.test(name) || name === "") {
                    fullNameError.textContent = "Full name must start with uppercase letters, contain no numbers or special characters, and have no extra spaces.";
                    fullNameError.style.display = "block";
                    submitBtn.disabled = true;
                }
            });
            fullNameInput.addEventListener("input", function () {
                fullNameError.style.display = "none";
                submitBtn.disabled = false;
            });
            // Validation for Phone Number
            const phoneInput = document.getElementById("phone");
            const phoneError = document.getElementById("phoneError");
            phoneInput.addEventListener("blur", function () {
                const phone = phoneInput.value.trim();
                phoneError.style.display = "none";
                submitBtn.disabled = false;
                // Regex: Must start with 0 and have exactly 10 digits
                const phonePattern = /^0\d{9}$/;
                if (!phonePattern.test(phone) || phone === "") {
                    phoneError.textContent = "Phone number must start with 0 and have exactly 10 digits.";
                    phoneError.style.display = "block";
                    submitBtn.disabled = true;
                }
            });
            phoneInput.addEventListener("input", function () {
                phoneError.style.display = "none";
                submitBtn.disabled = false;
            });
        </script>
    </body>
</html>