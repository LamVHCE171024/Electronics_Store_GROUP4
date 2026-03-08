/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class Stock {
    private int IOID;
    private String staffName;
    private String supplierName;
    private Timestamp importDate;
    private long totalCost;
    private  Timestamp lastModify;
    private String productName;
    private int quantity;
    private long importPrice;
    private int productId;
    private String brandName;
    private String categoryName;
    private long retailPrice;
    private long profitMargin;
    
    public Stock(){}

    public Stock(int IOID, String staffName, String supplierName, Timestamp importDate, long totalCost, Timestamp lastModify, String productName, int quantity, long importPrice, int productId, String brandName, String categoryName, long retailPrice, long profitMargin) {
        this.IOID = IOID;
        this.staffName = staffName;
        this.supplierName = supplierName;
        this.importDate = importDate;
        this.totalCost = totalCost;
        this.lastModify = lastModify;
        this.productName = productName;
        this.quantity = quantity;
        this.importPrice = importPrice;
        this.productId = productId;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.retailPrice = retailPrice;
        this.profitMargin = profitMargin;
    }

    public int getIOID() {
        return IOID;
    }

    public void setIOID(int IOID) {
        this.IOID = IOID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public Timestamp getLastModify() {
        return lastModify;
    }

    public void setLastModify(Timestamp lastModify) {
        this.lastModify = lastModify;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(long importPrice) {
        this.importPrice = importPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(long retailPrice) {
        this.retailPrice = retailPrice;
    }

    public long getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(long profitMargin) {
        this.profitMargin = profitMargin;
    }

    
}
