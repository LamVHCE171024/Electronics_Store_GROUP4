/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.LocalDateTime;

/**
 *
 * @author pc
 */
public class OTPManager {

    private int otpCode;
    private LocalDateTime expiryTime;
    private int resendCount;

    public OTPManager(int otpCode, int expireMinutes) {
        this.otpCode = otpCode;
        this.expiryTime = LocalDateTime.now().plusMinutes(expireMinutes);
        this.resendCount = 1;
    }

    public int getOtpCode() {
        return otpCode;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }

    public int getResendCount() {
        return resendCount;
    }

    public boolean canResend() {
        return resendCount < 3;
    }

    public void incrementResend() {
        this.resendCount++;
        this.expiryTime = LocalDateTime.now().plusMinutes(5); // reset thời hạn nếu gửi lại
    }
}
