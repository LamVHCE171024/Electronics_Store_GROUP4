/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Account;
import model.Staff;
import utils.DBContext;

/**
 *
 * @author pc
 */
public class StaffDAO extends DBContext {

    public StaffDAO() {
        super();
    }

    public List<Staff> getStaffList() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT StaffID, a.Email, FullName, HiredDate FROM Staff s JOIN Accounts a ON s.AccountID = a.AccountID";

        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("StaffID");
                String email = rs.getString("Email");
                String fullName = rs.getString("FullName");
                Date hiredDate = rs.getDate("HiredDate"); // dùng getDate vì trong DB là kiểu DATE

                list.add(new Staff(id, email, fullName, hiredDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getStaffIDByAccountID(int accountID) {
        String sql = "SELECT StaffID FROM Staff WHERE AccountID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("StaffID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Staff getStaffByID(int staffID) {
        Staff sta = null;
        String sql = "SELECT StaffID, a.Email, FullName,s.PhoneNumber, HiredDate,Position,s.BirthDate,s.Gender  FROM Staff s JOIN Accounts a ON s.AccountID = a.AccountID Where StaffID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("StaffID");
                    String email = rs.getString("Email");
                    String fullName = rs.getString("FullName");
                    String phone = rs.getString("PhoneNumber");
                    Date hiredDate = rs.getTimestamp("HiredDate");
                    String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(hiredDate);
                    String position = rs.getString("Position");
                    Date birthday = rs.getTimestamp("BirthDate");
                    String gender = rs.getString("Gender");
                    sta = new Staff(id, email, fullName, phone, hiredDate, position, birthday, gender);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log ra logger nếu dùng log4j, slf4j
        }
        return sta; // nếu không có bản ghi hoặc có lỗi
    }

    public List<Staff> searchStaffByName(String keyword) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT StaffID, a.Email, FullName, HiredDate "
                + "FROM Staff s JOIN Accounts a ON s.AccountID = a.AccountID "
                + "WHERE LOWER(FullName) LIKE ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("StaffID");
                    String email = rs.getString("Email");
                    String fullName = rs.getString("FullName");
                    Date hiredDate = rs.getDate("HiredDate");

                    list.add(new Staff(id, email, fullName, hiredDate));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // TRUONG HOANG MINH:
    /**
     * Deletes a staff and its associated account from the database. Step 1:
     * Delete from Staff table. Step 2: Delete from Accounts table.
     */
    public boolean deleteStaffById(int staffId) {
        String deleteStaffSQL = "DELETE FROM Staff WHERE StaffID = ?";
        String deleteAccountSQL = "DELETE FROM Accounts WHERE AccountID = ?";

        try (
                 PreparedStatement deleteStaffStmt = conn.prepareStatement(deleteStaffSQL);  PreparedStatement deleteAccountStmt = conn.prepareStatement(deleteAccountSQL)) {
            // Xóa từ bảng Staff
            deleteStaffStmt.setInt(1, staffId);
            int staffDeleted = deleteStaffStmt.executeUpdate();

            // Nếu xóa staff thành công thì mới xóa account
            if (staffDeleted > 0) {
                deleteAccountStmt.setInt(1, staffId);
                deleteAccountStmt.executeUpdate();
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createStaffWithAccount(Account account, Staff staff) {
        String insertAccountSQL = "INSERT INTO Accounts (Email, PasswordHash, RoleID, IsActive, EmailVerified) VALUES (?, ?, 2, 1, 1)";
        String insertStaffSQL = "INSERT INTO Staff (StaffID, AccountID, FullName, PhoneNumber, BirthDate, Gender, Position, HiredDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Thêm vào bảng Accounts
            PreparedStatement accountStmt = conn.prepareStatement(insertAccountSQL, Statement.RETURN_GENERATED_KEYS);
            accountStmt.setString(1, account.getEmail());
            accountStmt.setString(2, account.getPasswordHash());
            

            int affectedRows = accountStmt.executeUpdate();

            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            // Lấy AccountID vừa tạo
            ResultSet generatedKeys = accountStmt.getGeneratedKeys();
            int accountId = -1;
            if (generatedKeys.next()) {
                accountId = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            // 2. Thêm vào bảng Staff (StaffID = AccountID)
            PreparedStatement staffStmt = conn.prepareStatement(insertStaffSQL);
            staffStmt.setInt(1, accountId); // StaffID
            staffStmt.setInt(2, accountId); // AccountID
            staffStmt.setString(3, staff.getFullName());
            staffStmt.setString(4, staff.getPhone());
            staffStmt.setDate(5, new java.sql.Date(staff.getBirthDay().getTime()));
            staffStmt.setString(6, staff.getGender());
            staffStmt.setString(7, staff.getPosition());
            staffStmt.setDate(8, new java.sql.Date(staff.getHiredDate().getTime()));

            int staffInserted = staffStmt.executeUpdate();

            if (staffInserted == 0) {
                conn.rollback();
                return false;
            }

            conn.commit(); // Cả hai thành công
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback(); // Có lỗi → rollback toàn bộ
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true); // Trả lại trạng thái auto commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateStaffWithAccount(Account account, Staff staff) {
        String updateAccountSQL = "UPDATE Accounts SET Email = ? WHERE AccountID = ?";
        String updateStaffSQL = "UPDATE Staff SET FullName = ?, PhoneNumber = ?, BirthDate = ?, Gender = ?, Position = ?, HiredDate = ? WHERE StaffID = ?";

        try {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Cập nhật bảng Accounts
            PreparedStatement accountStmt = conn.prepareStatement(updateAccountSQL);
            accountStmt.setString(1, account.getEmail());
            accountStmt.setInt(2, account.getAccountID());

            int affectedAcc = accountStmt.executeUpdate();
            if (affectedAcc == 0) {
                conn.rollback();
                return false;
            }

            // Cập nhật bảng Staff
            PreparedStatement staffStmt = conn.prepareStatement(updateStaffSQL);
            staffStmt.setString(1, staff.getFullName());
            staffStmt.setString(2, staff.getPhone());
            staffStmt.setDate(3, new java.sql.Date(staff.getBirthDay().getTime()));
            staffStmt.setString(4, staff.getGender());
            staffStmt.setString(5, staff.getPosition());
            staffStmt.setDate(6, new java.sql.Date(staff.getHiredDate().getTime()));
            staffStmt.setInt(7, staff.getStaffID());

            int affectedStaff = staffStmt.executeUpdate();
            if (affectedStaff == 0) {
                conn.rollback();
                return false;
            }

            conn.commit(); // Thành công cả hai bảng
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Staff getStaffById(int staffId) {
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff s = new Staff();
                s.setStaffID(rs.getInt("StaffID"));
                s.setFullName(rs.getString("FullName"));
                s.setPhone(rs.getString("PhoneNumber"));
                s.setBirthDay(rs.getDate("BirthDate"));
                s.setGender(rs.getString("Gender"));
                s.setPosition(rs.getString("Position"));
                s.setHiredDate(rs.getDate("HiredDate"));
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByStaffId(int staffId) {
        String sql = "SELECT a.* FROM Accounts a JOIN Staff s ON a.AccountID = s.AccountID WHERE s.StaffID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account a = new Account();
                a.setAccountID(rs.getInt("AccountID"));
                a.setEmail(rs.getString("Email"));
                a.setPasswordHash(rs.getString("PasswordHash"));
                a.setRoleID(rs.getInt("RoleID"));
                
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM Accounts WHERE Email = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT 1 FROM Staff WHERE PhoneNumber = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalStaff() {
        String sql = "SELECT COUNT(*) FROM Staff";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getStaffIdByAccountId(int accountId) {
        String sql = "SELECT StaffID FROM Staff WHERE AccountID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("StaffID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAccountIdByStaffId(int staffId) {
        String sql = "SELECT AccountID FROM Staff WHERE StaffID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("AccountID"); // lấy AccountID ra
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
