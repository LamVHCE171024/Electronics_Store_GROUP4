package model;

import java.util.Date;

public class CartItem {
    private int cartItemID;
    private int cartID;
    private int productID;
    private Integer variantID;
    private int quantity;
    private Date addedAt;
    private Product product; // Thêm thuộc tính Product
    private ProductVariant variant; // Thêm thuộc tính ProductVariant

    // Default constructor
    public CartItem() {
    }

    // Full-argument constructor
    public CartItem(int cartItemID, int cartID, int productID, Integer variantID, int quantity, 
                    Date addedAt, Product product, ProductVariant variant) {
        this.cartItemID = cartItemID;
        this.cartID = cartID;
        this.productID = productID;
        this.variantID = variantID;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.product = product;
        this.variant = variant;
    }

    // Getters and Setters
    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Integer getVariantID() {
        return variantID;
    }

    public void setVariantID(Integer variantID) {
        this.variantID = variantID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }
}