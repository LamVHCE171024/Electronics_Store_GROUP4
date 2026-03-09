/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author HP
 */
public class ImportStock {

    private int ioid;
    private int staffId;
    private int supplierId;
    private Timestamp importDate;
    private long totalAmount;
    private int isCompleted;
    private Suppliers supplier;
    private String fullName;
    private List<ImportStockDetail> importStockDetails;

    public ImportStock() {
    }

    public ImportStock(int staffId, int supplierId) {
        this.staffId = staffId;
        this.supplierId = supplierId;
    }

    public ImportStock(int staffId, int supplierId, long totalAmount) {
        this.staffId = staffId;
        this.supplierId = supplierId;
        this.totalAmount = totalAmount;
    }

    public ImportStock(int ioid, int staffId, int supplierId, Timestamp importDate, long totalAmount, int isCompleted) {
        this.ioid = ioid;
        this.staffId = staffId;
        this.supplierId = supplierId;
        this.importDate = importDate;
        this.totalAmount = totalAmount;
        this.isCompleted = isCompleted;
    }

    public int getIoid() {
        return ioid;
    }

    public void setIoid(int ioid) {
        this.ioid = ioid;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public void setSupplier(Suppliers supplier) {
        this.supplier = supplier;
    }

    public List<ImportStockDetail> getImportStockDetails() {
        return importStockDetails;
    }

    public void setImportStockDetails(List<ImportStockDetail> importStockDetails) {
        this.importStockDetails = importStockDetails;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
