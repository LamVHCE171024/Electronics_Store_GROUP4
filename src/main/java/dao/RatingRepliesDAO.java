/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.DBContext;
import model.RatingReplies;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class RatingRepliesDAO extends DBContext {

    public List<RatingReplies> getAllRatingRepliesByProduct(int productId) {
        List<RatingReplies> list = new ArrayList<>();
        String query = "SELECT rr.* FROM RatingReplies rr JOIN ProductRatings pr ON rr.RateID = pr.RateID WHERE pr.ProductID = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, productId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                RatingReplies rr = new RatingReplies(
                        rs.getInt("ReplyID"),
                        rs.getInt("StaffID"),
                        rs.getInt("RateID"),
                        rs.getString("Answer"),
                        rs.getBoolean("IsRead")
                );
                list.add(rr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RatingReplies> getAllRatingRepliesByRateID(int rateId) {
        List<RatingReplies> list = new ArrayList<>();
        String query = "select * from RatingReplies where RateID =?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, rateId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                RatingReplies rr = new RatingReplies(
                        rs.getInt("ReplyID"),
                        rs.getInt("StaffID"),
                        rs.getInt("RateID"),
                        rs.getString("Answer"),
                        rs.getBoolean("IsRead")
                );
                list.add(rr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addRatingReply(int staffId, int rateId, String answer) {
        int count = 0;
        String query = "INSERT INTO RatingReplies (StaffID, RateID, Answer, IsRead) VALUES (?, ?, ?, 0)";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, staffId);
            pre.setInt(2, rateId);
            pre.setString(3, answer);
            count = pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<RatingReplies> getCustomerReplies(int customerID) {
        List<RatingReplies> list = new ArrayList<>();
//        String query = "SELECT r.* FROM RatingReplies r JOIN ProductRatings pr ON r.rateID = pr.rateID WHERE pr.customerID = ? AND r.isRead = 0";
        String query = "SELECT rr.* FROM RatingReplies rr \n"
                + "JOIN ProductRatings pr ON rr.RateID = pr.RateID\n"
                + "WHERE pr.CustomerID =? ORDER BY pr.CreatedDate DESC";
        try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new RatingReplies(rs.getInt("ReplyID"),
                        rs.getInt("StaffID"),
                        rs.getInt("RateID"),
                        rs.getString("Answer"),
                        rs.getBoolean("IsRead")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public RatingReplies getReplyByRepyID(int replyID) {
        List<RatingReplies> list = new ArrayList<>();
        RatingReplies r = null;
//        String query = "SELECT r.* FROM RatingReplies r JOIN ProductRatings pr ON r.rateID = pr.rateID WHERE pr.customerID = ? AND r.isRead = 0";
        String query = "SELECT rr.* FROM RatingReplies rr WHERE rr.ReplyID =?";
        try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, replyID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                r = new RatingReplies(rs.getInt("ReplyID"),
                        rs.getInt("StaffID"),
                        rs.getInt("RateID"),
                        rs.getString("Answer"),
                        rs.getBoolean("IsRead"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    public int UpdateReply(RatingReplies reply, String Answer) {
        String query = "UPDATE RatingReplies SET Answer = ? WHERE ReplyID = ?";
        int result = 0;

        try {
            if (conn == null) {
                System.out.println("[ERROR] conn is null in UpdateReply!");
                return 0;
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Answer);
            stmt.setInt(2, reply.getReplyID());

            result = stmt.executeUpdate();
            System.out.println("[DAO] Rows updated: " + result);

        } catch (Exception e) {
            System.out.println("[ERROR] UpdateReply failed: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public boolean markReplyAsRead(int ReplyID) {
        String query = "UPDATE RatingReplies SET IsRead = 1 WHERE ReplyID = ?";

        try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ReplyID);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean DeleteRatingReply(int ReplyID) {
        String query = "DELETE FROM RatingReplies WHERE ReplyID = ?";

        try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ReplyID);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
     public void updateisReadComment(int rateID) {
        String query = "Update ProductRatings SET IsRead = 1  WHERE RateID =?";
        try {
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, rateID);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RatingRepliesDAO r = new RatingRepliesDAO();
        System.out.println("RATING REPLY_______________________");
        System.out.println(r.getReplyByRepyID(1).getAnswer());
    }
}
