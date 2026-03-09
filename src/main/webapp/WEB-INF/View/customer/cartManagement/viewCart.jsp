<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.CartItem"%>
<%@page import="model.Product"%>
<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <title>View Cart - TShop</title>
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
                padding: 20px 15px;
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
                box-shadow: 0 5px 15px rgba(0, 150, 136, 0.15);
            }
            .cart-table tbody td {
                padding: 20px 15px;
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
            .quantity-container {
                display: flex;
                align-items: center;
                gap: 5px;
                justify-content: center;
            }
            .quantity-btn {
                width: 35px;
                height: 35px;
                border-radius: 50%;
                border: 2px solid #009688;
                background: white;
                color: #009688;
                font-size: 1rem;
                font-weight: bold;
                cursor: pointer;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 0;
            }
            .quantity-btn:hover {
                background: #009688;
                color: white;
                transform: scale(1.1);
                box-shadow: 0 5px 15px rgba(0, 150, 136, 0.3);
            }
            .quantity-btn:active {
                transform: scale(0.95);
            }
            .quantity-value {
                width: 60px;
                text-align: center;
                border: 2px solid #b2dfdb;
                border-radius: 10px;
                padding: 8px;
                font-weight: 600;
                color: #004d40;
                background: white;
                transition: all 0.3s ease;
            }
            .quantity-value:focus {
                outline: none;
                border-color: #009688;
                box-shadow: 0 0 0 3px rgba(0, 150, 136, 0.2);
            }
            .quantity-value::-webkit-outer-spin-button,
            .quantity-value::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }
            .quantity-value {
                -moz-appearance: textfield;
            }
            .cart-total {
                font-size: 1.5rem;
                font-weight: 700;
                color: #004d40;
            }
            .action-buttons a {
                display: inline-block;
                margin-right: 5px;
            }
            .price {
                white-space: nowrap;
                font-weight: 600;
                color: #00897b;
                font-size: 1.1rem;
            }
            .table-header-actions {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                padding: 15px 20px;
                background: white;
                border-radius: 15px;
                box-shadow: 0 5px 15px rgba(0, 150, 136, 0.2);
                border: 1px solid #009688;
            }
            .delete-selected-icon, .delete-icon {
                color: #e74c3c;
                font-size: 1.3rem;
                padding: 8px;
                border-radius: 50%;
                transition: all 0.3s ease;
                cursor: pointer;
            }
            .delete-selected-icon:hover, .delete-icon:hover {
                color: #c0392b;
                background: rgba(231, 76, 60, 0.1);
                transform: scale(1.1);
            }
            .card {
                background: white;
                border: none;
                border-radius: 20px;
                box-shadow: 0 15px 35px rgba(0, 150, 136, 0.2);
                backdrop-filter: blur(10px);
                border: 1px solid #009688;
            }
            .btn {
                border-radius: 25px;
                padding: 12px 30px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                transition: all 0.3s ease;
                border: none;
            }
            .btn-success {
                background: linear-gradient(135deg, #00897b 0%, #26a69a 100%);
                box-shadow: 0 10px 30px rgba(0, 137, 123, 0.3);
            }
            .btn-success:hover {
                transform: translateY(-3px);
                box-shadow: 0 15px 40px rgba(0, 137, 123, 0.4);
            }
            .btn-secondary {
                background: linear-gradient(135deg, #b0bec5 0%, #eceff1 100%);
                box-shadow: 0 10px 30px rgba(176, 190, 197, 0.3);
            }
            .btn-secondary:hover {
                transform: translateY(-3px);
                box-shadow: 0 15px 40px rgba(176, 190, 197, 0.4);
            }
            .alert {
                border: none;
                border-radius: 15px;
                padding: 20px;
                margin-bottom: 25px;
                box-shadow: 0 10px 30px rgba(0, 150, 136, 0.15);
                background: linear-gradient(135deg, rgba(0, 150, 136, 0.1) 0%, rgba(38, 166, 154, 0.1) 100%);
                border-left: 5px solid #0097a7;
                color: #004d40;
            }
            input[type="checkbox"] {
                width: 20px;
                height: 20px;
                accent-color: #009688;
                cursor: pointer;
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
                .quantity-btn {
                    width: 30px;
                    height: 30px;
                }
                .quantity-value {
                    width: 50px;
                }
                .product-details {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/View/customer/homePage/header.jsp" />
        <div class="container">
            <h2 class="mb-4">Your Shopping Cart</h2>

            <!-- Display notification -->
            <%
                String message = (String) session.getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-info text-center">

            </div>
            <%
                    session.removeAttribute("message");
                }
            %>

            <!-- Cart Items Table -->
            <%
                List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                if (cartItems != null && !cartItems.isEmpty()) {
            %>
            <form id="deleteForm" action="${pageContext.request.contextPath}/RemoveCartItem" method="post">
                <input type="hidden" name="action" value="deleteMultiple">
                <input type="hidden" name="accountId" value="<%= session.getAttribute("user") != null ? ((Account) session.getAttribute("user")).getAccountID() : 0%>">
                <input type="hidden" name="selectedItems" id="product_id">
                <div class="table-header-actions">
                    <div>
                        <input type="checkbox" id="selectAll" onclick="toggleSelectAll()">
                        <label for="selectAll" class="ms-2 fw-bold">Select All</label>
                    </div>
                    <a href="javascript:void(0);" class="delete-selected-icon" onclick="confirmDeleteMultiple()"><i class="fas fa-trash"></i></a>
                </div>
                <table class="table cart-table">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Product</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (CartItem item : cartItems) {
                                Product product = item.getProduct();
                                if (product == null) {
                                    System.out.println("Sản phẩm null cho cartItemId: " + item.getCartItemID());
                                    continue;
                                }
                                BigDecimal unitPrice = product.getPrice();
                                BigDecimal discount = BigDecimal.valueOf(product.getDiscount());
                                BigDecimal discountFactor = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                                BigDecimal discountedPrice = unitPrice != null ? unitPrice.multiply(discountFactor) : BigDecimal.ZERO;
                                BigDecimal itemTotal = discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                                if (itemTotal == null || discountedPrice == null) {
                                    System.out.println("Tính giá không hợp lệ cho cartItemId: " + item.getCartItemID() + ", discountedPrice: " + discountedPrice + ", quantity: " + item.getQuantity());
                                    itemTotal = BigDecimal.ZERO;
                                }
                        %>
                        <tr data-unit-price="<%= discountedPrice.setScale(0, BigDecimal.ROUND_HALF_UP).toString()%>" data-cart-item-id="<%= item.getCartItemID()%>" data-item-total="<%= itemTotal.setScale(0, BigDecimal.ROUND_HALF_UP).toString()%>">
                            <td><input type="checkbox" class="selectItem" data-item-total="<%= itemTotal.setScale(0, BigDecimal.ROUND_HALF_UP).toString()%>" onclick="updateCartTotal(); saveSelectedItems();"></td>
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
                            <td>
                                <form class="action-buttons" id="quantityForm-<%= item.getCartItemID()%>">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="cartItemId" value="<%= item.getCartItemID()%>">
                                    <input type="hidden" name="accountId" value="<%= session.getAttribute("user") != null ? ((Account) session.getAttribute("user")).getAccountID() : 0%>">
                                    <div class="quantity-container">
                                        <button type="button" class="quantity-btn" onclick="decreaseQuantity(<%= item.getCartItemID()%>)">-</button>
                                        <input type="number" name="quantity" value="<%= item.getQuantity()%>" class="form-control quantity-value" id="quantity-<%= item.getCartItemID()%>" min="1" onchange="submitQuantityForm(<%= item.getCartItemID()%>)"readonly>
                                        <button type="button" class="quantity-btn" onclick="increaseQuantity(<%= item.getCartItemID()%>)">+</button>
                                    </div>
                                </form>
                            </td>
                            <td class="price" id="total-<%= item.getCartItemID()%>"><%= String.format("%,d", itemTotal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue())%> VND</td>
                            <td class="action-buttons">
                                <a href="javascript:void(0);" class="delete-icon" onclick="confirmDeleteCart(<%= item.getCartItemID()%>)"><i class="fas fa-trash"></i></a>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <!-- Cart Summary -->
                <div class="card p-4 mb-4">
                    <div class="d-flex justify-content-between cart-total">
                        <span>Total:</span>
                        <span id="cartTotal">0 VND</span>
                    </div>
                    <div class="text-end mt-4">
                        <form id="checkoutForm" action="${pageContext.request.contextPath}/CheckoutServlet" method="get">
                            <input type="hidden" name="selectedCartItemIds" id="selectedCartItemIds">
                            <button type="submit" class="btn btn-success me-3" onclick="prepareCheckout()">Proceed to Checkout</button>
                            <a href="${pageContext.request.contextPath}/Home" class="btn btn-secondary">Continue Shopping</a>
                        </form>
                    </div>
                </div>
            </form>
            <%
            } else {
            %>
            <div class="alert alert-info text-center">
                <h4>Your cart is empty</h4>
                <p class="mb-3">Looks like you haven't added any items to your cart yet.</p>
                <a href="${pageContext.request.contextPath}/Home" class="btn btn-primary">Start Shopping!</a>
            </div>
            <%
                }
            %>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script>
                                const ACCOUNT_ID = '<%= session.getAttribute("user") != null ? ((Account) session.getAttribute("user")).getAccountID() : 0%>';
                                let updateTimeouts = {};

                                function confirmDeleteCart(cartItemId) {
                                    Swal.fire({
                                        title: 'Are you sure?',
                                        text: "This cart item will be deleted.",
                                        icon: 'warning',
                                        showCancelButton: true,
                                        confirmButtonColor: '#d33',
                                        cancelButtonColor: '#3085d6',
                                        confirmButtonText: 'Delete',
                                        cancelButtonText: 'Cancel'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            window.location.href = '${pageContext.request.contextPath}/RemoveCartItem?action=remove&id=' + cartItemId + '&accountId=' + ACCOUNT_ID;
                                        }
                                    });
                                }

                                function confirmDeleteMultiple() {
                                    const selected = Array.from(document.querySelectorAll('.selectItem:checked')).map(item => item.closest('tr').getAttribute('data-cart-item-id'));
                                    if (selected.length === 0) {
                                        Swal.fire({
                                            icon: 'warning',
                                            title: 'No items selected',
                                            text: 'Please select at least one item to delete.',
                                            showConfirmButton: true
                                        });
                                        return;
                                    }
                                    Swal.fire({
                                        title: 'Are you sure?',
                                        text: `You are about to delete ${selected.length} item(s).`,
                                        icon: 'warning',
                                        showCancelButton: true,
                                        confirmButtonColor: '#d33',
                                        cancelButtonColor: '#3085d6',
                                        confirmButtonText: 'Delete',
                                        cancelButtonText: 'Cancel'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            document.getElementById('product_id').value = selected.join(',');
                                            document.getElementById('deleteForm').submit();
                                        }
                                    });
                                }

                                function formatNumber(number) {
                                    if (isNaN(number) || number === null || number === undefined) {
                                        console.warn("Invalid number to format:", number);
                                        return "0";
                                    }
                                    return Math.round(number).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                                }

                                function updateCartTotal() {
                                    let total = 0;
                                    document.querySelectorAll('.selectItem:checked').forEach(item => {
                                        const itemTotal = parseInt(item.getAttribute('data-item-total') || 0);
                                        total += itemTotal;
                                    });
                                    document.getElementById('cartTotal').textContent = formatNumber(total) + ' VND';
                                }

                                function updateItemTotal(cartItemId) {
                                    const row = document.querySelector(`tr[data-cart-item-id="${cartItemId}"]`);
                                    if (!row) {
                                        console.error("Row not found for cartItemId:", cartItemId);
                                        return;
                                    }
                                    const unitPrice = parseFloat(row.getAttribute('data-unit-price')) || 0;
                                    const quantityInput = document.getElementById(`quantity-${cartItemId}`);
                                    const quantity = parseInt(quantityInput.value) || 0;
                                    const newTotal = unitPrice * quantity;
                                    const totalCell = document.getElementById(`total-${cartItemId}`);
                                    if (totalCell) {
                                        totalCell.textContent = formatNumber(newTotal) + ' VND';
                                    }
                                    row.setAttribute('data-item-total', newTotal.toString());
                                    const checkbox = row.querySelector('.selectItem');
                                    if (checkbox) {
                                        checkbox.setAttribute('data-item-total', newTotal.toString());
                                    }
                                    updateCartTotal();
                                }

                                function saveSelectedItems() {
                                    const selected = Array.from(document.querySelectorAll('.selectItem:checked'))
                                            .map(item => item.closest('tr').getAttribute('data-cart-item-id'));
                                    $.ajax({
                                        url: '${pageContext.request.contextPath}/CartList',
                                        type: 'POST',
                                        data: {
                                            action: 'saveSelectedItems',
                                            selectedCartItemIds: selected.join(',')
                                        },
                                        success: function (response) {
                                            console.log('Selected items saved to session:', selected);
                                        },
                                        error: function (xhr, status, error) {
                                            console.error('Error saving selected items:', error);
                                        }
                                    });
                                }

                                function toggleSelectAll() {
                                    const selectAll = document.getElementById('selectAll');
                                    document.querySelectorAll('.selectItem').forEach(item => {
                                        item.checked = selectAll.checked;
                                    });
                                    updateCartTotal();
                                    saveSelectedItems();
                                }

                                function prepareCheckout() {
                                    const selected = Array.from(document.querySelectorAll('.selectItem:checked'))
                                            .map(item => item.closest('tr').getAttribute('data-cart-item-id'));
                                    if (selected.length === 0) {
                                        Swal.fire({
                                            icon: 'warning',
                                            title: 'No items selected',
                                            text: 'Please select at least one item to proceed to checkout.',
                                            showConfirmButton: true
                                        });
                                        event.preventDefault();
                                        return false;
                                    }
                                    document.getElementById('selectedCartItemIds').value = selected.join(',');
                                    return true;
                                }
                                function increaseQuantity(cartItemId) {


                                    const quantityInput = document.getElementById(`quantity-${cartItemId}`);
                                    if (!quantityInput) {

                                        console.error('Quantity input not found for cartItemId:', cartItemId);
                                        return;
                                    }
                                    let currentQuantity = parseInt(quantityInput.value) || 1;
                                    if (currentQuantity < 1000) {
                                        currentQuantity++;
                                        quantityInput.value = currentQuantity;

                                        console.log('Quantity increased to:', currentQuantity);
                                        submitQuantityForm(cartItemId);
                                    } else {

                                        console.log('Maximum quantity reached:', currentQuantity);
                                    }
                                }

                                function decreaseQuantity(cartItemId) {


                                    const quantityInput = document.getElementById(`quantity-${cartItemId}`);
                                    if (!quantityInput) {

                                        console.error('Quantity input not found for cartItemId:', cartItemId);
                                        return;
                                    }
                                    let currentQuantity = parseInt(quantityInput.value) || 1;
                                    if (currentQuantity > 1) {
                                        currentQuantity--;
                                        quantityInput.value = currentQuantity;

                                        console.log('Quantity decreased to:', currentQuantity);
                                        submitQuantityForm(cartItemId);
                                    } else {
                                        a
                                        console.log('Minimum quantity reached:', currentQuantity);
                                    }
                                }

                                function submitQuantityForm(cartItemId) {

                                    console.log('Submit triggered for cartItemId:', cartItemId);
                                    const quantityInput = document.getElementById(`quantity-${cartItemId}`);
                                    const form = document.getElementById(`quantityForm-${cartItemId}`);
                                    if (!quantityInput || !form) {

                                        console.error('Form or input not found for cartItemId:', cartItemId);
                                        return;
                                    }
                                    let currentQuantity = parseInt(quantityInput.value) || 1;
                                    if (currentQuantity < 1) {
                                        quantityInput.value = 1;
                                        currentQuantity = 1;

                                        console.log('Quantity set to minimum:', currentQuantity);
                                    }
                                    const formData = new FormData(form);
                                    // Debug dữ liệu gửi đi
                                    for (let pair of formData.entries()) {
                                        console.log(pair[0] + ': ' + pair[1]);
                                    }
                                    $.ajax({
                                        url: '${pageContext.request.contextPath}/UpdateCart?t=' + new Date().getTime(),
                                        type: 'POST',
                                        data: formData,
                                        processData: false,
                                        contentType: false,
                                        success: function (response) {
                                            console.log('Update response:', response);
                                            if (response.trim() === "success") {

                                                console.log('Quantity updated successfully:', cartItemId);
                                                updateItemTotal(cartItemId);
                                            } else {
                                                console.error('Update failed:', response);

                                                quantityInput.value = parseInt(quantityInput.getAttribute('data-previous-value')) || 1;
                                                updateItemTotal(cartItemId);
                                            }
                                        },
                                        error: function (xhr, status, error) {
                                            console.error('AJAX error:', error, xhr.status, xhr.responseText);

                                            quantityInput.value = parseInt(quantityInput.getAttribute('data-previous-value')) || 1;
                                            updateItemTotal(cartItemId);
                                        },
                                        beforeSend: function () {
                                            quantityInput.setAttribute('data-previous-value', quantityInput.value);
                                        }
                                    });
                                }
                                const lastAddedCartItemId = '<%= session.getAttribute("lastAddedCartItemId") != null ? session.getAttribute("lastAddedCartItemId") : "null"%>';
                                if (lastAddedCartItemId !== "null") {
                                    const checkbox = document.querySelector(`tr[data-cart-item-id="${lastAddedCartItemId}"] .selectItem`);
                                    if (checkbox) {
                                        checkbox.checked = true;
                                        updateCartTotal(); // Cập nhật tổng khi check
                                        saveSelectedItems(); // Lưu trạng thái chọn
                                    }
                                    // Xóa session attribute sau khi sử dụng
                                    fetch('${pageContext.request.contextPath}/ClearLastAddedCartItem', {
                                        method: 'POST'
                                    }).then(() => console.log('Cleared lastAddedCartItemId from session'));
                                }

            </script>
        </div>
        <jsp:include page="/WEB-INF/View/customer/homePage/footer.jsp" />
    </body>
</html>