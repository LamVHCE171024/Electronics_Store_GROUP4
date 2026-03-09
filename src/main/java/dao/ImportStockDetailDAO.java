package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.ImportStockDetail;
import model.Product;
import utils.DBContext;

public class ImportStockDetailDAO extends DBContext {

    // Insert nhiều chi tiết (batch), có QuantityLeft
    public int createImportStockDetails(ArrayList<ImportStockDetail> detailList) {
        String query = "INSERT INTO ImportStockDetails (ImportID, ProductID, Quantity, UnitPrice, QuantityLeft) VALUES ";
        ArrayList<String> values = new ArrayList<>();

        for (ImportStockDetail d : detailList) {
            // QuantityLeft = Quantity khi nhập mới
            String value = "(" + d.getIoid() + "," + d.getProduct().getProductId() + ","
                    + d.getQuantity() + "," + d.getUnitPrice() + "," + d.getQuantityLeft() + ")";
            values.add(value);
        }

        for (String v : values) {
            query += v + ",";
        }

        String finalQuery = query.substring(0, query.length() - 1);

        try {
            PreparedStatement ps = conn.prepareStatement(finalQuery);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return 1;
            }

        } catch (SQLException e) {
            System.out.println("createImportStockDetails: " + e.getMessage());
        }

        return 0;
    }

    // Insert 1 chi tiết
    public int createImportStockDetail(ImportStockDetail detail) {
        String query = "INSERT INTO ImportStockDetails (ImportID, ProductID, Quantity, UnitPrice, QuantityLeft) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, detail.getIoid());
            ps.setInt(2, detail.getProduct().getProductId());
            ps.setInt(3, detail.getQuantity());
            ps.setLong(4, detail.getUnitPrice());
            ps.setInt(5, detail.getQuantityLeft());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("createImportStockDetail: " + e.getMessage());
        }

        return 0;
    }

    // Lấy list chi tiết theo ImportID (bao gồm cả QuantityLeft)
    public ArrayList<ImportStockDetail> getDetailsById(int detailId) {
        String query = "SELECT d.*, p.ProductName FROM ImportStockDetails d "
                + "JOIN Products p ON d.ProductID = p.ProductID "
                + "WHERE d.ImportID = ?";
        ArrayList<ImportStockDetail> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, detailId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("ProductID"));
                p.setProductName(rs.getString("ProductName")); // lấy luôn tên
                list.add(new ImportStockDetail(
                        rs.getInt("ImportID"),
                        p,
                        rs.getInt("Quantity"),
                        rs.getLong("UnitPrice"),
                        rs.getInt("QuantityLeft")
                ));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("getDetailsById: " + e.getMessage());
        }
        return list;
    }

    // Update số lượng, giá (nếu có logic update QuantityLeft thì bổ sung field này)
    public int updateDetailById(ImportStockDetail d) {
        String query = "UPDATE ImportStockDetails SET Quantity = ?, UnitPrice = ?, QuantityLeft = ? WHERE ProductID = ? AND ImportID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, d.getQuantity());
            ps.setLong(2, d.getUnitPrice());
            ps.setInt(3, d.getQuantityLeft());
            ps.setInt(4, d.getProduct().getProductId());
            ps.setInt(5, d.getIoid());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateDetailById: " + e.getMessage());
        }
        return 0;
    }

    // Xóa chi tiết
    public int deleteDetailById(int productId, int importId) {
        String query = "DELETE FROM ImportStockDetails WHERE ProductID = ? AND ImportID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            ps.setInt(2, importId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteDetailById: " + e.getMessage());
        }
        return 0;
    }

    // Tính tổng tiền nhập của một phiếu
    public long calculateTotalPrice(int importId) {
        String query = "SELECT SUM(UnitPrice * Quantity) AS TotalPrice FROM ImportStockDetails WHERE ImportID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, importId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getLong("TotalPrice");
            }
        } catch (SQLException e) {
            System.out.println("calculateTotalPrice: " + e.getMessage());
        }
        return 0;
    }

    // Lấy các chi tiết nhập kho hôm nay
    public ArrayList<ImportStockDetail> getImportStocksToday() {
        ArrayList<ImportStockDetail> list = new ArrayList<>();
        String query = "SELECT IOD.*, P.ProductID FROM ImportStockDetails IOD "
                + "JOIN ImportStocks IO ON IOD.ImportID = IO.ImportID "
                + "JOIN Products P ON IOD.ProductID = P.ProductID "
                + "WHERE CAST(IO.ImportDate AS DATE) = CAST(GETDATE() AS DATE) "
                + "ORDER BY P.ProductID ASC";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("ProductID"));
                list.add(new ImportStockDetail(
                        rs.getInt("ImportID"),
                        p,
                        rs.getInt("Quantity"),
                        rs.getLong("UnitPrice"),
                        rs.getInt("QuantityLeft")
                ));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("getImportStocksToday: " + e.getMessage());
        }
        return list;
    }

    // Hàm cập nhật quantityLeft sau khi xuất kho một phần (FIFO)
    public int updateQuantityLeft(int importId, int productId, int quantityLeft) {
        String query = "UPDATE ImportStockDetails SET QuantityLeft = ? WHERE ImportID = ? AND ProductID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, quantityLeft);
            ps.setInt(2, importId);
            ps.setInt(3, productId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateQuantityLeft: " + e.getMessage());
        }
        return 0;
    }

    public boolean updateStockForOrder(List<CartItem> cartItems) {
        String updateImportStock = "UPDATE ImportStockDetails SET QuantityLeft = QuantityLeft - ? WHERE ProductID = ? AND QuantityLeft >= ?";

        try ( PreparedStatement psImport = conn.prepareStatement(updateImportStock)) {
            conn.setAutoCommit(false);
            try {
                for (CartItem item : cartItems) {
                    int quantity = item.getQuantity();
                    int productId = item.getProductID();

                    psImport.setInt(1, quantity);
                    psImport.setInt(2, productId);
                    psImport.setInt(3, quantity);
                    psImport.addBatch();
                }

                int[] updateCounts = psImport.executeBatch();
                // Kiểm tra xem tất cả các bản ghi có được cập nhật thành công không
                for (int count : updateCounts) {
                    if (count == 0) {
                        conn.rollback();
                        return false; // Có ít nhất một sản phẩm không đủ số lượng
                    }
                }
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
