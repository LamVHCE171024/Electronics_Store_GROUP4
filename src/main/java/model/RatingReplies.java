/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author VinhNTCE181630
 */
public class RatingReplies {
    private int replyID;
    private int staffID;
    private int rateID;
    private String answer;
    private boolean isRead; 

    public RatingReplies() {
    }

    public RatingReplies(int replyID, int staffID, int rateID, String answer, boolean isRead) {
        this.replyID = replyID;
        this.staffID = staffID;
        this.rateID = rateID;
        this.answer = answer;
        this.isRead = isRead;
    }
    
    public int getReplyID() {
        return replyID;
    }

    public void setReplyID(int replyID) {
        this.replyID = replyID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getRateID() {
        return rateID;
    }

    public void setRateID(int rateID) {
        this.rateID = rateID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    
}

