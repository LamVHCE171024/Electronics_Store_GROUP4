/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author VinhNTCE181630
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class ProductRating {

    private int rateID;
    private int customerID;
    private int productID;
    private int orderID;
    private Date createdDate;
    private int star;
    private String comment;
    private boolean isDeleted;
    private boolean isRead;
    private String fullName;
    private Customer customer;
    private List<RatingReplies> replies;

    public ProductRating(int star) {
        this.star = star;
    }

    public ProductRating(int rateID, int customerID, int productID, int orderID, Date createdDate, int star, String comment, boolean isDeleted, boolean isRead, String fullName) {
        this.rateID = rateID;
        this.customerID = customerID;
        this.productID = productID;
        this.orderID = orderID;
        this.createdDate = createdDate;
        this.star = star;
        this.comment = comment;
        this.isDeleted = isDeleted;
        this.isRead = isRead;
        this.fullName = fullName;
    }

    public ProductRating(int rateID, int customerID, int productID, int orderID, Date createdDate, int star, String comment, boolean isDeleted, boolean isRead) {
        this.rateID = rateID;
        this.customerID = customerID;
        this.productID = productID;
        this.orderID = orderID;
        this.createdDate = createdDate;
        this.star = star;
        this.comment = comment;
        this.isDeleted = isDeleted;
        this.isRead = isRead;
    }

    public ProductRating() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRateID() {
        return rateID;
    }

    public void setRateID(int rateID) {
        this.rateID = rateID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<RatingReplies> getReplies() {
        return replies;
    }

    public void setReplies(List<RatingReplies> replies) {
        this.replies = replies;
    }
}
