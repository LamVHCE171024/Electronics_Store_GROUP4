/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Customer;
import utils.DBContext;

/**
 *
 * @author pc
 */
public class CustomerDAO extends DBContext {

    public CustomerDAO() {
        super();
    }

    public List<Customer> getCustomerList() {
        List<Customer> list = new ArrayList<>();
        String sql = "Select CustomerID, a.Email, FullName, PhoneNumber,a.CreatedAt, a.IsActive from Customers c JOIN Accounts a on c.AccountID = a.AccountID";

        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("CustomerID");
                String email = rs.getString("Email");
                String fullName = rs.getString("FullName");
                String phone = rs.getString("PhoneNumber");
                Date createdAt = rs.getTimestamp("CreatedAt");
                boolean isActive = rs.getBoolean("IsActive");

                list.add(new Customer(id, email, fullName, phone, createdAt, isActive));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Customer getCustomerbyID(int customerID) {
        Customer cus = null;
        String sql = "SELECT c.CustomerID, a.Email, c.FullName, c.PhoneNumber,a.IsActive, c.BirthDate, c.Gender, d.AddressDetails FROM Customers c  JOIN Accounts a ON c.AccountID = a.AccountID JOIN Addresses d ON c.CustomerID = d.CustomerID WHERE c.CustomerID = ?";
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
                    String address = rs.getString("AddressDetails");

                    // Gọi constructor có thêm addressDetails
                    cus = new Customer(id, email, fullName, phone, isActive, birthday, gender, address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cus;
    }

    public boolean updateStatus(int customerID) {
        String sqlUpdate = "UPDATE Accounts SET isActive = CASE WHEN isActive = 1 THEN 0 ELSE 1 END WHERE AccountID = (SELECT AccountID FROM Customers WHERE CustomerID = ?)";

        try ( PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
            psUpdate.setInt(1, customerID);
            int rowsAffected = psUpdate.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public List<Customer> searchCustomerByName(String keyword) {
        List<Customer> list = new ArrayList<>();
        String sql = "Select CustomerID, a.Email, FullName, PhoneNumber,a.CreatedAt, a.IsActive from Customers c JOIN Accounts a on c.AccountID = a.AccountID WHERE LOWER(FullName) LIKE ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");  // tìm theo tên gần đúng

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("CustomerID");
                    String email = rs.getString("Email");
                    String fullName = rs.getString("FullName");
                    String phone = rs.getString("PhoneNumber");
                    Date createdAt = rs.getTimestamp("CreatedAt");
                    boolean isActive = rs.getBoolean("IsActive");

                    list.add(new Customer(id, email, fullName, phone, createdAt, isActive));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public Customer getCustomerByAccountId(int accountId) {
        Customer cus = null;
        String sql = "SELECT CustomerID, a.Email, FullName, PhoneNumber, a.IsActive, BirthDate, Gender "
                + "FROM Customers c JOIN Accounts a ON c.AccountID = a.AccountID "
                + "WHERE c.AccountID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
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
    //-----TAI-----//

    public int countTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM Customers";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO(); // giả sử bạn đã có class này
        List<Customer> accounts = dao.getCustomerList();

        for (Customer acc : accounts) {
            System.out.println("ID: " + acc.getId());
            System.out.println("Email: " + acc.getEmail());
            System.out.println("Password: " + acc.getPassword());
            System.out.println("Full Name: " + acc.getFullName());
            System.out.println("Phone: " + acc.getPhone());
            System.out.println("Created At: " + acc.getCreateAt());
            System.out.println("Is Active: " + acc.isActive());
            System.out.println("------------------------------");
        }
    }
}
