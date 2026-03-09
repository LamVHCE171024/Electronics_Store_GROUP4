package model;

import java.math.BigDecimal;

public class ProductVariant {

    private int variantId;
    private int productId;
    private String color;
    private String storage;
    private int quantity;
    private BigDecimal price;
    private int discount;
    private String sku;
    private String imageUrl;
    private boolean isActive;

    public ProductVariant() {
    }

    public ProductVariant(int variantId, int productId, String color, String storage, int quantity,
            BigDecimal price, int discount, String sku, String imageUrl, boolean isActive) {
        this.variantId = variantId;
        this.productId = productId;
        this.color = color;
        this.storage = storage;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    public ProductVariant(int variantId, int productId, String color, String storage, int quantity,
            BigDecimal price, int discount, String imageUrl, boolean isActive) {
        this.variantId = variantId;
        this.productId = productId;
        this.color = color;
        this.storage = storage;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.sku = null; // Giá trị mặc định cho sku
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    // Getters và Setters
    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
