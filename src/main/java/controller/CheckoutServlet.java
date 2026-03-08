package controller;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("cus");
        String action = request.getParameter("action");
        if (action == null) {
            action = "checkout";
        }
        if (user == null || customer == null) {
            response.sendRedirect("Login");
            return;
        }
        if (action.equalsIgnoreCase("checkout")) {
            String selectedIdsParam = request.getParameter("selectedCartItemIds");
            List<CartItem> selectedItems = new ArrayList<>();
            if (selectedIdsParam != null && !selectedIdsParam.trim().isEmpty()) {
                String[] idArray = selectedIdsParam.split(",");
                CartDAO cartDAO = new CartDAO();
                for (String idStr : idArray) {
                    try {
                        int cartItemId = Integer.parseInt(idStr.trim());
                        CartItem item = cartDAO.getCartItemById(cartItemId);
                        if (item != null && item.getProduct() != null) {
                            selectedItems.add(item);
                        }
                    } catch (NumberFormatException e) {
                        Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING, "Invalid cartItemId: {0}", idStr);
                    }
                }
            }
            if (selectedItems.isEmpty()) {
                session.setAttribute("message", "No product is chosen to pay.");
                request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/viewCart.jsp").forward(request, response);
                return;
            }
            AddressDAO addressDAO = new AddressDAO();
            List<Address> addresses = addressDAO.getAddressesByCustomerId(customer.getId());
            if (addresses == null || addresses.isEmpty()) {
                session.setAttribute("message", "No address found. Please add a new address.");
                response.sendRedirect("AddAddress");
                return;
            }
            Address selectedAddress = (Address) session.getAttribute("selectedAddress");
            if (selectedAddress == null) {
                selectedAddress = addressDAO.getDefaultAddressByCustomerId(customer.getId());
                if (selectedAddress != null) {
                    session.setAttribute("selectedAddress", selectedAddress);
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Default address found for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), selectedAddress.getAddressDetails()});
                } else {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING,
                            "No default address found for CustomerID: {0}", customer.getId());
                }
            }
            // Calculate total amount and store in session
            long totalAmount = calculateTotalAmount(selectedItems);
            session.setAttribute("totalAmount", totalAmount);
            session.setAttribute("selectedItems", selectedItems);
            session.setAttribute("selectedCartItemIds", selectedIdsParam);
            request.setAttribute("selectedItems", selectedItems);
            request.setAttribute("defaultAddress", selectedAddress);
            request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/checkout.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("voucher")) {
            String voucherCode = request.getParameter("voucherCode");
            String voucherId = request.getParameter("voucherId");
            String selectedIdsParam = request.getParameter("selectedCartItemIds");
            VoucherDAO voucherDAO = new VoucherDAO();
            CustomerVoucherDAO cvDAO = new CustomerVoucherDAO();
            Voucher voucher = null;
            // Clear previous messages
            session.removeAttribute("errorMessage");
            session.removeAttribute("message");
            try {
                // Validate voucher input
                if ((voucherId == null || voucherId.trim().isEmpty()) && (voucherCode == null || voucherCode.trim().isEmpty())) {
                    session.setAttribute("errorMessage", "Voucher code or ID is required.");
                    List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Voucher list size for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), voucherList.size()});
                    request.setAttribute("voucherList", voucherList);
                    request.setAttribute("selectedCartItemIds", selectedIdsParam);
                    request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                    return;
                }
                // Get voucher by ID
                if (voucherId != null && !voucherId.trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(voucherId);
                        voucher = voucherDAO.getVoucherById(id);
                        if (voucher != null && customer != null) {
                            voucher = voucherDAO.getVoucherByCodeForCustomer(voucher.getCode(), customer.getId());
                        }
                    } catch (NumberFormatException e) {
                        Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING, "Invalid voucherId: {0}", voucherId);
                        session.setAttribute("errorMessage", "Invalid voucher ID.");
                        List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                        Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                                "Voucher list size for CustomerID {0}: {1}",
                                new Object[]{customer.getId(), voucherList.size()});
                        request.setAttribute("voucherList", voucherList);
                        request.setAttribute("selectedCartItemIds", selectedIdsParam);
                        request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                        return;
                    }
                }
                // Get voucher by code
                else if (voucherCode != null && !voucherCode.trim().isEmpty()) {
                    if (customer != null) {
                        voucher = voucherDAO.getVoucherByCodeForCustomer(voucherCode, customer.getId());
                    } else {
                        voucher = voucherDAO.getVoucherByCode(voucherCode);
                    }
                }
                // Check if voucher exists
                if (voucher == null) {
                    session.setAttribute("errorMessage", "Voucher does not exist or you do not have permission to use it.");
                    List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Voucher list size for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), voucherList.size()});
                    request.setAttribute("voucherList", voucherList);
                    request.setAttribute("selectedCartItemIds", selectedIdsParam);
                    request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                    return;
                }
                // Validate selected items
                List<CartItem> selectedItems = (List<CartItem>) session.getAttribute("selectedItems");
                if (selectedItems == null || selectedItems.isEmpty()) {
                    session.setAttribute("errorMessage", "No items selected for checkout.");
                    List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Voucher list size for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), voucherList.size()});
                    request.setAttribute("voucherList", voucherList);
                    request.setAttribute("selectedCartItemIds", selectedIdsParam);
                    request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                    return;
                }
                // Calculate total amount
                long totalAmount = calculateTotalAmount(selectedItems);
                BigDecimal discountAmount = BigDecimal.ZERO;
                // Calculate discount
                if (voucher.getDiscountPercent() > 0) {
                    discountAmount = BigDecimal.valueOf(totalAmount)
                            .multiply(BigDecimal.valueOf(voucher.getDiscountPercent()))
                            .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                }
                if (voucher.getMaxDiscountAmount() > 0) {
                    // Cap discount at MaxDiscountAmount
                    if (discountAmount.compareTo(BigDecimal.valueOf(voucher.getMaxDiscountAmount())) > 0) {
                        discountAmount = BigDecimal.valueOf(voucher.getMaxDiscountAmount());
                        // Format the MaxDiscountAmount for display
                        DecimalFormat df = new DecimalFormat("#,###");
                        session.setAttribute("message", "Discount capped at maximum: " + df.format(voucher.getMaxDiscountAmount()) + " VND.");
                    }
                }
                // Validate voucher conditions
                if (!voucher.isActive() || voucher.getExpiryDate().before(new Date())) {
                    session.setAttribute("errorMessage", "Voucher is not active or has expired.");
                    List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Voucher list size for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), voucherList.size()});
                    request.setAttribute("voucherList", voucherList);
                    request.setAttribute("selectedCartItemIds", selectedIdsParam);
                    request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                    return;
                }
                if (totalAmount < voucher.getMinOrderAmount()) {
                    // Format the MinOrderAmount for display
                    DecimalFormat df = new DecimalFormat("#,###");
                    session.setAttribute("errorMessage", "Total order amount does not meet the minimum requirement of " + df.format(voucher.getMinOrderAmount()) + " VND.");
                    List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Voucher list size for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), voucherList.size()});
                    request.setAttribute("voucherList", voucherList);
                    request.setAttribute("selectedCartItemIds", selectedIdsParam);
                    request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                    return;
                }
                if (voucher.getUsedCount() >= voucher.getUsageLimit() && voucher.getUsageLimit() > 0) {
                    session.setAttribute("errorMessage", "Voucher has exceeded its usage limit.");
                    List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                            "Voucher list size for CustomerID {0}: {1}",
                            new Object[]{customer.getId(), voucherList.size()});
                    request.setAttribute("voucherList", voucherList);
                    request.setAttribute("selectedCartItemIds", selectedIdsParam);
                    request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
                    return;
                }
                // Calculate final total amount after discount
                BigDecimal finalTotalAmount = BigDecimal.valueOf(totalAmount).subtract(discountAmount);
                if (finalTotalAmount.compareTo(BigDecimal.ZERO) < 0) {
                    finalTotalAmount = BigDecimal.ZERO;
                }
                // Apply 8% VAT
                BigDecimal vatRate = new BigDecimal("1.08");
                finalTotalAmount = finalTotalAmount.multiply(vatRate).setScale(0, BigDecimal.ROUND_HALF_UP);
                // Format amounts for display
                DecimalFormat df = new DecimalFormat("#,###");
                String formattedDiscount = df.format(discountAmount);
                String formattedFinalTotal = df.format(finalTotalAmount);
      
               
                // Voucher is valid, apply it
                session.setAttribute("appliedVoucher", voucher);
                session.setAttribute("totalAmount", totalAmount);
                session.setAttribute("discountAmount", discountAmount.longValue());
                session.setAttribute("finalTotalAmount", finalTotalAmount.longValue());
                session.setAttribute("message", "Voucher applied successfully!");
                response.sendRedirect("CheckoutServlet?action=checkout&selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
            } catch (Exception e) {
                Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, "Unexpected error applying voucher: {0}", e.getMessage());
                session.setAttribute("errorMessage", "Unexpected error applying voucher: " + e.getMessage());
                List<CustomerVoucher> voucherList = cvDAO.getAllVouchersForCustomer(customer.getId());
                Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                        "Voucher list size for CustomerID {0}: {1}",
                        new Object[]{customer.getId(), voucherList.size()});
                request.setAttribute("voucherList", voucherList);
                request.setAttribute("selectedCartItemIds", selectedIdsParam);
                request.getRequestDispatcher("/WEB-INF/View/customer/cartManagement/voucherOrder.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        Customer customer = (Customer) session.getAttribute("cus");
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();
        VoucherDAO voucherDAO = new VoucherDAO();
        OrderDetailDAO orderDetailsDAO = new OrderDetailDAO();
        PaymentsDAO paymentsDAO = new PaymentsDAO();
        ImportStockDetailDAO importStockDetailDAO = new ImportStockDetailDAO();
        if (user == null || customer == null) {
            response.sendRedirect("Login");
            return;
        }
        String selectedIdsParam = request.getParameter("selectedCartItemIds");
        List<CartItem> selectedItems = new ArrayList<>();
        if (selectedIdsParam != null && !selectedIdsParam.trim().isEmpty()) {
            String[] idArray = selectedIdsParam.split(",");
            for (String idStr : idArray) {
                try {
                    int cartItemId = Integer.parseInt(idStr.trim());
                    CartItem item = cartDAO.getCartItemById(cartItemId);
                    if (item != null && item.getProduct() != null) {
                        selectedItems.add(item);
                    }
                } catch (NumberFormatException e) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING, "Invalid cartItemId: {0}", idStr);
                }
            }
        }
        if (selectedItems.isEmpty()) {
            session.setAttribute("message", "No product is chosen to pay.");
            response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
            return;
        }
        try {
            // Get form data
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String addressIdStr = request.getParameter("addressId");
            String totalAmountStr = request.getParameter("totalAmount");
            String totalPromotionStr = request.getParameter("totalPromotion");
            Voucher appliedVoucher = (Voucher) session.getAttribute("appliedVoucher");
            // Validate input data
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("Full name is required.");
            }
            if (phone == null || phone.trim().isEmpty()) {
                throw new IllegalArgumentException("Phone number is required.");
            }
            if (addressIdStr == null || addressIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Address ID is required.");
            }
            if (totalAmountStr == null || totalAmountStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Total amount is required.");
            }
            // Parse addressId
            int addressId;
            try {
                addressId = Integer.parseInt(addressIdStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Address ID: " + addressIdStr);
            }
            // Get AddressSnapshot from AddressID
            AddressDAO addressDAO = new AddressDAO();
            Address address = addressDAO.getAddressById(addressId);
            if (address == null) {
                throw new IllegalArgumentException("Invalid Address ID: " + addressIdStr);
            }
            String addressSnapshot = address.getProvinceName() + ", "
                    + address.getDistrictName() + ", "
                    + address.getWardName() + ", "
                    + address.getAddressDetails();
            // Parse totalAmount
            long totalAmount;
            try {
                totalAmount = Long.parseLong(totalAmountStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid total amount: " + totalAmountStr);
            }
            // Validate totalAmount
            long calculatedTotal = calculateTotalAmount(selectedItems);
            if (calculatedTotal != totalAmount) {
                session.setAttribute("message",
                        "The total amount does not match the product list. Expected: " + calculatedTotal + ", Received: " + totalAmount);
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Recalculate discount if voucher is applied
            BigDecimal discountAmount = BigDecimal.ZERO;
            if (appliedVoucher != null) {
                if (appliedVoucher.getDiscountPercent() > 0) {
                    discountAmount = BigDecimal.valueOf(totalAmount)
                            .multiply(BigDecimal.valueOf(appliedVoucher.getDiscountPercent()))
                            .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                }
                if (appliedVoucher.getMaxDiscountAmount() > 0) {
                    // Cap discount at MaxDiscountAmount
                    if (discountAmount.compareTo(BigDecimal.valueOf(appliedVoucher.getMaxDiscountAmount())) > 0) {
                        discountAmount = BigDecimal.valueOf(appliedVoucher.getMaxDiscountAmount());
                    }
                }
            }
            // Parse totalPromotion
            long totalPromotion = 0;
            if (totalPromotionStr != null && !totalPromotionStr.trim().isEmpty()) {
                try {
                    totalPromotion = Long.parseLong(totalPromotionStr);
                } catch (NumberFormatException e) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING,
                            "Invalid totalPromotion, defaulting to 0: {0}", totalPromotionStr);
                }
            }
            // Validate totalPromotion against discountAmount
            if (totalPromotion != discountAmount.longValue()) {
                Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING,
                        "Mismatch in totalPromotion: received={0}, expected={1}",
                        new Object[]{totalPromotion, discountAmount.longValue()});
                session.setAttribute("message", "Promotional value does not match the applied voucher.");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Validate totalPromotion
            if (totalPromotion < 0 || totalPromotion > totalAmount) {
                Logger.getLogger(CheckoutServlet.class.getName()).log(Level.WARNING,
                        "Invalid totalPromotion: {0}, totalAmount: {1}",
                        new Object[]{totalPromotion, totalAmount});
                session.setAttribute("message", "Promotional value is invalid.");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Validate CustomerID
            if (customer.getId() <= 0) {
                Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE,
                        "Invalid CustomerID: {0}", customer.getId());
                session.setAttribute("message", "Customer information is invalid.");
                response.sendRedirect("Login");
                return;
            }
            // Calculate final total amount with VAT
            BigDecimal finalTotalAmount = BigDecimal.valueOf(totalAmount).subtract(BigDecimal.valueOf(totalPromotion));
            if (finalTotalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalTotalAmount = BigDecimal.ZERO;
            }
            BigDecimal vatRate = new BigDecimal("1.08");
            finalTotalAmount = finalTotalAmount.multiply(vatRate).setScale(0, BigDecimal.ROUND_HALF_UP);
            // Prepare data for createOrder
            String orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String deliveredDate = null; // New order
            int status = 1; // Processing
            int discount = totalAmount > 0 ? (int) ((totalPromotion * 100) / totalAmount) : 0;
            // Validate order data
            boolean isValidOrder = true;
            StringBuilder validationMessage = new StringBuilder("Order validation errors: ");
            if (customer.getId() <= 0) {
                isValidOrder = false;
                validationMessage.append("CustomerID is invalid; ");
            }
            if (fullName == null || fullName.trim().isEmpty()) {
                isValidOrder = false;
                validationMessage.append("Full name is null or empty; ");
            }
            if (phone == null || phone.trim().isEmpty()) {
                isValidOrder = false;
                validationMessage.append("Phone number is null or empty; ");
            }
            if (addressSnapshot == null || addressSnapshot.trim().isEmpty()) {
                isValidOrder = false;
                validationMessage.append("Address snapshot is null or empty; ");
            }
            if (orderDate == null || orderDate.trim().isEmpty()) {
                isValidOrder = false;
                validationMessage.append("Order date is null or empty; ");
            }
            if (finalTotalAmount.compareTo(BigDecimal.ZERO) < 0) {
                isValidOrder = false;
                validationMessage.append("Total amount is negative; ");
            }
            // Log order data
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.INFO,
                    "Order data: CustomerID={0}, FullName={1}, Phone={2}, AddressID={3}, AddressSnapshot={4}, OrderDate={5}, TotalAmount={6}, Discount={7}, FinalTotalWithVAT={8}",
                    new Object[]{customer.getId(), fullName, phone, addressId, addressSnapshot, orderDate, totalAmount, discount, finalTotalAmount});
            // Return error if data is invalid
            if (!isValidOrder) {
                Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, validationMessage.toString());
                session.setAttribute("message", "Invalid order data: " + validationMessage.toString());
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Create order with AddressID
            int orderID = orderDAO.createOrder(customer.getId(), fullName, addressSnapshot, phone,
                    orderDate, deliveredDate, status, finalTotalAmount.longValue(), discount, addressId);
            if (orderID <= 0) {
                session.setAttribute("message", "Unable to create order! Please check the information.");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Add order details
            boolean orderDetailsSuccess = orderDetailsDAO.addOrderDetails(orderID, selectedItems);
            if (!orderDetailsSuccess) {
                session.setAttribute("message", "Error adding order details!");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Add payment
            boolean paymentSuccess = paymentsDAO.addPayment(orderID, finalTotalAmount.longValue(), "COD", "PENDING");
            if (!paymentSuccess) {
                session.setAttribute("message", "Error adding payment information!");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Delete cart items
            List<Integer> cartItemIds = new ArrayList<>();
            for (CartItem item : selectedItems) {
                cartItemIds.add(item.getCartItemID());
            }
            boolean cartItemsDeleted = cartDAO.deleteMultipleCartItemsByIntegerIds(cartItemIds);
            if (!cartItemsDeleted) {
                session.setAttribute("message", "Error deleting products from cart!");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Update stock
            boolean stockUpdated = importStockDetailDAO.updateStockForOrder(selectedItems);
            if (!stockUpdated) {
                session.setAttribute("message", "Error updating inventory!");
                response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
                return;
            }
            // Update voucher
            if (appliedVoucher != null) {
                voucherDAO.increaseUsedCount(appliedVoucher.getVoucherID());
                if (!appliedVoucher.isIsGlobal()) {
                    CustomerVoucherDAO cvDAO = new CustomerVoucherDAO();
                    cvDAO.decreaseVoucherQuantity(customer.getId(), appliedVoucher.getVoucherID());
                }
                session.removeAttribute("appliedVoucher");
                session.removeAttribute("discountAmount");
                session.removeAttribute("finalTotalAmount");
                session.removeAttribute("totalAmount");
            }
            // Clear session attributes
            session.removeAttribute("selectedItems");
            session.removeAttribute("selectedCartItemIds");
            // Format finalTotalAmount for success message
            DecimalFormat df = new DecimalFormat("#,###");
            String formattedFinalTotal = df.format(finalTotalAmount);
            session.setAttribute("message", "Order placed successfully! Total with VAT: " + formattedFinalTotal + " VND.");
            response.sendRedirect("ViewOrderOfCustomer");
        } catch (NumberFormatException e) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE,
                    "Invalid input data: {0}", e.getMessage());
            session.setAttribute("message", "Invalid input data: " + e.getMessage());
            response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
        } catch (IllegalArgumentException e) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE,
                    "Validation error: {0}", e.getMessage());
            session.setAttribute("message", "Validation error: " + e.getMessage());
            response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
        } catch (Exception e) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE,
                    "Error processing order: {0}", e.getMessage());
            session.setAttribute("message", "Error processing order: " + e.getMessage());
            response.sendRedirect("CheckoutServlet?selectedCartItemIds=" + (selectedIdsParam != null ? selectedIdsParam : ""));
        }
    }

    private long calculateTotalAmount(List<CartItem> cartItems) {
        long totalAmount = 0;
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                Product product = item.getProduct();
                if (product != null) {
                    BigDecimal unitPrice = product.getPrice();
                    BigDecimal discount = BigDecimal.valueOf(product.getDiscount());
                    BigDecimal discountFactor = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                    BigDecimal discountedPrice = unitPrice.multiply(discountFactor);
                    BigDecimal itemTotal = discountedPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                    totalAmount += itemTotal.longValue();
                }
            }
        }
        return totalAmount;
    }
}