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
import model.OrderStatus;
import utils.DBContext;

/**
 *
 * @author VinhNTCE181630
 */
public class OrderStatusDAO extends DBContext {


    public List<OrderStatus> getAllStatuses() {
        List<OrderStatus> list = new ArrayList<>();
        String sql = "SELECT StatusID, StatusName FROM OrderStatus"; // bảng OrderStatus với 2 cột

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderStatus status = new OrderStatus(
                        rs.getInt("StatusID"),
                        rs.getString("StatusName")
                );
                list.add(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
