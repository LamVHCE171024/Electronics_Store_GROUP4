package model;

import java.time.LocalDateTime;

public class Suppliers {

    private int supplierID;
    private String taxId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime lastModify;
    private int activate;
    private String contactPerson;   
    private String supplyGroup;     
    private String description;     

    public Suppliers() {
    }

    // Constructor có tất cả thuộc tính
    public Suppliers(int supplierID, String taxId, String name, String email, String phoneNumber, String address,
                     LocalDateTime createdDate, LocalDateTime lastModify, int activate,
                     String contactPerson, String supplyGroup, String description) {
        this.supplierID = supplierID;
        this.taxId = taxId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdDate = createdDate;
        this.lastModify = lastModify;
        this.activate = activate;
        this.contactPerson = contactPerson;
        this.supplyGroup = supplyGroup;
        this.description = description;
    }

    public Suppliers(int supplierID, String name) {
        this.supplierID = supplierID;
        this.name = name;
    }
    
    // Getters and Setters
    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModify() {
        return lastModify;
    }

    public void setLastModify(LocalDateTime lastModify) {
        this.lastModify = lastModify;
    }


    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getSupplyGroup() {
        return supplyGroup;
    }

    public void setSupplyGroup(String supplyGroup) {
        this.supplyGroup = supplyGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
