/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Lam
 */
public class Staff {

    private int staffID;
    private String email;
    private String fullName;
    private String phone;
    private String gender;

    private int accountId;
    private Date birthDay;
    private String position;
    private Date hiredDate;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Staff() {
    }

    public Staff(int staffID, String email, String fullName, Date hiredDate) {
        this.staffID = staffID;
        this.email = email;
        this.fullName = fullName;
        this.hiredDate = hiredDate;
    }

    public Staff(int staffID, String email, String fullName, String phone, Date hiredDate, Date birthDay, String gender) {
        this.staffID = staffID;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.hiredDate = hiredDate;
        this.birthDay = birthDay;
        this.gender = gender;
    }
    
     public Staff(int staffID, String email, String fullName, String phone, Date hiredDate,String position, Date birthDay, String gender) {
        this.staffID = staffID;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.hiredDate = hiredDate;
        this.position = position;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}