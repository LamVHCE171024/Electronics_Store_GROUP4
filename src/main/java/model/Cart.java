package model;

import java.time.LocalDateTime;
import java.util.List;

public class Cart {
    private int cartId;
    private int accountId;
    private LocalDateTime createdAt;
    private List<CartItem> cartItems;

    // Constructor
    public Cart() {
    }

    public Cart(int cartId, int accountId, LocalDateTime createdAt, List<CartItem> cartItems) {
        this.cartId = cartId;
        this.accountId = accountId;
        this.createdAt = createdAt;
        this.cartItems = cartItems;
    }

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}