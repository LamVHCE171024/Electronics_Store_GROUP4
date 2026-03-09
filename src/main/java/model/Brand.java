/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP - Gia Khiêm
 */
public class Brand {
    private int brandId;
    private String brandName;
    private String desciptionBrand;
    private int categoryID;
    private String imgUrlLogo;

    public Brand() {
    }

    public Brand(int brandId, String brandName, String desciptionBrand, int categoryID, String imgUrlLogo) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.desciptionBrand = desciptionBrand;
        this.categoryID = categoryID;
        this.imgUrlLogo = imgUrlLogo;
    }

    public Brand(int brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }
    
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setImgUrlLogo(String imgUrlLogo) {
        this.imgUrlLogo = imgUrlLogo;
    }

    public String getImgUrlLogo() {
        return imgUrlLogo;
    }

    public int getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getDesciptionBrand() {
        return desciptionBrand;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setDesciptionBrand(String desciptionBrand) {
        this.desciptionBrand = desciptionBrand;
    }
}
