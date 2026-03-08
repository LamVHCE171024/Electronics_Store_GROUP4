/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author HP - Gia Khiêm
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String descriptionCategory;
    private Timestamp createdAt;
    private String imgUrlLogo;
    private Boolean isActive;

    public Category(int categoryId, String categoryName, String descriptionCategory, Timestamp  createdAt, String imgUrlLogo, Boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.descriptionCategory = descriptionCategory;
        this.createdAt = createdAt;
        this.imgUrlLogo = imgUrlLogo;
        this.isActive = isActive;
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    

    public Category() {
    }

    public void setDescriptionCategory(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescriptionCategory() {
        return descriptionCategory;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setImgUrlLogo(String imgUrlLogo) {
        this.imgUrlLogo = imgUrlLogo;
    }

    public String getImgUrlLogo() {
        return imgUrlLogo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return descriptionCategory;
    }

    public void setCategortId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + ", descriptionCategory=" + descriptionCategory + ", createdAt=" + createdAt + ", imgUrlLogo=" + imgUrlLogo + ", isActive=" + isActive + '}';
    }
    
    

    
}
