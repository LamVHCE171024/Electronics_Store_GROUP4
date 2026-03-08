package model;

import java.math.BigDecimal;
import java.sql.Date;

public class InventoryStatistic {

    private int importId;          
    private String categoryName;
    private String brandName;
    private String model;
    private String fullName;
    private int stockQuantity;
    private int importQuantity;
    private int soldQuantity; 
    private String supplierName;
    private Date importDate;
    private BigDecimal productImportPrice;
    private String modelName;
    private int categoryId;
    private int productId;

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }


    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getImportQuantity() {
        return importQuantity;
    }
    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }
    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getImportDate() {
        return importDate;
    }
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public BigDecimal getProductImportPrice() {
        return productImportPrice;
    }
    public void setProductImportPrice(BigDecimal productImportPrice) {
        this.productImportPrice = productImportPrice;
    }

    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
}
