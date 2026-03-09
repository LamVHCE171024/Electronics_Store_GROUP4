package dao;

import model.CustomerVoucher;
import model.Voucher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

public class CustomerVoucherDAO extends DBContext {

    public List<CustomerVoucher> getAllVouchersForCustomer(int customerId) {
        List<CustomerVoucher> list = new ArrayList<>();

        System.out.println("=== DEBUG: Getting vouchers for customer: " + customerId);
        System.out.println("=== DEBUG: Connection status: " + (conn != null ? "Connected" : "NULL"));

        // 1. Kiểm tra personal vouchers
        String sqlPersonal = "SELECT cv.CustomerID, cv.VoucherID, cv.ExpirationDate, cv.Quantity, "
                + "v.VoucherID as V_VoucherID, v.Code, v.DiscountPercent, v.ExpiryDate, v.MinOrderAmount, "
                + "v.MaxDiscountAmount, v.UsageLimit, v.UsedCount, v.IsActive, v.CreatedAt, v.Description, v.IsGlobal "
                + "FROM CustomerVoucher cv "
                + "JOIN Vouchers v ON cv.VoucherID = v.VoucherID "
                + "WHERE cv.CustomerID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sqlPersonal)) {
            ps.setInt(1, customerId);
            System.out.println("=== DEBUG: Executing personal voucher query for customerId: " + customerId);
            ResultSet rs = ps.executeQuery();

            int personalCount = 0;
            while (rs.next()) {
                personalCount++;
                System.out.println("=== DEBUG: Found personal voucher: " + rs.getString("Code"));

                Voucher voucher = new Voucher(
                        rs.getInt("V_VoucherID"),
                        rs.getString("Code"),
                        rs.getInt("DiscountPercent"),
                        rs.getDate("ExpiryDate"),
                        rs.getDouble("MinOrderAmount"),
                        rs.getDouble("MaxDiscountAmount"),
                        rs.getInt("UsageLimit"),
                        rs.getInt("UsedCount"),
                        rs.getBoolean("IsActive"),
                        rs.getDate("CreatedAt"),
                        rs.getString("Description"),
                        rs.getBoolean("IsGlobal")
                );
                CustomerVoucher cv = new CustomerVoucher(
                        rs.getInt("CustomerID"),
                        rs.getInt("VoucherID"),
                        rs.getDate("ExpirationDate"),
                        rs.getInt("Quantity"),
                        voucher
                );
                list.add(cv);
            }
            System.out.println("=== DEBUG: Personal vouchers found: " + personalCount);
        } catch (Exception e) {
            System.out.println("=== DEBUG: Error in personal voucher query: " + e.getMessage());
            e.printStackTrace();
        }

        // 2. Kiểm tra global vouchers
        String sqlGlobal = "SELECT v.VoucherID as V_VoucherID, v.Code, v.DiscountPercent, v.ExpiryDate, v.MinOrderAmount, "
                + "v.MaxDiscountAmount, v.UsageLimit, v.UsedCount, v.IsActive, v.CreatedAt, v.Description, v.IsGlobal "
                + "FROM Vouchers v "
                + "WHERE v.IsGlobal = 1 AND v.IsActive = 1 AND v.ExpiryDate >= GETDATE() "
                + "AND NOT EXISTS (SELECT 1 FROM CustomerVoucher cv WHERE cv.CustomerID = ? AND cv.VoucherID = v.VoucherID)";

        try ( PreparedStatement ps = conn.prepareStatement(sqlGlobal)) {
            ps.setInt(1, customerId);
            System.out.println("=== DEBUG: Executing global voucher query for customerId: " + customerId);
            ResultSet rs = ps.executeQuery();

            int globalCount = 0;
            while (rs.next()) {
                globalCount++;
                System.out.println("=== DEBUG: Found global voucher: " + rs.getString("Code"));

                Voucher voucher = new Voucher(
                        rs.getInt("V_VoucherID"),
                        rs.getString("Code"),
                        rs.getInt("DiscountPercent"),
                        rs.getDate("ExpiryDate"),
                        rs.getDouble("MinOrderAmount"),
                        rs.getDouble("MaxDiscountAmount"),
                        rs.getInt("UsageLimit"),
                        rs.getInt("UsedCount"),
                        rs.getBoolean("IsActive"),
                        rs.getDate("CreatedAt"),
                        rs.getString("Description"),
                        rs.getBoolean("IsGlobal")
                );
                CustomerVoucher cv = new CustomerVoucher(
                        customerId,
                        rs.getInt("V_VoucherID"),
                        rs.getDate("ExpiryDate"),
                        1,
                        voucher
                );
                list.add(cv);
            }
            System.out.println("=== DEBUG: Global vouchers found: " + globalCount);
        } catch (Exception e) {
            System.out.println("=== DEBUG: Error in global voucher query: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== DEBUG: Total vouchers returned: " + list.size());
        return list;
    }

    // --------- Các hàm khác giữ nguyên nếu cần ----------
    public boolean assignVoucher(CustomerVoucher cv) {
        String sql = "INSERT INTO CustomerVoucher (CustomerID, VoucherID, ExpirationDate, Quantity) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cv.getCustomerId());
            ps.setInt(2, cv.getVoucherId());
            ps.setDate(3, new java.sql.Date(cv.getExpirationDate().getTime()));
            ps.setInt(4, cv.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeCustomerVoucher(int customerId, int voucherId) {
        String sql = "DELETE FROM CustomerVoucher WHERE CustomerID = ? AND VoucherID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean decreaseQuantity(int customerId, int voucherId) {
        String sql = "UPDATE CustomerVoucher SET Quantity = Quantity - 1 WHERE CustomerID = ? AND VoucherID = ? AND Quantity > 0";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasAvailableVoucher(int customerId, int voucherId) {
        String sql = "SELECT Quantity FROM CustomerVoucher WHERE CustomerID = ? AND VoucherID = ? AND Quantity > 0";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAsigned(int customerId, int voucherId) {
        String sql = "SELECT COUNT(*) FROM CustomerVoucher WHERE CustomerID = ? AND VoucherID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void decreaseVoucherQuantity(int customerId, int voucherId) {
        String sql = "UPDATE CustomerVoucher SET Quantity = Quantity - 1 WHERE CustomerID = ? AND VoucherID = ? AND Quantity > 0";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, voucherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
