package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Address;
import utils.DBContext;

public class AddressDAO extends DBContext {

    // Lấy tất cả địa chỉ của 1 khách hàng
    public List<Address> getAllAddressesByCustomerId(int customerId) {
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM Addresses WHERE CustomerID = ? ORDER BY IsDefault DESC, AddressID DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Address address = new Address(
                        rs.getInt("AddressID"),
                        rs.getInt("CustomerID"),
                        rs.getString("ProvinceName"),
                        rs.getString("DistrictName"),
                        rs.getString("WardName"),
                        rs.getString("AddressDetails"),
                        rs.getBoolean("IsDefault")
                );
                list.add(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới địa chỉ
    public int createAddress(Address a) {
        int n = 0;
        String sql = "INSERT INTO Addresses (CustomerID, ProvinceName, DistrictName, WardName, AddressDetails, IsDefault) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, a.getCustomerId());
            ps.setString(2, a.getProvinceName());
            ps.setString(3, a.getDistrictName());
            ps.setString(4, a.getWardName());
            ps.setString(5, a.getAddressDetails());
            ps.setBoolean(6, a.isDefault());
            n = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    // Lấy địa chỉ theo AddressID
    public Address getAddressById(int addressId) {
        String sql = "SELECT * FROM Addresses WHERE AddressID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Address(
                        rs.getInt("AddressID"),
                        rs.getInt("CustomerID"),
                        rs.getString("ProvinceName"),
                        rs.getString("DistrictName"),
                        rs.getString("WardName"),
                        rs.getString("AddressDetails"),
                        rs.getBoolean("IsDefault")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa địa chỉ theo ID
    public boolean deleteAddressById(int addressId) {
        String sql = "DELETE FROM Addresses WHERE AddressID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, addressId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật địa chỉ
    public boolean updateAddress(Address a) {
        String sql = "UPDATE Addresses SET ProvinceName = ?, DistrictName = ?, WardName = ?, AddressDetails = ?, IsDefault = ? "
                + "WHERE AddressID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, a.getProvinceName());
            ps.setString(2, a.getDistrictName());
            ps.setString(3, a.getWardName());
            ps.setString(4, a.getAddressDetails());
            ps.setBoolean(5, a.isDefault());
            ps.setInt(6, a.getAddressId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy địa chỉ mặc định của khách hàng
    public Address getDefaultAddress(int customerId) {
        String sql = "SELECT * FROM Addresses WHERE CustomerID = ? AND IsDefault = 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Address(
                        rs.getInt("AddressID"),
                        rs.getInt("CustomerID"),
                        rs.getString("ProvinceName"),
                        rs.getString("DistrictName"),
                        rs.getString("WardName"),
                        rs.getString("AddressDetails"),
                        rs.getBoolean("IsDefault")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra khách hàng đã có địa chỉ mặc định chưa
    public boolean hasDefaultAddress(int customerId) {
        String sql = "SELECT 1 FROM Addresses WHERE CustomerID = ? AND IsDefault = 1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đếm tổng số địa chỉ của khách hàng
    public int getTotalAddressOfCustomer(int customerId) {
        String sql = "SELECT COUNT(*) FROM Addresses WHERE CustomerID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Bỏ địa chỉ mặc định cũ (khi set mặc định mới)
    public void unsetDefaultAddresses(int customerId) {
        String sql = "UPDATE Addresses SET IsDefault = 0 WHERE CustomerID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm lấy địa chỉ mặc định theo CustomerID
    public Address getDefaultAddressByCustomerId(int customerId) {
        Address address = null;
        String sql = "SELECT AddressID, CustomerID, ProvinceName, DistrictName, WardName, AddressDetails, IsDefault "
                + "FROM Addresses "
                + "WHERE CustomerID = ? AND IsDefault = 1";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address = new Address();
                    address.setAddressId(rs.getInt("AddressID"));
                    address.setCustomerId(rs.getInt("CustomerID"));
                    address.setProvinceName(rs.getString("ProvinceName"));
                    address.setDistrictName(rs.getString("DistrictName"));
                    address.setWardName(rs.getString("WardName"));
                    address.setAddressDetails(rs.getString("AddressDetails"));
                    address.setDefault(rs.getBoolean("IsDefault"));
                } else {
                    Logger.getLogger(AddressDAO.class.getName()).log(Level.WARNING,
                            "No default address found for CustomerID: {0}", customerId);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE,
                    "Error fetching default address for CustomerID: {0}, Error: {1}",
                    new Object[]{customerId, e.getMessage()});
            e.printStackTrace();
        }
        return address;
    }

    public List<Address> getAddressesByCustomerId(int customerId) {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT AddressID, CustomerID, ProvinceName, DistrictName, WardName, AddressDetails, IsDefault "
                + "FROM Addresses "
                + "WHERE CustomerID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Address address = new Address();
                    address.setAddressId(rs.getInt("AddressID"));
                    address.setCustomerId(rs.getInt("CustomerID"));
                    address.setProvinceName(rs.getString("ProvinceName"));
                    address.setDistrictName(rs.getString("DistrictName"));
                    address.setWardName(rs.getString("WardName"));
                    address.setAddressDetails(rs.getString("AddressDetails"));
                    address.setDefault(rs.getBoolean("IsDefault"));
                    addresses.add(address);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE,
                    "Error fetching addresses for CustomerID: {0}, Error: {1}",
                    new Object[]{customerId, e.getMessage()});
            e.printStackTrace();
        }
        return addresses;
    }
}
