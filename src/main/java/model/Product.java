/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author HP - Gia Khiêm
 */
public class Product {

    private int productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int discount;
    private int supplierId;
    private String supplierName;
    private int categoryId;
    private String categoryName;
    private int brandId;
    private String brandName;
    private boolean isFeatured;
    private boolean isBestSeller;
    private boolean isNew;
    private int warrantyPeriod;
    private boolean isActive;
    private String imageUrl;
    private BigDecimal ImportPrice;

    
    public Product(int productId, String productName, String description, BigDecimal price, int discount, int supplierId, int categoryId, int brandId, boolean isFeatured, boolean isBestSeller, boolean isNew, int warrantyPeriod, boolean isActive, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.isFeatured = isFeatured;
        this.isBestSeller = isBestSeller;
        this.isNew = isNew;
        this.warrantyPeriod = warrantyPeriod;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
    }

    public Product(int productId, String productName, String description, BigDecimal price, int discount, int supplierId, int categoryId, String categoryName, int brandId, String brandName, boolean isFeatured, boolean isBestSeller, boolean isNew, int warrantyPeriod, boolean isActive, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.isFeatured = isFeatured;
        this.isBestSeller = isBestSeller;
        this.isNew = isNew;
        this.warrantyPeriod = warrantyPeriod;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
    }
    
    public Product(int productId, String productName, String description, BigDecimal price, int discount, int supplierId, String supplierName, int categoryId, String categoryName, int brandId, String brandName, boolean isFeatured, boolean isBestSeller, boolean isNew, int warrantyPeriod, boolean isActive, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.isFeatured = isFeatured;
        this.isBestSeller = isBestSeller;
        this.isNew = isNew;
        this.warrantyPeriod = warrantyPeriod;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
    }
    
    public Product(int productId, String productName, String description, BigDecimal price, int discount, int supplierId, String supplierName, int categoryId, String categoryName, int brandId, String brandName, boolean isFeatured, boolean isBestSeller, boolean isNew, int warrantyPeriod, boolean isActive, String imageUrl, BigDecimal importStock) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.isFeatured = isFeatured;
        this.isBestSeller = isBestSeller;
        this.isNew = isNew;
        this.warrantyPeriod = warrantyPeriod;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.ImportPrice = importStock;
    }

    public Product(int productId, String productName, BigDecimal price, int discount, boolean isActive) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.isActive = isActive;
    }


    public Product(){}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public boolean isIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public boolean isIsBestSeller() {
        return isBestSeller;
    }

    public void setIsBestSeller(boolean isBestSeller) {
        this.isBestSeller = isBestSeller;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public String getBrandName() {
        return brandName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public BigDecimal getImportPrice() {
        return ImportPrice;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setImportPrice(BigDecimal ImportPrice) {
        this.ImportPrice = ImportPrice;
    }
}
