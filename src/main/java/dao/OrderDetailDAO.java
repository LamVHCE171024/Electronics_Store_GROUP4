/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import utils.DBContext;
import model.OrderDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;

/**
 *
 * @author Vinh ne
 */
public class OrderDetailDAO extends DBContext {

    public List<OrderDetail> getAllByOrderId(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String query = "SELECT od.OrderID, od.ProductID, od.Quantity, od.Price, "
                + "p.ProductName "
                + "FROM OrderItems od "
                + "JOIN Products p ON od.ProductID = p.ProductID "
                + "WHERE od.OrderID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, orderId);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderID(rs.getInt("OrderID"));
                detail.setProductID(rs.getInt("ProductID"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setPrice(rs.getLong("Price"));
                detail.setProductName(rs.getString("ProductName"));
                list.add(detail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderDetail getOrderDetailOfEachOrder(int orderID) {
        OrderDetail od = null;
        try {
            PreparedStatement pre = conn.prepareStatement("SELECT TOP 1 od.OrderID, od.ProductID, od.Quantity, od.Price, c.[CategoryName], p.ProductName FROM OrderDetails as od\n"
                    + "join Products as p on p.ProductID = od.ProductID\n"
                    + "join Categories c ON p.CategoryID = c.CategoryID\n"
                    + "WHERE OrderID = ?");
            pre.setInt(1, orderID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                od = new OrderDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getLong(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException e) {
        }
        return od;
    }

    public List<OrderDetail> getOrderDetail(String orderid) {
        List<OrderDetail> list = new ArrayList<>();
        String query = "SELECT od.OrderID, od.ProductID, od.Quantity, od.Price, p.CategoryID, p.ProductName "
                + "FROM OrderDetails od "
                + "JOIN Products p ON p.ProductID = od.ProductID "
                + "WHERE od.OrderID = ?";

        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setString(1, orderid);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                OrderDetail od = new OrderDetail(
                        rs.getInt("OrderID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getLong("Price"),
                        rs.getString("CategoryID"),
                        rs.getString("ProductName")
                );
                list.add(od);
            }
        } catch (Exception e) {
            e.printStackTrace(); // để hiện lỗi nếu có
        }
        return list;
    }

    public boolean getCustomerByProductID(int customerId, int productId) {
        boolean isOk = false;
        String query = "SELECT CASE "
                + "WHEN NOT EXISTS (SELECT 1 FROM ProductRatings WHERE CustomerID = ? AND ProductID = ?) "
                + "AND EXISTS (SELECT 1 FROM OrderDetails od "
                + "JOIN Orders o ON od.OrderID = o.OrderID "
                + "WHERE o.CustomerID = ? AND od.ProductID = ? AND o.Status = 4) "
                + "THEN 1 ELSE 0 END AS CanReview;";

        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, customerId);
            pre.setInt(2, productId);
            pre.setInt(3, customerId);
            pre.setInt(4, productId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                isOk = rs.getBoolean("CanReview");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    public static void main(String[] args) {
        OrderDetailDAO od = new OrderDetailDAO();
        //System.out.println(od.getOrderDetailOfEachOrder(2));
        for (OrderDetail order : od.getOrderDetail("3")) {
            System.out.println(order.getPrice());
        }
    }

    public List<OrderDetail> getOrderDetailsByOrderID(int orderID) {
        List<OrderDetail> list = new ArrayList<>();
        String sql
                = "SELECT od.OrderID, od.ProductID, od.Quantity, od.Price, "
                + "p.ProductName, c.CategoryName "
                + "FROM OrderDetails od "
                + "JOIN Products p ON od.ProductID = p.ProductID "
                + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                + "WHERE od.OrderID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderID(rs.getInt("OrderID"));
                detail.setProductID(rs.getInt("ProductID"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setPrice(rs.getLong("Price"));
                detail.setProductName(rs.getString("ProductName"));
                detail.setCategory(rs.getString("CategoryName"));

                list.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addOrderDetails(int orderId, List<CartItem> cartItems) {
        String sql = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            try {
                for (CartItem item : cartItems) {
                    Product product = item.getProduct();
                    BigDecimal unitPrice = product.getPrice();
                    BigDecimal discount = BigDecimal.valueOf(product.getDiscount());
                    BigDecimal discountFactor = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                    BigDecimal discountedPrice = unitPrice.multiply(discountFactor);

                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getProductID());
                    ps.setInt(3, item.getQuantity());
                    ps.setBigDecimal(4, discountedPrice);
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
