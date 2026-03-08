/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
import utils.DBContext;

/**
 *
 * @author HP - Gia Khiêm
 */
public class BrandDAO extends DBContext{
    
    public BrandDAO() {
        super();
    }
    
    public List<Brand> getAllBrand() {
        List<Brand> brandList = new ArrayList<>();
        String sql = "SELECT BrandID, BrandName, Description, CategoryID, ImgURLLogo FROM Brands";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");
                String descriptionBrand = rs.getString("Description");
                int categoryID = rs.getInt("CategoryID");
                String imgUrlLogo = rs.getString("ImgURLLogo");

                brandList.add(new Brand(brandId, brandName, descriptionBrand, categoryID, imgUrlLogo));
            }
            return brandList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return brandList;
    }
    
    public List<Brand> getBrandByCategoryId(int categoryId) {
        List<Brand> brandList = new ArrayList<>();
        String sql = "SELECT BrandID, BrandName, Description, CategoryID, ImgURLLogo FROM Brands where CategoryID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");
                String descriptionBrand = rs.getString("Description");
                int categoryIDDB = categoryId;
                String imgUrlLogo = rs.getString("ImgURLLogo");

                brandList.add(new Brand(brandId, brandName, descriptionBrand, categoryIDDB, imgUrlLogo));
            }
            return brandList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return brandList;
    }
    
}
