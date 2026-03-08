/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Brand;

import model.CartItem;
import model.Category;
import model.Product;
import model.ProductDetail;
import model.ProductVariant;

import model.Suppliers;
import utils.DBContext;

/**
 *
 * @author HP - Gia Khiêm
 */
public class ProductDAO extends DBContext {

    public List<Product> getProductIsNew() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where p.IsNew = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getProductIsFeatured() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where p.IsFeatured = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getProductIsBestSeller() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where p.IsBestSeller = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getDiscountedProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where p.Discount > 0";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getProductList() {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
               + "p.SupplierID, p.CategoryID, p.BrandID, p.IsFeatured, p.IsBestSeller, "
               + "p.IsNew, p.WarrantyPeriod, p.IsActive, pi.ImageURL "
               + "FROM Products p "
               + "LEFT JOIN ProductImages pi ON p.ProductID = pi.ProductID";

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                if (rs.wasNull()) {
                    supplierId = 0; 
                }
                int categoryId = rs.getInt("CategoryID");
                if (rs.wasNull()) {
                    categoryId = 0;
                }
                int brandId = rs.getInt("BrandID");
                if (rs.wasNull()) {
                    brandId = 0;
                }
                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("IsActive");
                String imageUrl = rs.getString("ImageURL");

                list.add(new Product(productID, productName, description, price, discount, supplierId, categoryId, brandId, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Product> searchProductByName(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "p.SupplierID, p.CategoryID, p.BrandID, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.IsActive, "
                + "pi.ImageURL "
                + "FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.ProductID = pi.ProductID "
                + "WHERE LOWER(p.ProductName) LIKE ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productID = rs.getInt("ProductID");
                    String productName = rs.getString("ProductName");
                    String description = rs.getString("Description");
                    BigDecimal price = rs.getBigDecimal("Price");
                    int discount = rs.getInt("Discount");
                    int stock = rs.getInt("Stock");
                    String status = rs.getString("Status");
                    int supplierId = rs.getInt("SupplierID");
                    if (rs.wasNull()) {
                        supplierId = 0;
                    }
                    int categoryId = rs.getInt("CategoryID");
                    if (rs.wasNull()) {
                        categoryId = 0;
                    }
                    int brandId = rs.getInt("BrandID");
                    if (rs.wasNull()) {
                        brandId = 0;
                    }
                    boolean isFeatured = rs.getBoolean("IsFeatured");
                    boolean isBestSeller = rs.getBoolean("IsBestSeller");
                    boolean isNew = rs.getBoolean("IsNew");
                    int warrantyPeriod = rs.getInt("WarrantyPeriod");
                    boolean isActive = rs.getBoolean("IsActive");
                    String imageUrl = rs.getString("ImageURL");

                    list.add(new Product(productID, productName, description, price, discount, supplierId, categoryId, brandId, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product getProductByID(int productID) {
        Product product = null;
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "p.SupplierID, p.CategoryID, p.BrandID, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.IsActive, "
                + "pi.ImageURL "
                + "FROM Products p "
                + "LEFT JOIN ProductImages pi ON p.ProductID = pi.ProductID "
                + "WHERE p.ProductID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String productName = rs.getString("ProductName");
                    String description = rs.getString("Description");
                    BigDecimal price = rs.getBigDecimal("Price");
                    int discount = rs.getInt("Discount");   
                    int supplierId = rs.getInt("SupplierID");
                    if (rs.wasNull()) {
                        supplierId = 0;
                    }
                    int categoryId = rs.getInt("CategoryID");
                    if (rs.wasNull()) {
                        categoryId = 0;
                    }
                    int brandId = rs.getInt("BrandID");
                    if (rs.wasNull()) {
                        brandId = 0;
                    }
                    boolean isFeatured = rs.getBoolean("IsFeatured");
                    boolean isBestSeller = rs.getBoolean("IsBestSeller");
                    boolean isNew = rs.getBoolean("IsNew");
                    int warrantyPeriod = rs.getInt("WarrantyPeriod");
                    boolean isActive = rs.getBoolean("IsActive");
                    String imageUrl = rs.getString("ImageURL");

                    product = new Product(productID, productName, description, price, discount, supplierId, categoryId, brandId, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

    public boolean activateProduct(int productId) {
        String sql = "UPDATE Products SET IsActive = 1 WHERE ProductID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectProduct(int productId) {
        String sql = "UPDATE Products SET IsActive = 0 WHERE ProductID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected by rejectProduct: " + rowsAffected + " for ProductID: " + productId);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in rejectProduct: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveNotification(String title, String message, int productId) {
        String sql = "INSERT INTO Notifications (Title, Message, Type, IsRead, CreatedAt, RelatedEntityID, RelatedEntityType) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, message);
            ps.setString(3, "ProductUpdate");
            ps.setBoolean(4, false);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, productId);
            ps.setString(7, "Product");
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    <===================================================== GIA KHIÊM ======================================================>
    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        String sql = 
    "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, " +
    "       isd.UnitPrice, isd.Quantity, " +
    "       p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, " +
    "       br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, " +
    "       p.WarrantyPeriod, p.isActive, pro.ImageURL " +
    "FROM Products p " +
    "JOIN ProductImages pro ON p.ProductID = pro.ProductID " +
    "JOIN Categories cate ON cate.CategoryID = p.CategoryID " +
    "JOIN Brands br on br.BrandID = p.BrandID " +
    "JOIN Suppliers sup on sup.SupplierID = p.SupplierID " +
    "OUTER APPLY ( " +
    "    SELECT TOP 1 isd.UnitPrice, isd.Quantity " +
    "    FROM ImportStockDetails isd " +
    "    WHERE isd.ProductID = p.ProductID " +
    "    ORDER BY isd.ImportID DESC " +
    ") isd";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getAllProductInactive() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "

                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "

                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where p.isActive = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getAllProductActive() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "

                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "

                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "where p.isActive = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where p.ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                String supplierName = rs.getString("Name");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");

                product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<ProductDetail> getProductDetailById(int productId) {
        List<ProductDetail> productDetailList = new ArrayList<>();
        String sql = "SELECT p.ProductDetailID, p.ProductID, p.CategoryDetailID, p.AttributeValue, ip.ImageURL1, ip.ImageURL2, ip.ImageURL3, "
                + "ip.ImageURL4 "
                + "FROM ProductDetails p "
                + "LEFT JOIN ImgProductDetails ip ON p.ProductID = ip.ProductID "
                + "where p.ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productDetailId = rs.getInt("ProductDetailID");

                int categoryDetailId = rs.getInt("CategoryDetailID");
                String attributeValue = rs.getString("AttributeValue");
                String imageUrl1 = rs.getString("ImageURL1");
                String imageUrl2 = rs.getString("ImageURL2");
                String imageUrl3 = rs.getString("ImageURL3");
                String imageUrl4 = rs.getString("ImageURL4");
                ProductDetail productDetail = new ProductDetail(productDetailId, productId, categoryDetailId, attributeValue, imageUrl1, imageUrl2, imageUrl3, imageUrl4);

                productDetailList.add(productDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetailList;
    }

    public ProductDetail getOneProductDetailById(int productId) {
        ProductDetail productDetail = null;
        String sql = "SELECT p.ProductDetailID, p.ProductID, p.CategoryDetailID, p.AttributeValue, ip.ImageURL1, ip.ImageURL2, ip.ImageURL3, "
                + "ip.ImageURL4 "
                + "FROM ProductDetails p "
                + "LEFT JOIN ImgProductDetails ip ON p.ProductID = ip.ProductID "
                + "where p.ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productDetailId = rs.getInt("ProductDetailID");

                int categoryDetailId = rs.getInt("CategoryDetailID");
                String attributeValue = rs.getString("AttributeValue");
                String imageUrl1 = rs.getString("ImageURL1");
                String imageUrl2 = rs.getString("ImageURL2");
                String imageUrl3 = rs.getString("ImageURL3");
                String imageUrl4 = rs.getString("ImageURL4");
                productDetail = new ProductDetail(productDetailId, productId, categoryDetailId, attributeValue, imageUrl1, imageUrl2, imageUrl3, imageUrl4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetail;
    }

    public boolean deleteProduct(int productId) {
        String sql = "UPDATE Products SET isActive = 0 WHERE ProductID = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProductWhenCancel(int productId) {
        String sql = "Delete Products WHERE ProductID = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateProductInfo(int id, String productName, BigDecimal price, int suppliers, int category, int brand, boolean isFeatured, boolean isBestSeller,
            boolean pnew, boolean isActive, String img) {
        String sql1 = "UPDATE Products SET ProductName = ?, Price = ?, SupplierID = ?, CategoryID = ?, "
                + "BrandID = ?, IsFeatured = ?, IsBestSeller = ?, IsNew = ?, IsActive = ? WHERE ProductID = ?";

        String sql2 = "UPDATE ProductImages SET ImageURL = ? WHERE ProductID = ?";

        try (
                 PreparedStatement pstmt1 = conn.prepareStatement(sql1);  PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            // Update product
            pstmt1.setString(1, productName);
            pstmt1.setBigDecimal(2, price);
            pstmt1.setInt(3, suppliers);
            pstmt1.setInt(4, category);
            pstmt1.setInt(5, brand);
            pstmt1.setBoolean(6, isFeatured);
            pstmt1.setBoolean(7, isBestSeller);
            pstmt1.setBoolean(8, pnew);
            pstmt1.setBoolean(9, isActive);
            pstmt1.setInt(10, id);

            int rows1 = pstmt1.executeUpdate();

            // Update image
            pstmt2.setString(1, img);
            pstmt2.setInt(2, id);

            int rows2 = pstmt2.executeUpdate();

            return rows1 > 0 && rows2 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProductDetail(int productId, int productDetailID, String proDetail, String url1, String url2, String url3, String url4, String mainUrl) {
        String sql1 = "UPDATE ProductImages SET ImageURL = ? where ProductID = ?";

        String sql2 = "UPDATE ProductDetails SET AttributeValue = ? WHERE ProductDetailID  = ?";

        String sql3 = "UPDATE ImgProductDetails SET ImageURL1 = ?, ImageURL2 = ?, ImageURL3 = ?, ImageURL4 = ? WHERE ProductID = ?";

        try (
                 PreparedStatement pstmt1 = conn.prepareStatement(sql1);  PreparedStatement pstmt2 = conn.prepareStatement(sql2);  PreparedStatement pstmt3 = conn.prepareStatement(sql3)) {

            // Update mainimage
            pstmt1.setString(1, mainUrl);
            pstmt1.setInt(2, productId);

            int rows1 = pstmt1.executeUpdate();

            // Update product detail
            pstmt2.setString(1, proDetail);
            pstmt2.setInt(2, productDetailID);

            int rows2 = pstmt2.executeUpdate();

            // Update img detail
            pstmt3.setString(1, url1);
            pstmt3.setString(2, url2);
            pstmt3.setString(3, url3);
            pstmt3.setString(4, url4);
            pstmt3.setInt(5, productId);

            int rows3 = pstmt3.executeUpdate();

            return rows1 > 0 && rows2 > 0 && rows3 > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM Products";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertProduct(String name, String description, int suppliers, int stock, int category, int brand, boolean isFeatured, boolean isBestSeller,
            boolean isNew, boolean isActive, String url) {
        boolean rowInserted = false;
        int productId = 0;
        String sql = "INSERT INTO Products (ProductName, Description, CategoryID, BrandID, IsFeatured, IsBestSeller, IsNew, isActive, SupplierID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO ProductImages (ProductID, ImageURL) VALUES (?, ?)";

        try ( PreparedStatement stmtProduct = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  PreparedStatement stmtImage = conn.prepareStatement(sql2)) {

            stmtProduct.setString(1, name);
            stmtProduct.setString(2, description);
            stmtProduct.setInt(3, category);
            stmtProduct.setInt(4, brand);
            stmtProduct.setBoolean(5, isFeatured);
            stmtProduct.setBoolean(6, isBestSeller);
            stmtProduct.setBoolean(7, isNew);
            stmtProduct.setBoolean(8, isActive);
            stmtProduct.setInt(9, suppliers);
            rowInserted = stmtProduct.executeUpdate() > 0;

            if (rowInserted) {
                try ( ResultSet generatedKeys = stmtProduct.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        productId = generatedKeys.getInt(1);

                        stmtImage.setInt(1, productId);
                        stmtImage.setString(2, url);

                        stmtImage.executeUpdate();

                        rowInserted = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productId;
    }

    public boolean insertImageProductDetail(String imgaUrl1, String imgaUrl2, String imgaUrl3, String imgaUrl4, int productId) {
        boolean rowInserted = false;
        String sql = "INSERT ImgProductDetails (ImageURL1, ImageURL2, ImageURL3, ImageURL4, ProductID ) VALUES (?, ?, ?, ?, ?)";
        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imgaUrl1);
            stmt.setString(2, imgaUrl2);
            stmt.setString(3, imgaUrl3);
            stmt.setString(4, imgaUrl4);
            stmt.setInt(5, productId);
            rowInserted = stmt.executeUpdate() > 0;
            if (rowInserted) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertProductDetail(int productId, int categoryId, String attributeValue) {
        boolean rowInserted = false;
        String sql = "INSERT INTO ProductDetails (ProductID, CategoryDetailID, AttributeValue) VALUES (?, ?, ?)";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, categoryId);
            stmt.setString(3, attributeValue);

            rowInserted = stmt.executeUpdate() > 0;

            if (rowInserted) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Product> getProductByBrandAndPrice(int brandId, BigDecimal min, BigDecimal max) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, "
                + "p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL, "
                + "isd.UnitPrice, isd.Quantity "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br ON br.BrandID = p.BrandID "
                + "JOIN Suppliers sup ON sup.SupplierID = p.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "WHERE br.BrandID = ? AND p.Price >= ? AND p.Price <= ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandId);
            ps.setBigDecimal(2, min);
            ps.setBigDecimal(3, max);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandIdDB = brandId;
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");

                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandIdDB, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
        } catch (Exception e) {
            System.out.println("Error in getProductByKeyword: " + e.getMessage());
        }
        return list;
    }

    public List<Product> getProductByCategoryAndPrice(int categoryId, BigDecimal min, BigDecimal max) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, "
                + "p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL, "
                + "isd.UnitPrice, isd.Quantity "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br ON br.BrandID = p.BrandID "
                + "JOIN Suppliers sup ON sup.SupplierID = p.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "WHERE cate.CategoryID = ? AND p.Price >= ? AND p.Price <= ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ps.setBigDecimal(2, min);
            ps.setBigDecimal(3, max);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryIdDB = categoryId;
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryIdDB, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
        } catch (Exception e) {
            System.out.println("Error in getProductByKeyword: " + e.getMessage());
        }
        return list;
    }

    public List<Product> getProductByKeyword(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, "
                + "p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL, "
                + "isd.UnitPrice, isd.Quantity "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br ON br.BrandID = p.BrandID "
                + "JOIN Suppliers sup ON sup.SupplierID = p.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "WHERE LOWER(p.ProductName) LIKE ? "
                + "OR LOWER(br.BrandName) LIKE ? "
                + "OR LOWER(cate.CategoryName) LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            String keywordPattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, keywordPattern);
            ps.setString(2, keywordPattern);
            ps.setString(3, keywordPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");
                String supplierName = rs.getString("Name");

                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
        } catch (Exception e) {
            System.out.println("Error in getProductByKeyword: " + e.getMessage());
        }
        return list;
    }

    public List<Product> getProductByBrand(int brandId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, "
                + "cate.CategoryID, cate.CategoryName, "
                + "br.BrandID, br.BrandName, "
                + "p.IsFeatured, p.IsBestSeller, p.IsNew, "
                + "p.WarrantyPeriod, p.isActive, pro.ImageURL "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br ON br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "JOIN Suppliers sup ON sup.SupplierID = p.SupplierID "
                + "WHERE br.BrandID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, brandId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandIdDB = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandIdDB, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Product> getProductByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "isd.UnitPrice, isd.Quantity, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL " 
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "LEFT JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "where cate.CategoryID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                int categoryIdDB = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                String supplierName = rs.getString("Name");
                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                Product product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);

                list.add(product);
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Product getProductByIdHasImportPrice(int productId) {
        Product product = null;
        String sql = "SELECT p.ProductID, p.ProductName, p.Description, p.Price, p.Discount, "
                + "p.SupplierID, sup.Name, cate.CategoryID, cate.CategoryName, br.BrandID, br.BrandName, p.IsFeatured, p.IsBestSeller, p.IsNew, p.WarrantyPeriod, p.isActive, pro.ImageURL, "
                + "isd.UnitPrice, isd.Quantity "
                + "FROM Products p "
                + "JOIN ProductImages pro ON p.ProductID = pro.ProductID "
                + "JOIN Categories cate ON cate.CategoryID = p.CategoryID "
                + "JOIN Brands br on br.BrandID = p.BrandID "
                + "JOIN Suppliers sup on sup.SupplierID = p.SupplierID "
                + "LEFT JOIN ImportStockDetails isd ON isd.ProductID = p.ProductID "
                + "where p.ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("ProductName");
                String description = rs.getString("Description");
                BigDecimal price = rs.getBigDecimal("Price");
                int discount = rs.getInt("Discount");
                int supplierId = rs.getInt("SupplierID");
                String supplierName = rs.getString("Name");
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                int brandId = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                boolean isFeatured = rs.getBoolean("IsFeatured");
                boolean isBestSeller = rs.getBoolean("IsBestSeller");
                boolean isNew = rs.getBoolean("IsNew");
                int warrantyPeriod = rs.getInt("WarrantyPeriod");
                boolean isActive = rs.getBoolean("isActive");
                String imageUrl = rs.getString("ImageURL");

                BigDecimal unitPrice = rs.getBigDecimal("UnitPrice");
                int quatity = rs.getInt("Quantity");

                product = new Product(productId, productName, description, price, discount, supplierId, supplierName, categoryId, categoryName, brandId, brandName, isFeatured, isBestSeller, isNew, warrantyPeriod, isActive, imageUrl, unitPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

//    <===================================================== GIA KHIÊM ======================================================>
    public List<ProductVariant> getAllVariantsForCartItems(List<CartItem> cartItems) {
        List<ProductVariant> list = new ArrayList<>();
        if (cartItems == null || cartItems.isEmpty()) {
            return list;
        }

        StringBuilder sql = new StringBuilder("SELECT VariantID, ProductID, Color, Storage, Quantity, Price, Discount, ImageURL, IsActive ");
        sql.append("FROM ProductVariants WHERE ProductID IN (");
        for (int i = 0; i < cartItems.size(); i++) {
            sql.append("?");
            if (i < cartItems.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        try ( PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < cartItems.size(); i++) {
                ps.setInt(i + 1, cartItems.get(i).getProduct().getProductId());
            }
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int variantId = rs.getInt("VariantID");
                    int productId = rs.getInt("ProductID");
                    String color = rs.getString("Color");
                    String storage = rs.getString("Storage");
                    int quantity = rs.getInt("Quantity");
                    BigDecimal price = rs.getBigDecimal("Price");
                    int discount = rs.getInt("Discount");
                    String imageUrl = rs.getString("ImageURL");
                    boolean isActive = rs.getBoolean("IsActive");

                    ProductVariant variant = new ProductVariant(variantId, productId, color, storage, quantity, price, discount, imageUrl, isActive);
                    list.add(variant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public void increaseStock(int productID, int quantity) {
    String sql = "UPDATE OrderDetails SET Quantity = Quantity - ? WHERE ProductID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, quantity);
        ps.setInt(2, productID);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   //----Tai---//
    public int countLowStockDynamic(int threshold) {
    String sql = "SELECT COUNT(*) AS LowStockCount FROM ( " +
                 "SELECT p.ProductID, " +
                 "ISNULL(SUM(isd.Quantity), 0) - ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS StockLeft " +
                 "FROM Products p " +
                 "LEFT JOIN ImportStockDetails isd ON p.ProductID = isd.ProductID " +
                 "GROUP BY p.ProductID " +
                 ") AS StockView WHERE StockLeft <= ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, threshold); // Ví dụ, threshold = 5
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}


    public Map<Integer, Integer> getAllProductStocks() {
        Map<Integer, Integer> stockMap = new HashMap<>();
        String sql = "SELECT p.ProductID, "
                + "ISNULL(SUM(isd.Quantity), 0) - ISNULL((SELECT SUM(od.Quantity) FROM OrderDetails od WHERE od.ProductID = p.ProductID), 0) AS Stock "
                + "FROM Products p "
                + "LEFT JOIN ImportStockDetails isd ON p.ProductID = isd.ProductID "
                + "GROUP BY p.ProductID";
        try ( PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                int stock = rs.getInt("Stock");
                stockMap.put(productId, stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockMap;
    }

}
