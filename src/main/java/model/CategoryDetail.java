/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP - Gia Khiêm
 */
public class CategoryDetail {
    private int categoryDetailID;
    private int CategoryID;
    private String categoryDatailName;
    private int categoryDetailsGroupID;

    public CategoryDetail() {
    }

    public CategoryDetail(int categoryDetailID, int CategoryID, String categoryDatailName, int categoryDetailsGroupID) {
        this.categoryDetailID = categoryDetailID;
        this.CategoryID = CategoryID;
        this.categoryDatailName = categoryDatailName;
        this.categoryDetailsGroupID = categoryDetailsGroupID;
    }

    public int getCategoryDetailID() {
        return categoryDetailID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public String getCategoryDatailName() {
        return categoryDatailName;
    }

    public void setCategoryDetailID(int categoryDetailID) {
        this.categoryDetailID = categoryDetailID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    public void setCategoryDatailName(String categoryDatailName) {
        this.categoryDatailName = categoryDatailName;
    }

    public int getCategoryDetailsGroupID() {
        return categoryDetailsGroupID;
    }

    public void setCategoryDetailsGroupID(int categoryDetailsGroupID) {
        this.categoryDetailsGroupID = categoryDetailsGroupID;
    }

    
    
}
