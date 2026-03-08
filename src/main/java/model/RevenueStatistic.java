/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class RevenueStatistic {
    private Date orderDate;
    private int orderMonth;
    private int orderYear;
    private int totalOrder;
    private long totalRevenue;
    private int totalProductsSold;
    private int month;
    private long revenue;
    private int categoryId;
    private String categoryName;
    
    public RevenueStatistic(){}

    public RevenueStatistic(Date orderDate, int orderMonth, int orderYear, int totalOrder, long totalRevenue, int totalProductsSold, int month, long revenue, int categoryId, String categoryName) {
        this.orderDate = orderDate;
        this.orderMonth = orderMonth;
        this.orderYear = orderYear;
        this.totalOrder = totalOrder;
        this.totalRevenue = totalRevenue;
        this.totalProductsSold = totalProductsSold;
        this.month = month;
        this.revenue = revenue;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderMonth() {
        return orderMonth;
    }

    public void setOrderMonth(int orderMonth) {
        this.orderMonth = orderMonth;
    }

    public int getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(int orderYear) {
        this.orderYear = orderYear;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalProductsSold() {
        return totalProductsSold;
    }

    public void setTotalProductsSold(int totalProductsSold) {
        this.totalProductsSold = totalProductsSold;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}
