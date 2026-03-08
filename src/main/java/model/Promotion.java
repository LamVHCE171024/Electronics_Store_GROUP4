package model;

import java.sql.Timestamp;

public class Promotion {

    private int promotionID;
    private String targetType;
    private int targetID;
    private int discount;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    private String name;
    private boolean activeDiscount; // Thêm cột ActiveDiscount

    // Constructors
    public Promotion() {
    }

    public Promotion(String targetType, int targetID, int discount, Timestamp startDate, Timestamp endDate, String name, boolean activeDiscount) {
        this.targetType = targetType;
        this.targetID = targetID;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.activeDiscount = activeDiscount;
    }

    // Getters and Setters
    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public int getTargetID() {
        return targetID;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActiveDiscount() {
        return activeDiscount;
    } // Getter

    public void setActiveDiscount(boolean activeDiscount) {
        this.activeDiscount = activeDiscount;
    } // Setter
}
