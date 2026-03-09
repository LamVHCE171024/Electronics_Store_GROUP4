package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a payment for an order.
 */
public class Payment {
    private int paymentID;
    private int orderID;
    private String paymentMethod;
    private String paymentStatus;
    private Date paidDate;
    private BigDecimal amount;

    // Constructors
    public Payment() {
    }

    public Payment(int paymentID, int orderID, String paymentMethod, String paymentStatus, Date paidDate, BigDecimal amount) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paidDate = paidDate;
        this.amount = amount;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
}
