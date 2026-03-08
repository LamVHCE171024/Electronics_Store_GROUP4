package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;

import utils.DBContext;

public class OrderDAO extends DBContext {

    public List<Order> getOrderList() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE Status != 6 ORDER BY Status ASC";
        try ( PreparedStatement pre = conn.prepareStatement(sql)) {
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getLong("TotalAmount"),
                        rs.getString("OrderedDate"),
                        rs.getString("DeliveredDate"),
                        rs.getInt("Status"),
                        rs.getInt("Discount"),
                        rs.getString("AddressSnapshot"),
                        rs.getInt("AddressID"),
                        rs.getString("UpdatedAt")
                );

                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order getOrderByID(String orderID) {
        Order o = null;
        String query = "SELECT * FROM Orders WHERE OrderID = ?";
        try ( PreparedStatement pre = conn.prepareStatement(query)) {
            pre.setString(1, orderID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                o = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getLong("TotalAmount"),
                        rs.getString("OrderedDate"),
                        rs.getString("DeliveredDate"),
                        rs.getInt("Status"),
                        rs.getInt("Discount"),
                        rs.getString("AddressSnapshot"),
                        rs.getInt("AddressID"),
                        rs.getString("UpdatedAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public boolean updateStatus(int orderId, int newStatus) {
        String query = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, newStatus);
            pre.setInt(2, orderId);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> searchOrders(String searchQuery) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE FullName LIKE ? OR PhoneNumber LIKE ? ORDER BY Status ASC";
        try ( PreparedStatement pre = conn.prepareStatement(query)) {
            pre.setString(1, "%" + searchQuery + "%");
            pre.setString(2, "%" + searchQuery + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getLong("TotalAmount"),
                        rs.getString("OrderedDate"),
                        rs.getString("DeliveredDate"),
                        rs.getInt("Status"),
                        rs.getInt("Discount"),
                        rs.getString("AddressSnapshot"),
                        rs.getInt("AddressID"),
                        rs.getString("UpdatedAt")
                );
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int updateOrder(int orderID, int status) {
        int count = 0;
        String query = "UPDATE Orders SET Status = ?, UpdatedAt = DATEADD(HOUR, 7, GETUTCDATE()) WHERE OrderID = ?";
        String queryWithDeliveredDate = "UPDATE Orders SET Status = ?, DeliveredDate = DATEADD(HOUR, 7, GETUTCDATE()), UpdatedAt = DATEADD(HOUR, 7, GETUTCDATE()) WHERE OrderID = ?";

        try {
            PreparedStatement pre;
            if (status == 4) {
                pre = conn.prepareStatement(queryWithDeliveredDate);
            } else {
                pre = conn.prepareStatement(query);
            }
            pre.setInt(1, status);
            pre.setInt(2, orderID);

            count = pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Order> getOrdersByCustomerID(int customerID) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE CustomerID = ? ORDER BY OrderedDate DESC";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, customerID);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getLong("TotalAmount"),
                        rs.getString("OrderedDate"),
                        rs.getString("DeliveredDate"),
                        rs.getInt("Status"),
                        rs.getInt("Discount"),
                        rs.getString("AddressSnapshot"),
                        rs.getInt("AddressID"),
                        rs.getString("UpdatedAt")
                );
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int createOrder(int customerId, String fullName, String addressSnapshot,
            String phoneNumber, String orderedDate, String deliveredDate, int status,
            long totalAmount, int discount, int addressId) {

        String sql = "INSERT INTO Orders (CustomerID, FullName, AddressSnapshot, PhoneNumber, OrderedDate, DeliveredDate, Status, TotalAmount, Discount, AddressID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( PreparedStatement pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pre.setInt(1, customerId);
            pre.setString(2, fullName);
            pre.setString(3, addressSnapshot);
            pre.setString(4, phoneNumber);

            Timestamp orderedTimestamp = null;
            if (orderedDate != null && !orderedDate.trim().isEmpty()) {
                try {
                    orderedTimestamp = Timestamp.valueOf(orderedDate);
                } catch (IllegalArgumentException e) {
                    Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE,
                            "Invalid OrderedDate format: {0}", orderedDate);
                    return -1;
                }
            } else {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, "OrderedDate is null or empty");
                return -1;
            }
            pre.setTimestamp(5, orderedTimestamp);

            Timestamp deliveredTimestamp = null;
            if (deliveredDate != null && !deliveredDate.trim().isEmpty()) {
                try {
                    deliveredTimestamp = Timestamp.valueOf(deliveredDate);
                } catch (IllegalArgumentException e) {
                    Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE,
                            "Invalid DeliveredDate format: {0}", deliveredDate);
                    return -1;
                }
            }
            pre.setTimestamp(6, deliveredTimestamp);

            pre.setInt(7, status);
            pre.setLong(8, totalAmount);
            pre.setInt(9, discount);
            pre.setInt(10, addressId);

            int affectedRows = pre.executeUpdate();

            Logger.getLogger(OrderDAO.class.getName()).log(Level.INFO,
                    "SQL execution completed. Affected rows: {0}", affectedRows);

            if (affectedRows > 0) {
                try ( ResultSet rs = pre.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        Logger.getLogger(OrderDAO.class.getName()).log(Level.INFO,
                                "Order created successfully with OrderID: {0}", orderId);
                        return orderId;
                    }
                }
            }
            Logger.getLogger(OrderDAO.class.getName()).log(Level.WARNING, "No rows affected when inserting order");
            return -1;
        } catch (SQLException e) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE,
                    "SQL Error creating order: Message={0}, SQLState={1}, ErrorCode={2}",
                    new Object[]{e.getMessage(), e.getSQLState(), e.getErrorCode()});
            e.printStackTrace();
            return -1;
        }
    }
    ////-------------Tai------///
    public int countTodayOrders() {
    String sql = "SELECT COUNT(*) FROM Orders WHERE CAST(OrderedDate AS DATE) = CAST(GETDATE() AS DATE)";
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return rs.getInt(1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

}
