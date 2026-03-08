package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.InventoryStatistic;
import utils.DBContext;

public class InventoryStatisticDAO extends DBContext {

    public ArrayList<InventoryStatistic> getAllInventoryBatch() {
        ArrayList<InventoryStatistic> list = new ArrayList<>();

        String soldSql = "SELECT ProductID, ISNULL(SUM(Quantity), 0) AS SoldQuantity FROM OrderDetails GROUP BY ProductID";
        Map<Integer, Integer> soldMap = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(soldSql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                soldMap.put(rs.getInt("ProductID"), rs.getInt("SoldQuantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        String sql = "SELECT "
                + "isd.ImportID, " 
                + "c.CategoryName, b.BrandName, p.ProductName, "
                + "s.Name AS SupplierName, isd.Quantity AS ImportQuantity, "
                + "istk.ImportDate, isd.UnitPrice AS ImportPrice, "
                + "p.ProductID, c.CategoryID "
                + "FROM ImportStockDetails isd "
                + "JOIN ImportStocks istk ON isd.ImportID = istk.ImportID "
                + "LEFT JOIN Products p ON isd.ProductID = p.ProductID "
                + "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID "
                + "LEFT JOIN Brands b ON p.BrandID = b.BrandID "
                + "LEFT JOIN Suppliers s ON p.SupplierID = s.SupplierID "
                + "ORDER BY p.ProductID, istk.ImportDate ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int lastProductId = -1;
            int soldRemain = 0;

            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                if (productId != lastProductId) {
                    soldRemain = soldMap.getOrDefault(productId, 0);
                    lastProductId = productId;
                }

                int importQuantity = rs.getInt("ImportQuantity");
                int soldQty = Math.min(importQuantity, soldRemain);
                int stockRemain = importQuantity - soldQty;
                soldRemain -= soldQty;

                InventoryStatistic stat = new InventoryStatistic();
                stat.setImportId(rs.getInt("ImportID")); // Set ImportID
                stat.setCategoryName(rs.getString("CategoryName"));
                stat.setBrandName(rs.getString("BrandName"));
                stat.setFullName(rs.getString("ProductName"));
                stat.setImportQuantity(importQuantity);
                stat.setSoldQuantity(soldQty);
                stat.setStockQuantity(stockRemain);
                stat.setSupplierName(rs.getString("SupplierName"));
                stat.setImportDate(rs.getDate("ImportDate"));
                stat.setProductImportPrice(rs.getBigDecimal("ImportPrice"));
                stat.setProductId(productId);
                stat.setCategoryId(rs.getInt("CategoryID"));
                list.add(stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<InventoryStatistic> getAllProductStock() {
        ArrayList<InventoryStatistic> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, c.CategoryName, b.BrandName, "
               + "SUM(isd.Quantity) AS TotalImported, "
               + "ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS TotalSold, "
               + "SUM(isd.Quantity) - ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS Stock, "
               + "MAX(isd.UnitPrice) AS LastImportPrice, "
               + "s.Name AS SupplierName, c.CategoryID "
               + "FROM Products p "
               + "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID "
               + "LEFT JOIN Brands b ON p.BrandID = b.BrandID "
               + "LEFT JOIN Suppliers s ON p.SupplierID = s.SupplierID "
               + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
               + "GROUP BY p.ProductID, p.ProductName, c.CategoryName, b.BrandName, s.Name, c.CategoryID";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                InventoryStatistic stat = new InventoryStatistic();
                stat.setProductId(rs.getInt("ProductID"));
                stat.setFullName(rs.getString("ProductName"));
                stat.setCategoryName(rs.getString("CategoryName"));
                stat.setBrandName(rs.getString("BrandName"));
                stat.setImportQuantity(rs.getInt("TotalImported"));
                stat.setSoldQuantity(rs.getInt("TotalSold"));
                stat.setStockQuantity(rs.getInt("Stock"));
                stat.setProductImportPrice(rs.getBigDecimal("LastImportPrice"));
                stat.setSupplierName(rs.getString("SupplierName"));
                stat.setCategoryId(rs.getInt("CategoryID"));
                list.add(stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<InventoryStatistic> searchInventory(String keyword) {
        ArrayList<InventoryStatistic> list = new ArrayList<>();
        String sql =
                "SELECT "
                + "  c.CategoryName, "
                + "  b.BrandName, "
                + "  p.ProductName, "
                + "  ISNULL(SUM(isd.Quantity), 0) AS TotalImported, "
                + "  ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS TotalSold, "
                + "  ISNULL(SUM(isd.Quantity), 0) - ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS Stock, "
                + "  s.Name AS SupplierName, "
                + "  (SELECT TOP 1 istk.ImportDate "
                + "      FROM ImportStocks istk "
                + "      JOIN ImportStockDetails isd2 ON istk.ImportID = isd2.ImportID "
                + "      WHERE isd2.ProductID = p.ProductID "
                + "      ORDER BY istk.ImportDate DESC) AS LastImportDate, "
                + "  (SELECT TOP 1 isd2.UnitPrice "
                + "      FROM ImportStockDetails isd2 "
                + "      JOIN ImportStocks istk2 ON istk2.ImportID = isd2.ImportID "
                + "      WHERE isd2.ProductID = p.ProductID "
                + "      ORDER BY istk2.ImportDate DESC) AS LastImportPrice, "
                + "  p.ProductID, c.CategoryID "
                + "FROM Products p "
                + "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID "
                + "LEFT JOIN Brands b ON p.BrandID = b.BrandID "
                + "LEFT JOIN Suppliers s ON p.SupplierID = s.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "WHERE p.ProductName LIKE ? OR b.BrandName LIKE ? OR c.CategoryName LIKE ? "
                + "GROUP BY c.CategoryName, b.BrandName, p.ProductName, s.Name, p.ProductID, c.CategoryID";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchValue = "%" + keyword + "%";
            ps.setString(1, searchValue);
            ps.setString(2, searchValue);
            ps.setString(3, searchValue);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventoryStatistic stat = new InventoryStatistic();
                    stat.setCategoryName(rs.getString("CategoryName"));
                    stat.setBrandName(rs.getString("BrandName"));
                    stat.setFullName(rs.getString("ProductName"));
                    stat.setImportQuantity(rs.getInt("TotalImported"));
                    stat.setSoldQuantity(rs.getInt("TotalSold"));
                    stat.setStockQuantity(rs.getInt("Stock"));
                    stat.setSupplierName(rs.getString("SupplierName"));
                    stat.setImportDate(rs.getDate("LastImportDate"));
                    stat.setProductImportPrice(rs.getBigDecimal("LastImportPrice"));
                    stat.setProductId(rs.getInt("ProductID"));
                    stat.setCategoryId(rs.getInt("CategoryID"));
                    list.add(stat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<InventoryStatistic> getInventoryByCategory(int categoryId) {
        ArrayList<InventoryStatistic> list = new ArrayList<>();
        String sql =
                "SELECT "
                + "  c.CategoryName, "
                + "  b.BrandName, "
                + "  p.ProductName, "
                + "  ISNULL(SUM(isd.Quantity), 0) AS TotalImported, "
                + "  ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS TotalSold, "
                + "  ISNULL(SUM(isd.Quantity), 0) - ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS Stock, "
                + "  s.Name AS SupplierName, "
                + "  (SELECT TOP 1 istk.ImportDate "
                + "      FROM ImportStocks istk "
                + "      JOIN ImportStockDetails isd2 ON istk.ImportID = isd2.ImportID "
                + "      WHERE isd2.ProductID = p.ProductID "
                + "      ORDER BY istk.ImportDate DESC) AS LastImportDate, "
                + "  (SELECT TOP 1 isd2.UnitPrice "
                + "      FROM ImportStockDetails isd2 "
                + "      JOIN ImportStocks istk2 ON istk2.ImportID = isd2.ImportID "
                + "      WHERE isd2.ProductID = p.ProductID "
                + "      ORDER BY istk2.ImportDate DESC) AS LastImportPrice, "
                + "  p.ProductID, c.CategoryID "
                + "FROM Products p "
                + "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID "
                + "LEFT JOIN Brands b ON p.BrandID = b.BrandID "
                + "LEFT JOIN Suppliers s ON p.SupplierID = s.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "WHERE c.CategoryID = ? "
                + "GROUP BY c.CategoryName, b.BrandName, p.ProductName, s.Name, p.ProductID, c.CategoryID";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InventoryStatistic stat = new InventoryStatistic();
                    stat.setCategoryName(rs.getString("CategoryName"));
                    stat.setBrandName(rs.getString("BrandName"));
                    stat.setFullName(rs.getString("ProductName"));
                    stat.setImportQuantity(rs.getInt("TotalImported"));
                    stat.setSoldQuantity(rs.getInt("TotalSold"));
                    stat.setStockQuantity(rs.getInt("Stock"));
                    stat.setSupplierName(rs.getString("SupplierName"));
                    stat.setImportDate(rs.getDate("LastImportDate"));
                    stat.setProductImportPrice(rs.getBigDecimal("LastImportPrice"));
                    stat.setProductId(rs.getInt("ProductID"));
                    stat.setCategoryId(rs.getInt("CategoryID"));
                    list.add(stat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
