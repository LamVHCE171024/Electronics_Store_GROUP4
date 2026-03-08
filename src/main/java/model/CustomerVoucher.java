package model;

import java.util.Date;

public class CustomerVoucher {

    private int customerId;
    private int voucherId;
    private Date expirationDate;
    private int quantity;
     private Voucher voucher;

    public CustomerVoucher() {
    }

    public CustomerVoucher(int customerId, int voucherId, Date expirationDate, int quantity, Voucher voucher) {
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.voucher = voucher;
    }
     // Dùng khi thêm mới
    public CustomerVoucher(int customerId, int voucherId, Date expirationDate, int quantity) {
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    

    // Getter, Setter
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
