/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP - Gia Khiêm
 */
public class ProductDetail {
    private int productDetailId;
    private int productId;
    private int categoryDetailId;
    private String attributeValue;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;

    public ProductDetail(int productDetailId, int ProductID, int CategoryDetailID, String attributeValue, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4) {
        this.productDetailId = productDetailId;
        this.productId = ProductID;
        this.categoryDetailId = CategoryDetailID;
        this.attributeValue = attributeValue;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public int getProductID() {
        return productId;
    }

    public int getCategoryDetailID() {
        return categoryDetailId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public void setProductID(int ProductID) {
        this.productId = ProductID;
    }

    public void setCategoryDetailID(int CategoryDetailID) {
        this.categoryDetailId = CategoryDetailID;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }
    
    
}
