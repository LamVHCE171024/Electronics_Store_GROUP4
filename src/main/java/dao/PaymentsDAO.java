package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import utils.DBContext;

public class PaymentsDAO extends DBContext {

    public boolean addPayment(int orderId, long amount, String paymentMethod, String paymentStatus) {
        String sql = "INSERT INTO Payments (OrderID, PaymentMethod, PaymentStatus, PaidDate, Amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setString(2, paymentMethod);
            ps.setString(3, paymentStatus);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setBigDecimal(5, BigDecimal.valueOf(amount));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}