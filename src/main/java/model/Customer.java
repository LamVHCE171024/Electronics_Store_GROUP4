/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author pc
 */
public class Customer {
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private Date createAt;
    private boolean isActive;
    private String birthDay;
    private String gender;
    private String address;

 
    public Customer() {
    }

    public Customer(int id, String email, String fullName, String phone, Date createAt, boolean isActive) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.createAt = createAt;
        this.isActive = isActive;
    }

    public Customer(int id, String email, String fullName, String phone, boolean isActive, String birthDay, String gender) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.isActive = isActive;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public Customer(int id, String email, String fullName, String phone, boolean active, String birthday, String gender, String address) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.isActive = isActive;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
       public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
