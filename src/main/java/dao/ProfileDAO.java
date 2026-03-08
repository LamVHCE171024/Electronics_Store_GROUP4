/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Customer;
import utils.DBContext;

/**
 *
 * @author pc
 */
public class ProfileDAO extends DBContext {

    public ProfileDAO() {
        super();
    }

    public Customer getCustomerbyID(int customerID) {
        Customer cus = null;
        String sql = "SELECT c.CustomerID, a.Email, FullName, PhoneNumber,a.IsActive,BirthDate,Gender \n"
                + "FROM Accounts a JOIN Customers c ON c.AccountID = a.AccountID \n"
                + "WHERE a.AccountID = ?;";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("CustomerID");
                    String email = rs.getString("Email");
                    String fullName = rs.getString("FullName");
                    String phone = rs.getString("PhoneNumber");
                    boolean isActive = rs.getBoolean("IsActive");
                    String birthday = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    cus = new Customer(id, email, fullName, phone, isActive, birthday, gender);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cus;
    }

    public Customer getCustomerbyCustomerID(int customerID) {
        Customer cus = null;
        String sql = "SELECT c.CustomerID, a.Email, FullName, PhoneNumber,a.IsActive,BirthDate,Gender \n"
                + "FROM Accounts a JOIN Customers c ON c.AccountID = a.AccountID \n"
                + "WHERE c.CustomerID = ?;";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("CustomerID");
                    String email = rs.getString("Email");
                    String fullName = rs.getString("FullName");
                    String phone = rs.getString("PhoneNumber");
                    boolean isActive = rs.getBoolean("IsActive");
                    String birthday = rs.getString("BirthDate");
                    String gender = rs.getString("Gender");
                    cus = new Customer(id, email, fullName, phone, isActive, birthday, gender);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cus;
    }
    public int getAccountIDByCustomerID(int customerID) {
        String sql = "SELECT AccountID FROM Customers WHERE CustomerID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("AccountID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // không tìm thấy
    }
    

    public boolean updateProfileCustomer(int customerID, String fullName, String phone, String birthDate, String gender) {
        String sql = "UPDATE Customers SET FullName = ?, PhoneNumber = ?, BirthDate = ?, Gender = ? WHERE CustomerID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, birthDate);
            ps.setString(4, gender);
            ps.setInt(5, customerID);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
