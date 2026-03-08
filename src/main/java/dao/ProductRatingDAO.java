/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.DBContext;
import model.Product;
import model.ProductRating;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class ProductRatingDAO extends DBContext {

    public List<ProductRating> getAllProductRating(int productID) {
        List<ProductRating> list = new ArrayList<>();
        String query = "SELECT P.* ,C.FullName FROM ProductRatings AS P\n"
                + "JOIN Customers AS C ON C.CustomerID = P.CustomerID \n"
                + "WHERE ProductID = ?  ORDER BY P.CreatedDate DESC";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, productID);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ProductRating p = new ProductRating(
                        rs.getInt("RateID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("ProductID"),
                        rs.getInt("OrderID"),
                        rs.getDate("CreatedDate"),
                        rs.getInt("Star"),
                        rs.getString("Comment"),
                        rs.getBoolean("isDeleted"),
                        rs.getBoolean("isRead"),
                        rs.getString("FullName")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public float getStarAverage(int productId) {
        float star = 0;
        String query = "SELECT COALESCE(ROUND(SUM(Star) * 1.0 / COUNT(Star), 0), 0) AS avs\n"
                + "FROM ProductRatings as p  \n"
                + "WHERE p.ProductID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, productId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                star = rs.getFloat("avs");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return star;
    }

    public ProductRating getStarAVG(int productId) {
        int star = 0;
        ProductRating p = new ProductRating();
        String query = "SELECT COALESCE(ROUND(SUM(Star) * 1.0 / COUNT(Star), 0), 0) AS avs\n"
                + "FROM ProductRatings as p  \n"
                + "WHERE p.ProductID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, productId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                star = rs.getInt("avs");
                p.setStar(star);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return p;
    }

    public ProductRating getProductRating(int rateID) {
        ProductRating pro = new ProductRating();
        String query = "select * from ProductRatings WHERE RateID =?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, rateID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {

                pro.setRateID(rs.getInt("RateID"));
                pro.setCustomerID(rs.getInt("CustomerID"));
                pro.setProductID(rs.getInt("ProductID"));
                pro.setOrderID(rs.getInt("OrderID"));
                pro.setCreatedDate(rs.getDate("CreatedDate"));
                pro.setStar(rs.getInt("Star"));
                pro.setComment(rs.getString("Comment"));
                pro.setIsDeleted(rs.getBoolean("isDeleted"));
                pro.setIsRead(rs.getBoolean("isRead"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(pro.getProductID());
        return pro;
    }

    public List<ProductRating> getNewFeedback() {
        List<ProductRating> list = new ArrayList<>();
        String query = "SELECT P.* ,C.FullName FROM ProductRatings AS P JOIN Customers AS C ON C.CustomerID = P.CustomerID  ORDER BY P.IsRead ASC";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ProductRating p = new ProductRating(
                        rs.getInt("RateID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("ProductID"),
                        rs.getInt("OrderID"),
                        rs.getDate("CreatedDate"),
                        rs.getInt("Star"),
                        rs.getString("Comment"),
                        rs.getBoolean("isDeleted"),
                        rs.getBoolean("isRead"),
                        rs.getString("FullName")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatusComment(int rateID, int status) {
        boolean isOk = false;
        String query = "Update ProductRatings SET IsDeleted = ? WHERE RateID =?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, status);
            pre.setInt(2, rateID);
            pre.executeUpdate();
            isOk = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk;
    }

   

    public int addProductRating(int customerId, int productId, int orderId, int star, String comment) {
        int count = 0;
        String query = "INSERT INTO ProductRatings "
                + "(CustomerID, ProductID, OrderID, CreatedDate, Star, Comment, isDeleted, isRead) "
                + "VALUES (?, ?, ?, GETDATE(), ?, ?, 0, 0)";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, customerId);
            pre.setInt(2, productId);
            pre.setInt(3, orderId);
            pre.setInt(4, star);
            pre.setString(5, comment);
            count = pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Product getProductID(int rateID) {
        Product p = new Product();
        String query = "select FullName , ProductID from Products where ProductID =(select ProductID from ProductRatings WHERE RateID =?)";

        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, rateID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                p.setProductName(rs.getString("ProductName"));
                p.setProductId(rs.getInt("ProductID"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    public boolean markReplyAsUnRead(int ReplyID) {
        String query = "UPDATE ProductRatings SET IsRead= 0 WHERE RateID = ?";

        try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ReplyID);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public List<ProductRating> getProductRatingsByProductId(int productId) {
        List<ProductRating> list = new ArrayList<>();

        // ✅ JOIN để lấy thêm FullName từ bảng Customers
        String sql = "SELECT P.*, C.FullName "
                + "FROM ProductRatings P "
                + "JOIN Customers C ON P.CustomerID = C.CustomerID "
                + "WHERE P.ProductID = ? AND P.IsDeleted = 0 "
                + "ORDER BY P.CreatedDate DESC";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductRating rating = new ProductRating();
                    rating.setRateID(rs.getInt("RateID"));
                    rating.setCustomerID(rs.getInt("CustomerID"));
                    rating.setProductID(rs.getInt("ProductID"));
                    rating.setCreatedDate(rs.getTimestamp("CreatedDate"));
                    rating.setStar(rs.getInt("Star"));
                    rating.setComment(rs.getString("Comment"));
                    rating.setIsDeleted(rs.getBoolean("IsDeleted"));
                    rating.setIsRead(rs.getBoolean("IsRead"));

                    // ✅ Thêm dòng này để set FullName (rất quan trọng)
                    rating.setFullName(rs.getString("FullName"));

                    list.add(rating);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean hasRatedProduct(int customerID, int productID, int orderID) {
        String sql = "SELECT COUNT(*) FROM ProductRatings WHERE CustomerID = ? AND ProductID = ? AND OrderID = ?";
        try (
                 PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ps.setInt(2, productID);
            ps.setInt(3, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //----- Tai----//
    public int countUnreadFeedback() {
        String sql = "SELECT COUNT(*) FROM ProductRatings WHERE IsRead = 0";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



}
