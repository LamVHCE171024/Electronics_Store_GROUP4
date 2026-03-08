/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Account;
import model.Customer;
import utils.DBContext;

/**
 *
 * @author pc
 */
public class AccountDAO extends DBContext {

    public AccountDAO() {
        super();
    }

    public String hashMD5(String pass) {
        try {
            MessageDigest mes = MessageDigest.getInstance("MD5");
            byte[] mesMD5 = mes.digest(pass.getBytes());
            //[0x0a, 0x7a, 0x12, 0x09,...]
            StringBuilder str = new StringBuilder();
            for (byte b : mesMD5) {
                //0x0a
                String ch = String.format("%02x", b);
                //0a
                str.append(ch);
            }
            //str = 0a7a1209
            return str.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public Account verifyMD5(String email, String pass) {
        Account acc = new Account();
        String sql = "SELECT * FROM Accounts WHERE Email = ? AND PasswordHash = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, hashMD5(pass));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                acc.setAccountID(rs.getInt("AccountID"));
                acc.setEmail(rs.getString("Email"));
                acc.setPasswordHash(rs.getString("PasswordHash"));
                acc.setIsActive(rs.getBoolean("IsActive"));
                acc.setRoleID(rs.getInt("RoleID"));
                return acc;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean checkEmailExisted(String email) {
        String sql = "SELECT * FROM Accounts WHERE Email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean changePassword(int id, String oldPassword, String newPassword) {
        String sqlCheck = "SELECT PasswordHash FROM Accounts WHERE AccountID = ?";
        String sqlUpdate = "UPDATE Accounts SET PasswordHash = ? WHERE AccountID = ?";

        try ( PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentPasswordHash = rs.getString("PasswordHash");
                String oldPasswordHash = hashMD5(oldPassword);
                System.out.println("🔐 [DEBUG] Mật khẩu hash trong DB:      " + currentPasswordHash);
                System.out.println("🔐 [DEBUG] Mật khẩu hash người dùng nhập: " + oldPasswordHash);
                System.out.println("🔐 [DEBUG] Mật khẩu gốc từ form: " + oldPassword);

                // Kiểm tra mật khẩu cũ đúng không
                if (!currentPasswordHash.equals(oldPasswordHash)) {
                    return false; // Mật khẩu cũ sai
                }
            } else {
                return false; // Không tìm thấy account
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Nếu đúng thì update mật khẩu mới
        try ( PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
            String newPasswordHash = hashMD5(newPassword);
            updateStmt.setString(1, newPasswordHash);
            updateStmt.setInt(2, id);
            int rowsAffected = updateStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addNewAccount(String email, String passwordHash, String fullName, String phone) {
        String insertAccountSQL = "INSERT INTO Accounts (Email, PasswordHash, RoleID, IsActive) VALUES (?, ?, ?, ?)";
        String getNextCustomerIdSQL = "SELECT ISNULL(MAX(CustomerID), 0) + 1 AS NextID FROM Customers";
        String insertCustomerSQL = "INSERT INTO Customers (CustomerID, AccountID, FullName, PhoneNumber) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // bắt đầu transaction

            // Thêm account
            PreparedStatement psAcc = conn.prepareStatement(insertAccountSQL, Statement.RETURN_GENERATED_KEYS);
            psAcc.setString(1, email);
            psAcc.setString(2, passwordHash);
            psAcc.setInt(3, 3); // RoleID = 3 (customer)
            psAcc.setBoolean(4, true);
            int accInserted = psAcc.executeUpdate();

            if (accInserted == 0) {
                conn.rollback();
                return false;
            }

            // Lấy AccountID vừa tạo
            ResultSet rsAcc = psAcc.getGeneratedKeys();
            int accountId;
            if (rsAcc.next()) {
                accountId = rsAcc.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            // Lấy CustomerID mới
            PreparedStatement psNextId = conn.prepareStatement(getNextCustomerIdSQL);
            ResultSet rs = psNextId.executeQuery();
            int customerId = 1;
            if (rs.next()) {
                customerId = rs.getInt("NextID");
            }

            // Thêm vào bảng Customers
            PreparedStatement psCus = conn.prepareStatement(insertCustomerSQL);
            psCus.setInt(1, customerId);
            psCus.setInt(2, accountId);
            psCus.setString(3, fullName);
            psCus.setString(4, phone);
            int cusInserted = psCus.executeUpdate();

            if (cusInserted == 0) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("❌ DB Error: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addNewAccountGoogle(String email, String fullName, String phone) {
        String insertAccountSQL = "INSERT INTO Accounts (Email, RoleID, IsActive) VALUES (?, ?, ?)";
        String getNextCustomerIdSQL = "SELECT ISNULL(MAX(CustomerID), 0) + 1 AS NextID FROM Customers";
        String insertCustomerSQL = "INSERT INTO Customers (CustomerID, AccountID, FullName, PhoneNumber) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // bắt đầu transaction

            // Thêm account
            PreparedStatement psAcc = conn.prepareStatement(insertAccountSQL, Statement.RETURN_GENERATED_KEYS);
            psAcc.setString(1, email);
            psAcc.setInt(2, 3); // RoleID = 3 (customer)
            psAcc.setBoolean(3, true);
            int accInserted = psAcc.executeUpdate();

            if (accInserted == 0) {
                conn.rollback();
                return false;
            }

            // Lấy AccountID vừa tạo
            ResultSet rsAcc = psAcc.getGeneratedKeys();
            int accountId;
            if (rsAcc.next()) {
                accountId = rsAcc.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            // Lấy CustomerID mới
            PreparedStatement psNextId = conn.prepareStatement(getNextCustomerIdSQL);
            ResultSet rs = psNextId.executeQuery();
            int customerId = 1;
            if (rs.next()) {
                customerId = rs.getInt("NextID");
            }

            // Thêm vào bảng Customers
            PreparedStatement psCus = conn.prepareStatement(insertCustomerSQL);
            psCus.setInt(1, customerId);
            psCus.setInt(2, accountId);
            psCus.setString(3, fullName);
            psCus.setString(4, phone);
            int cusInserted = psCus.executeUpdate();

            if (cusInserted == 0) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("❌ DB Error: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Account getAccountByEmail(String email) {
        String sql = "SELECT * FROM Accounts WHERE Email = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int accountId = rs.getInt("AccountID");
                    int roleId = rs.getInt("RoleID");
                    boolean status = rs.getBoolean("IsActive");

                    return new Account(accountId, email, status, roleId);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ getAccountByEmail Error: " + e.getMessage());
        }
        return null; // Không tìm thấy hoặc có lỗi
    }

    public int getRoleByEmail(String email) {
        String sql = "SELECT RoleID FROM Accounts WHERE Email = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("RoleID");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ getRoleByEmail Error: " + e.getMessage());
        }
        return -1; // Không tìm thấy hoặc có lỗi
    }

    public boolean updatePassword(String email, String hashedPassword) {
        String sql = "UPDATE Accounts SET PasswordHash = ? WHERE Email = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adminResetPassword(int id, String newPassword) {
        String sqlUpdate = "UPDATE Accounts SET PasswordHash = ? WHERE AccountID = ?";

        try ( PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
            String newPasswordHash = hashMD5(newPassword);
            updateStmt.setString(1, newPasswordHash);
            updateStmt.setInt(2, id);
            int rowsAffected = updateStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        String pass = "123456";
        System.out.println(dao.hashMD5(pass));
    }
}
