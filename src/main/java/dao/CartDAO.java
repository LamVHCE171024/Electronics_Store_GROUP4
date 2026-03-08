package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import utils.DBContext;

public class CartDAO extends DBContext {

    public CartDAO() {
        super();
    }

    public List<CartItem> getCartItemsByAccountId(int accountId) {
        List<CartItem> cartItems = new ArrayList<>();
        // ⭐ Sửa lại SQL - lấy hình ảnh đầu tiên từ ProductImages
        String sql = "SELECT ci.CartItemID, ci.CartID, ci.ProductID, ci.Quantity, ci.AddedAt, "
                + "p.ProductID, p.ProductName, p.Price, p.Discount, p.isActive, "
                + "p.CategoryID, "
                + "(SELECT TOP 1 pi.ImageURL FROM ProductImages pi WHERE pi.ProductID = p.ProductID) AS ImageURL " // Lấy hình ảnh đầu tiên
                + "FROM CartItems ci "
                + "JOIN Cart c ON ci.CartID = c.CartID "
                + "JOIN Products p ON ci.ProductID = p.ProductID "
                + "WHERE c.AccountID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng CartItem
                    CartItem item = new CartItem();
                    item.setCartItemID(rs.getInt("CartItemID"));
                    item.setCartID(rs.getInt("CartID"));
                    item.setProductID(rs.getInt("ProductID"));
                    item.setQuantity(rs.getInt("Quantity"));

                    Timestamp addedAt = rs.getTimestamp("AddedAt");
                    if (addedAt != null) {
                        item.setAddedAt(new java.util.Date(addedAt.getTime()));
                    }

                    // ⭐ Tạo đối tượng Product với đầy đủ thông tin
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setDiscount(rs.getInt("Discount"));

                    product.setIsActive(rs.getBoolean("isActive"));
                    product.setCategoryId(rs.getInt("CategoryID"));

                    String imageUrl = rs.getString("ImageURL");
                    product.setImageUrl(imageUrl);

                    item.setProduct(product);
                    cartItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQLException in getCartItemsByAccountId: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("🔍 Total CartItems found: " + cartItems.size());
        return cartItems;
    }

    public CartItem getCartItemById(int cartItemId) {
        String sql = "SELECT ci.CartItemID, ci.CartID, ci.ProductID, ci.Quantity, ci.AddedAt, "
                + "p.ProductID, p.ProductName, p.Price, p.Discount, p.isActive, "
                + "p.CategoryID, "
                + "(SELECT TOP 1 pi.ImageURL FROM ProductImages pi WHERE pi.ProductID = p.ProductID) AS ImageURL "
                + "FROM CartItems ci "
                + "JOIN Products p ON ci.ProductID = p.ProductID "
                + "WHERE ci.CartItemID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartItem item = new CartItem();
                    item.setCartItemID(rs.getInt("CartItemID"));
                    item.setCartID(rs.getInt("CartID"));
                    item.setProductID(rs.getInt("ProductID"));
                    item.setQuantity(rs.getInt("Quantity"));

                    Timestamp addedAt = rs.getTimestamp("AddedAt");
                    if (addedAt != null) {
                        item.setAddedAt(new java.util.Date(addedAt.getTime()));
                    }

                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setDiscount(rs.getInt("Discount"));
                    product.setIsActive(rs.getBoolean("isActive"));
                    product.setCategoryId(rs.getInt("CategoryID"));
                    product.setImageUrl(rs.getString("ImageURL"));

                    item.setProduct(product);
                    return item;
                } else {
                    Logger.getLogger(CartDAO.class.getName()).log(Level.WARNING, "No CartItem found for ID: {0}", cartItemId);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, "Error fetching CartItem: {0}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM CartItems WHERE CartItemID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMultipleCartItems(List<String> cartItemIds) {
        if (cartItemIds == null || cartItemIds.isEmpty()) {
            return false;
        }

        String sql = "DELETE FROM CartItems WHERE CartItemID IN ("
                + String.join(",", new String[cartItemIds.size()]).replaceAll("[^,]+", "?") + ")";
        try {
            conn.setAutoCommit(false);
            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < cartItemIds.size(); i++) {
                    ps.setInt(i + 1, Integer.parseInt(cartItemIds.get(i)));
                }
                int rowsAffected = ps.executeUpdate();
                conn.commit();
                return rowsAffected == cartItemIds.size();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateCartItemQuantity(int cartItemId, int quantity) {
        String sql = "UPDATE CartItems SET Quantity = ? WHERE cartItemId = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            System.out.println("[CartDAO] updateCartItemQuantity SQL: " + sql);
            System.out.println("[CartDAO] Params: quantity=" + quantity + ", cartItemId=" + cartItemId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("[CartDAO] Rows updated: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception ex) {
            System.err.println("[CartDAO] Error updateCartItemQuantity: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateCartItemVariant(int cartItemId, Integer variantId) {
        String sql = "UPDATE CartItems SET VariantID = ? WHERE CartItemID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            if (variantId == null || variantId == 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, variantId);
            }
            ps.setInt(2, cartItemId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductStock(int productId, int newStock) {
        String sql = "UPDATE Products SET Stock = ? WHERE ProductID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVariantStock(int variantId, int newStock) {
        String sql = "UPDATE ProductVariants SET Quantity = ? WHERE VariantID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, variantId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cart getCartByAccountId(int accountId) throws SQLException {
        Cart cart = null;
        String sql = "SELECT * FROM Cart WHERE AccountID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cart = new Cart();
                    cart.setCartId(rs.getInt("CartID"));
                    cart.setAccountId(rs.getInt("AccountID"));
                    cart.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                    cart.setCartItems(getCartItemsByCartId(cart.getCartId()));
                }
            }
        }
        return cart;
    }

    public void createCart(Cart cart) throws SQLException {
        String sql = "INSERT INTO Cart (AccountID, CreatedAt) VALUES (?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cart.getAccountId());
            ps.setTimestamp(2, Timestamp.valueOf(cart.getCreatedAt()));
            ps.executeUpdate();
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cart.setCartId(rs.getInt(1));
                }
            }
        }
    }

    public boolean addCartItem(CartItem item) throws SQLException {
        String sql = "INSERT INTO CartItems (CartID, ProductID, Quantity) VALUES (?, ?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getCartID());
            ps.setInt(2, item.getProductID());
            ps.setInt(3, item.getQuantity());
            ps.executeUpdate();
            return true;
        }
    }

    public CartItem getCartItemByProductAndCart(int cartId, int productId) throws SQLException {
        String sql = "SELECT * FROM CartItems WHERE CartID = ? AND ProductID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartItem item = new CartItem();
                    item.setCartItemID(rs.getInt("CartItemID"));
                    item.setCartID(rs.getInt("CartID"));
                    item.setProductID(rs.getInt("ProductID"));
                    item.setQuantity(rs.getInt("Quantity"));
                    return item;
                }
            }
        }
        return null;
    }

    public List<CartItem> getCartItemsByCartId(int cartId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.CartItemID, ci.CartID, ci.ProductID, ci.Quantity, "
                + "p.ProductID, p.ProductName, p.Price, p.Discount, p.isActive "
                + "FROM CartItems ci "
                + "JOIN Products p ON ci.ProductID = p.ProductID "
                + "WHERE ci.CartID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem();
                    item.setCartItemID(rs.getInt("CartItemID"));
                    item.setCartID(rs.getInt("CartID"));
                    item.setProductID(rs.getInt("ProductID"));
                    item.setQuantity(rs.getInt("Quantity"));

                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setDiscount(rs.getInt("Discount"));

                    product.setIsActive(rs.getBoolean("isActive"));
                    item.setProduct(product);

                    cartItems.add(item);
                }
            }
        }
        return cartItems;
    }

    public void processCartForCheckout(int accountId, int orderId) throws SQLException {
        List<CartItem> cartItems = getCartItemsByAccountId(accountId);
        if (cartItems != null && !cartItems.isEmpty()) {
            String sql = "INSERT INTO OrderDetails (OrderID, ProductID, VariantID, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false);
                try {
                    for (CartItem item : cartItems) {
                        ps.setInt(1, orderId);
                        ps.setInt(2, item.getProductID());
                        ps.setObject(3, item.getVariantID());
                        ps.setInt(4, item.getQuantity());
                        BigDecimal price = item.getProduct().getPrice().multiply(BigDecimal.valueOf(1 - item.getProduct().getDiscount() / 100.0));
                        ps.setBigDecimal(5, price);
                        ps.addBatch();

                    }
                    ps.executeBatch();
                    // Xóa giỏ hàng sau khi checkout
                    deleteCartItemsByAccountId(accountId);
                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        }
    }

    public boolean deleteCartItemsByAccountId(int accountId) {
        String sql = "DELETE FROM CartItems WHERE CartID IN (SELECT CartID FROM Cart WHERE AccountID = ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMultipleCartItemsByIntegerIds(List<Integer> cartItemIds) {
        if (cartItemIds == null || cartItemIds.isEmpty()) {
            return false;
        }

        String sql = "DELETE FROM CartItems WHERE CartItemID IN ("
                + String.join(",", new String[cartItemIds.size()]).replaceAll("[^,]+", "?") + ")";
        try {
            conn.setAutoCommit(false);
            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < cartItemIds.size(); i++) {
                    ps.setInt(i + 1, cartItemIds.get(i));
                }
                int rowsAffected = ps.executeUpdate();
                conn.commit();
                return rowsAffected == cartItemIds.size();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getProductQuantityLeft(int productId, int requiredQuantity) throws SQLException {
        String sql = "SELECT SUM(QuantityLeft) AS TotalQuantityLeft FROM ImportStockDetails WHERE ProductID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int totalQuantityLeft = rs.getInt("TotalQuantityLeft");
                    return totalQuantityLeft >= requiredQuantity;
                }
            }
        }
        return false; // Return false if product not found or no quantity left
    }
}
