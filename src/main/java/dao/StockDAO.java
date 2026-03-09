/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Stock;
import utils.DBContext;

/**
 *
 * @author HP
 */
public class StockDAO extends DBContext {

    public List<Stock> getAllStocks() throws SQLException {
        List<Stock> list = new ArrayList<>();
        String sql
                = "SELECT \n"
                + "    io.ImportID,\n"
                + "    s.FullName AS StaffName,\n"
                + "    sup.Name AS SupplierName,\n"
                + "    io.ImportDate,\n"
                + "    io.TotalAmount,\n"
                + "    p.ProductID,\n"
                + "    p.ProductName AS ProductName,\n"
                + "    b.BrandName AS BrandName,\n"
                + "    c.CategoryName AS CategoryName,\n"
                + "    iod.Quantity,\n"
                + "    iod.UnitPrice AS ImportPrice,\n"
                + "    p.Price AS NewRetailPrice, \n"
                + "    (p.Price - iod.UnitPrice) AS ProfitMargin,\n"
                + "    ROUND(((p.Price - iod.UnitPrice) * 100.0 / iod.UnitPrice), 2) AS ProfitPercentage\n"
                + "FROM ImportStocks io\n"
                + "JOIN Staff s ON io.StaffID = s.StaffID\n"
                + "JOIN Suppliers sup ON io.SupplierID = sup.SupplierID\n"
                + "JOIN ImportStockDetails iod ON io.ImportID = iod.ImportID\n"
                + "JOIN Products p ON iod.ProductID = p.ProductID\n"
                + "JOIN Brands b ON p.BrandID = b.BrandID\n"
                + "JOIN Categories c ON p.CategoryID = c.CategoryID\n"
                + "ORDER BY io.ImportDate DESC, p.ProductID;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Stock stock = new Stock();
                stock.setIOID(rs.getInt("IOID"));
                stock.setStaffName(rs.getString("staffName"));
                stock.setImportDate(rs.getTimestamp("importDate"));
                stock.setTotalCost(rs.getLong("totalCost"));
                stock.setLastModify(rs.getTimestamp("lastModify"));
                stock.setProductName(rs.getString("productName"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setImportPrice(rs.getLong("importPrice"));
                stock.setProductId(rs.getInt("productId"));
                stock.setBrandName(rs.getString("brandName"));
                stock.setCategoryName(rs.getString("categoryName"));
                stock.setRetailPrice(rs.getLong("retailPrice"));
                stock.setProfitMargin(rs.getLong("profitMargin"));

                list.add(stock);
            }

        } catch (SQLException E) {
            System.out.println(E);
        }
        return list;
    }

}
