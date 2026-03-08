/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import utils.DBContext;

/**
 *
 * @author VinhNTCE181630
 */
public class FeedbackDAO extends DBContext {
    public int insertFeedback(int customerID, int productID, int orderID, int star, String comment) {
        int count = 0;
        String sql = "INSERT INTO ProductRatings (CustomerID, ProductID, OrderID, CreatedDate, Star, Comment, isDeleted, isRead) "
                   + "VALUES (?, ?, ?, GETDATE(), ?, ?, 0, 0)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.setInt(2, productID);
            ps.setInt(3, orderID);
            ps.setInt(4, star);
            ps.setString(5, comment);
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
