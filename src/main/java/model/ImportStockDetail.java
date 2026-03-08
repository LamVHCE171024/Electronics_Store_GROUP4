/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class ImportStockDetail {

    private int ioid;
    private Product product;
    private int quantity;
    private long unitPrice;
    private int quantityLeft;

    public ImportStockDetail(int ioid, Product product, int quantity, long unitPrice, int quantityLeft) {
        this.ioid = ioid;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.quantityLeft = quantityLeft;
    }
    
    public ImportStockDetail(){}

    public int getIoid() {
        return ioid;
    }

    public void setIoid(int ioid) {
        this.ioid = ioid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    
}
