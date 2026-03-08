/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP - Gia Khiêm
 */
public class CategoryDetailGroup {
    private int CategoryDetailsGroupID;
    private String NameCategoryDetailsGroup;
    private int CategoryID;

    public CategoryDetailGroup(int CategoryDetailsGroupID, String NameCategoryDetailsGroup, int CategoryID) {
        this.CategoryDetailsGroupID = CategoryDetailsGroupID;
        this.NameCategoryDetailsGroup = NameCategoryDetailsGroup;
        this.CategoryID = CategoryID;
    }

    public CategoryDetailGroup() {
    }

    public int getCategoryDetailsGroupID() {
        return CategoryDetailsGroupID;
    }

    public String getNameCategoryDetailsGroup() {
        return NameCategoryDetailsGroup;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryDetailsGroupID(int CategoryDetailsGroupID) {
        this.CategoryDetailsGroupID = CategoryDetailsGroupID;
    }

    public void setNameCategoryDetailsGroup(String NameCategoryDetailsGroup) {
        this.NameCategoryDetailsGroup = NameCategoryDetailsGroup;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    
    
}
