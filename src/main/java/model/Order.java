/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Vinhntce181630
 */
public class Order {

    private int orderID;
    private int accountID;
    private int CustomerId;
    private String fullName;
    private String phone;
    private long totalAmount;
    private String orderDate;
    private String deliveredDate;
    private int status;
    private int discount;
    private String addressSnapshot;
    private int addressID;
    private String updatedDate;

    public Order() {
    }

    public Order(int CustomerId, String fullName, String phone, long totalAmount, String orderDate, String deliveredDate, int status, int discount, String addressSnapshot, int addressID, String updatedDate) {
        this.CustomerId = CustomerId;
        this.fullName = fullName;
        this.phone = phone;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.deliveredDate = deliveredDate;
        this.status = status;
        this.discount = discount;
        this.addressSnapshot = addressSnapshot;
        this.addressID = addressID;
        this.updatedDate = updatedDate;
    }

    public Order(int CustomerId, String fullName, String phone, String orderDate, String addressSnapshot) {
        this.CustomerId = CustomerId;
        this.fullName = fullName;
        this.phone = phone;
        this.orderDate = orderDate;
        this.addressSnapshot = addressSnapshot;
    }

   

    public Order(int orderID, int accountID, String fullName, String phone, long totalAmount,
            String orderDate, String deliveredDate, int status, int discount,
            String addressSnapshot, int addressID, String updatedDate) {
        this.orderID = orderID;
        this.accountID = accountID;
        this.fullName = fullName;
        this.phone = phone;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.deliveredDate = deliveredDate;
        this.status = status;
        this.discount = discount;
        this.addressSnapshot = addressSnapshot;
        this.addressID = addressID;
        this.updatedDate = updatedDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getAddressSnapshot() {
        return addressSnapshot;
    }

    public void setAddressSnapshot(String addressSnapshot) {
        this.addressSnapshot = addressSnapshot;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
