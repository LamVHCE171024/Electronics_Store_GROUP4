package utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private static final Map<String, OTPManager> otpMap = new HashMap<>();

    public static int generateVerificationCode() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

   public static boolean sendOTPEmail(String recipientEmail, int code, String type) throws UnsupportedEncodingException {
    final String senderEmail = System.getenv("EMAIL_USERNAME");
    final String senderPassword = System.getenv("EMAIL_PASSWORD");

    if (senderEmail == null || senderPassword == null) {
        System.err.println("❌ Lỗi: Không tìm thấy thông tin đăng nhập email.");
        return false;
    }

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    });

    try {
        String subject = "";
        String htmlContent = "";

        String contactBlock = "<div style='display: flex; align-items: center; margin-top: 30px;'>"
                + "  <div style='margin-right: 20px;'>"
                + "    <img src='https://drive.google.com/uc?export=view&id=1L_7sTrEP_dT2hM2oQyloOaZBRKJ8HjLB' alt='TShop' width='80'/>"
                + "  </div>"
                + "  <div style='font-size:13px; line-height: 1.5;'>"
                + "    <div><span style='color:orange;'>T MOBILE</span></div>"
                + "    <div><span style='color:green;'>| 600, Nguyen Van Cu Street, An Binh Ward, Ninh Kieu District, Can Tho City</span></div>"
                + "    <div><span style='color:blue;'>| Hotline: 02927.30.30.88 | Email: <a href='mailto:erd.ct@fe.edu.vn'>erd.ct@fe.edu.vn</a></span></div>"
                + "  </div>"
                + "</div>";

        if ("REGISTER".equals(type)) {
            subject = "TMobile - Email Verification";
            htmlContent = "<h3>Welcome to TMobile!</h3>"
                    + "<p>Your registration verification code is: <strong>" + code + "</strong></p>"
                    + "<p>Please enter this code to complete your registration.</p>"
                    + contactBlock;

        } else if ("RESET_PASSWORD".equals(type)) {
            subject = "TMobile - Password Reset Code";
            htmlContent = "<h3>Reset Your Password</h3>"
                    + "<p>Your password reset OTP is: <strong>" + code + "</strong></p>"
                    + "<p>This code is valid for 5 minutes. If you did not request a password reset, ignore this email.</p>"
                    + contactBlock;
        }

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail, "TMobile Support"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setContent(htmlContent, "text/html; charset=UTF-8");

        Transport.send(message);
        System.out.println("✅ Email sent successfully to " + recipientEmail);
        return true;
    } catch (MessagingException e) {
        System.err.println("❌ Lỗi khi gửi email: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

    // Gửi email thông báo đăng ký thành công
    public static boolean sendSuccessEmail(String recipientEmail) throws UnsupportedEncodingException {
        final String senderEmail = System.getenv("EMAIL_USERNAME");
        final String senderPassword = System.getenv("EMAIL_PASSWORD");

        if (senderEmail == null || senderPassword == null) {
            System.err.println("❌ Lỗi: Không tìm thấy thông tin đăng nhập email.");
            return false;
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            String subject = "Welcome to TMobile!";
            String htmlContent = "<h3>Congratulations!</h3>"
                    + "<p>Your account has been successfully created.</p>"
                    + "<p>Now you can log in and enjoy our services.</p>"
                    + "<div style='display: flex; align-items: center; margin-top: 30px;'>"
                    + "  <div style='margin-right: 20px;'>"
                    + " <img src='https://drive.google.com/uc?export=view&id=1L_7sTrEP_dT2hM2oQyloOaZBRKJ8HjLB' alt='TShop' width='80'/>"
                    + "  </div>"
                    + "  <div style='font-size:13px; line-height: 1.5;'>"
                    + "    <div><span style='color:orange;'>T MOBILE</span></div>"
                    + "    <div><span style='color:green;'>| 600, Nguyen Van Cu Street, An Binh Ward, Ninh Kieu District, Can Tho City</span></div>"
                    + "    <div><span style='color:blue;'>| Hotline: 02927.30.30.88 | Email: <a href='mailto:erd.ct@fe.edu.vn'>erd.ct@fe.edu.vn</a></span></div>"
                    + "  </div>"
                    + "</div>";

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "TMobile Support"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("✅ Welcome email sent to " + recipientEmail);
            return true;
        } catch (MessagingException e) {
            System.err.println("❌ Lỗi khi gửi email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Integer getCurrentOTP(String recipientEmail) {
        OTPManager manager = otpMap.get(recipientEmail);
        if (manager != null && !manager.isExpired()) {
            return manager.getOtpCode();
        }
        return null;
    }

    public static boolean verifyOTP(String recipientEmail, int inputCode) {
        OTPManager manager = otpMap.get(recipientEmail);
        if (manager != null && !manager.isExpired() && manager.getOtpCode() == inputCode) {
            otpMap.remove(recipientEmail); // xác minh xong thì xóa
            return true;
        }
        return false;
    }
}
