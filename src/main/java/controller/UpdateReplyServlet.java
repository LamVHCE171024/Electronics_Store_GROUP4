/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RatingRepliesDAO;
import model.RatingReplies;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 *
 */
@WebServlet(name = "UpdateReplyServlet", urlPatterns = {"/UpdateReply"})
public class UpdateReplyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RatingRepliesDAO rrDAO = new RatingRepliesDAO();
        String answerUpdate = request.getParameter("answer");
        String replyID_raw = request.getParameter("replyID");
        System.out.println("UpdateReplyServlet received: replyID = " + replyID_raw + ", answer = " + answerUpdate);

        try {
            int replyID = Integer.parseInt(replyID_raw);        
            if (replyID > 0 && answerUpdate != null && !answerUpdate.trim().isEmpty()) {
                RatingReplies reply = rrDAO.getReplyByRepyID(replyID);
                if (reply == null) {
                    System.out.println("Reply not found for ID = " + replyID);
                    response.getWriter().write("Failed");
                    return;
                }
                int result = rrDAO.UpdateReply(reply, answerUpdate);
                System.out.println("Update result: " + result);
                if (result > 0) {
                    response.getWriter().write("Success");
                } else {
                    response.getWriter().write("Failed");
                }
            } else {
                response.getWriter().write("Invalid Input");
                System.out.println("Invalid input detected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
