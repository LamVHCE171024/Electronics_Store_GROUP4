package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.RevenueStatistic;
import utils.DBContext;

public class RevenueStatisticDAO extends DBContext {

    public ArrayList<RevenueStatistic> getRevenueByDay() {
        ArrayList<RevenueStatistic> list = new ArrayList<>();
        String sql = "SELECT o.OrderedDate, "
                   + "COUNT(DISTINCT o.OrderID) AS TotalOrder, "
                   + "SUM(od.Quantity * od.Price) AS TotalRevenue, "
                   + "SUM(od.Quantity) AS TotalProductsSold "
                   + "FROM Orders o "
                   + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                   + "GROUP BY o.OrderedDate "
                   + "ORDER BY o.OrderedDate";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RevenueStatistic stat = new RevenueStatistic();
                stat.setOrderDate(rs.getDate("OrderedDate"));
                stat.setTotalOrder(rs.getInt("TotalOrder"));
                stat.setTotalRevenue(rs.getLong("TotalRevenue"));
                stat.setTotalProductsSold(rs.getInt("TotalProductsSold"));
                list.add(stat);
            }
        } catch (Exception e) {
            System.err.println("Error in getRevenueByDay: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<RevenueStatistic> getRevenueByMonth() {
        ArrayList<RevenueStatistic> list = new ArrayList<>();
        String sql = "SELECT MONTH(o.OrderedDate) AS Month, "
                   + "YEAR(o.OrderedDate) AS Year, "
                   + "COUNT(DISTINCT o.OrderID) AS TotalOrder, "
                   + "SUM(od.Quantity * od.Price) AS TotalRevenue, "
                   + "SUM(od.Quantity) AS TotalProductsSold "
                   + "FROM Orders o "
                   + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                   + "GROUP BY MONTH(o.OrderedDate), YEAR(o.OrderedDate) "
                   + "ORDER BY Year, Month";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RevenueStatistic stat = new RevenueStatistic();
                stat.setOrderMonth(rs.getInt("Month"));
                stat.setOrderYear(rs.getInt("Year"));
                stat.setTotalOrder(rs.getInt("TotalOrder"));
                stat.setTotalRevenue(rs.getLong("TotalRevenue"));
                stat.setTotalProductsSold(rs.getInt("TotalProductsSold"));
                list.add(stat);
            }
        } catch (Exception e) {
            System.err.println("Error in getRevenueByMonth: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<RevenueStatistic> getRevenueByCategory(int categoryId) {
        ArrayList<RevenueStatistic> list = new ArrayList<>();
        String sql = "SELECT c.CategoryID, c.CategoryName, "
                   + "COUNT(DISTINCT o.OrderID) AS TotalOrder, "
                   + "SUM(od.Quantity * od.Price) AS Revenue, "
                   + "SUM(od.Quantity) AS TotalProductsSold "
                   + "FROM Orders o "
                   + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                   + "JOIN Products p ON p.ProductID = od.ProductID "
                   + "JOIN Categories c ON c.CategoryID = p.CategoryID "
                   + "WHERE c.CategoryID = ? "
                   + "GROUP BY c.CategoryID, c.CategoryName";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RevenueStatistic stat = new RevenueStatistic();
                    stat.setCategoryId(rs.getInt("CategoryID"));
                    stat.setCategoryName(rs.getString("CategoryName"));
                    stat.setTotalOrder(rs.getInt("TotalOrder"));
                    stat.setTotalRevenue(rs.getLong("Revenue"));
                    stat.setTotalProductsSold(rs.getInt("TotalProductsSold"));
                    list.add(stat);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getRevenueByCategory: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<RevenueStatistic> getRevenueByCategoryOnDay(java.sql.Date date) {
        ArrayList<RevenueStatistic> list = new ArrayList<>();
        String sql = "SELECT c.CategoryID, c.CategoryName, "
                   + "SUM(od.Quantity * od.Price) AS Revenue "
                   + "FROM Orders o "
                   + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                   + "JOIN Products p ON od.ProductID = p.ProductID "
                   + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                   + "WHERE CAST(o.OrderedDate AS DATE) = ? "
                   + "GROUP BY c.CategoryID, c.CategoryName";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RevenueStatistic stat = new RevenueStatistic();
                    stat.setCategoryId(rs.getInt("CategoryID"));
                    stat.setCategoryName(rs.getString("CategoryName"));
                    stat.setTotalRevenue(rs.getLong("Revenue"));
                    list.add(stat);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getRevenueByCategoryOnDay: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<RevenueStatistic> getRevenueByCategoryOnMonth(int month, int year) {
        ArrayList<RevenueStatistic> list = new ArrayList<>();
        String sql = "SELECT c.CategoryID, c.CategoryName, "
                   + "SUM(od.Quantity * od.Price) AS Revenue "
                   + "FROM Orders o "
                   + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                   + "JOIN Products p ON od.ProductID = p.ProductID "
                   + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                   + "WHERE MONTH(o.OrderedDate) = ? AND YEAR(o.OrderedDate) = ? "
                   + "GROUP BY c.CategoryID, c.CategoryName";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RevenueStatistic stat = new RevenueStatistic();
                    stat.setCategoryId(rs.getInt("CategoryID"));
                    stat.setCategoryName(rs.getString("CategoryName"));
                    stat.setTotalRevenue(rs.getLong("Revenue"));
                    list.add(stat);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getRevenueByCategoryOnMonth: " + e.getMessage());
        }
        return list;
    }

    public long getMonthlyRevenue(int month, int year) {
        String sql = "SELECT SUM(od.Quantity * od.Price) "
                   + "FROM Orders o "
                   + "JOIN OrderDetails od ON o.OrderID = od.OrderID "
                   + "WHERE MONTH(o.OrderedDate) = ? AND YEAR(o.OrderedDate) = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getMonthlyRevenue: " + e.getMessage());
        }
        return 0;
    }
}
