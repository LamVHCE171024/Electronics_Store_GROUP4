/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pc
 */
public class Account {

    private int accountID;
    private String email;
    private String passwordHash;

    private boolean isActive;
    private boolean emailVerified;
    private String profileImageURL;

    private int roleID;

    public Account() {
    }

    public Account(int accountID, String email, String passwordHash, int roleID) {
        this.accountID = accountID;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleID = roleID;
    }

    public Account(int accountID, String email, boolean isActive, int roleID) {
        this.accountID = accountID;
        this.email = email;
        this.isActive = isActive;
        this.roleID = roleID;
    }

    public Account(String email, String passwordHash, int i, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

}
