/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;
import utils.DBContext;

/**
 *
 * @author USER
 */
public class AccounttestDAO extends DBContext{

    public Account authenticate(String email, String password) {
        String sql = "SELECT * FROM Accounts WHERE Email = ? AND PasswordHash = ? AND IsActive = 1";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password); // Giả sử mật khẩu không mã hóa
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountID(rs.getInt("AccountID"));
                    account.setEmail(rs.getString("Email"));
                    account.setPasswordHash(rs.getString("PasswordHash"));
                    account.setIsActive(rs.getBoolean("IsActive"));
                    account.setRoleID(rs.getInt("RoleID"));
                    account.setEmailVerified(rs.getBoolean("EmailVerified"));
                    account.setProfileImageURL(rs.getString("ProfileImageURL"));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
