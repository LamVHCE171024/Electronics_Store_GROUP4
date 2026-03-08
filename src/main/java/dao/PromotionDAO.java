package dao;

import model.Promotion;
import java.sql.*;
import utils.DBContext;

public class PromotionDAO extends DBContext {

    public boolean addPromotion(String targetType, int targetID, int discount, Timestamp startDate, Timestamp endDate, String name) {
        // Kiểm tra nếu sản phẩm đã có khuyến mãi đang hoạt động
        if (isProductAlreadyInActivePromotion(targetID, targetType)) {
            return false;
        }

        String sql = "INSERT INTO Promotions (TargetType, TargetID, Discount, StartDate, EndDate, Name, ActiveDiscount) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, targetType);
            pstmt.setInt(2, targetID);
            pstmt.setInt(3, discount);
            pstmt.setTimestamp(4, startDate);
            pstmt.setTimestamp(5, endDate);
            pstmt.setString(6, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                applyPromotionsToProducts(); // Áp dụng khuyến mãi sau khi thêm
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra xem sản phẩm đã có khuyến mãi đang hoạt động
    public boolean isProductAlreadyInActivePromotion(int targetID, String newTargetType) {
        String sql = "SELECT COUNT(*) FROM Promotions p "
                + "WHERE p.ActiveDiscount = 1 "
                + "AND GETDATE() BETWEEN p.StartDate AND p.EndDate "
                + "AND (p.TargetType = 'PRODUCT' AND p.TargetID = ? "
                + "     OR (p.TargetType = 'CATEGORY' AND EXISTS (SELECT 1 FROM Products WHERE CategoryID = ? AND ProductID = ?)) "
                + "     OR (p.TargetType = 'BRAND' AND EXISTS (SELECT 1 FROM Products WHERE BrandID = ? AND ProductID = ?)))";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, targetID); // Kiểm tra sản phẩm cụ thể
            pstmt.setInt(2, targetID); // Kiểm tra Category
            pstmt.setInt(3, targetID); // Kiểm tra ProductID
            pstmt.setInt(4, targetID); // Kiểm tra Brand
            pstmt.setInt(5, targetID); // Kiểm tra ProductID
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Giả định lỗi thì không cho thêm
        }
    }

    public void applyPromotionsToProducts() {
        String sql = "UPDATE p SET p.Discount = COALESCE((SELECT TOP 1 pr.Discount "
                + "                          FROM Promotions pr "
                + "                          WHERE (pr.TargetType = 'BRAND' AND p.BrandID = pr.TargetID) OR "
                + "                                (pr.TargetType = 'CATEGORY' AND p.CategoryID = pr.TargetID) OR "
                + "                                (pr.TargetType = 'PRODUCT' AND p.ProductID = pr.TargetID) "
                + "                          AND pr.ActiveDiscount = 1 "
                + "                          AND GETDATE() BETWEEN pr.StartDate AND pr.EndDate "
                + "                          ORDER BY pr.CreatedAt DESC), 0), "
                + "    p.DiscountStartDate = (SELECT TOP 1 pr.StartDate "
                + "                          FROM Promotions pr "
                + "                          WHERE (pr.TargetType = 'BRAND' AND p.BrandID = pr.TargetID) OR "
                + "                                (pr.TargetType = 'CATEGORY' AND p.CategoryID = pr.TargetID) OR "
                + "                                (pr.TargetType = 'PRODUCT' AND p.ProductID = pr.TargetID) "
                + "                          AND pr.ActiveDiscount = 1 "
                + "                          AND GETDATE() BETWEEN pr.StartDate AND pr.EndDate "
                + "                          ORDER BY pr.CreatedAt DESC), "
                + "    p.DiscountEndDate = (SELECT TOP 1 pr.EndDate "
                + "                        FROM Promotions pr "
                + "                        WHERE (pr.TargetType = 'BRAND' AND p.BrandID = pr.TargetID) OR "
                + "                              (pr.TargetType = 'CATEGORY' AND p.CategoryID = pr.TargetID) OR "
                + "                              (pr.TargetType = 'PRODUCT' AND p.ProductID = pr.TargetID) "
                + "                        AND pr.ActiveDiscount = 1 "
                + "                        AND GETDATE() BETWEEN pr.StartDate AND pr.EndDate "
                + "                        ORDER BY pr.CreatedAt DESC) "
                + "FROM Products p "
                + "WHERE EXISTS (SELECT 1 "
                + "              FROM Promotions pr "
                + "              WHERE (pr.TargetType = 'BRAND' AND p.BrandID = pr.TargetID) OR "
                + "                    (pr.TargetType = 'CATEGORY' AND p.CategoryID = pr.TargetID) OR "
                + "                    (pr.TargetType = 'PRODUCT' AND p.ProductID = pr.TargetID) "
                + "              AND pr.ActiveDiscount = 1 "
                + "              AND GETDATE() BETWEEN pr.StartDate AND pr.EndDate)";
        try ( Statement stmt = conn.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println("Rows updated in Products: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
