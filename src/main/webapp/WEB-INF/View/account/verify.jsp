<%-- 
    Document   : verify
    Created on : Jun 11, 2025, 4:10:00 PM
    Author     : pc
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Verify OTP</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            .login-container {
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px 0;
            }

            .login-card {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                padding: 40px;
                width: 100%;
                max-width: 450px;
            }

            .login-title {
                color: #333;
                font-weight: 600;
                margin-bottom: 10px;
                text-align: center;
            }

            .subtitle {
                color: #6c757d;
                font-size: 14px;
                text-align: center;
                margin-bottom: 30px;
            }

            .otp-container {
                display: flex;
                justify-content: space-between;
                gap: 10px;
                margin-bottom: 30px;
            }

            .otp-input {
                width: 50px;
                height: 50px;
                border: 2px solid #e9ecef;
                border-radius: 10px;
                text-align: center;
                font-size: 18px;
                font-weight: bold;
                transition: all 0.3s ease;
            }

            .otp-input:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
                outline: none;
            }

            .otp-input.filled {
                border-color: #28a745;
                background-color: #f8f9fa;
            }

            .form-control {
                border-radius: 10px;
                border: 2px solid #e9ecef;
                padding: 12px 15px;
                transition: all 0.3s ease;
                margin-bottom: 15px;
            }

            .form-control:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
            }

            .btn-verify {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 15px;
                color: white;
            }

            .btn-verify:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
                color: white;
            }

            .btn-verify:disabled {
                opacity: 0.6;
                cursor: not-allowed;
                transform: none;
            }

            .btn-resend {
                background: linear-gradient(135deg, #ffa726 0%, #ff9800 100%);
                border: none;
                border-radius: 10px;
                padding: 12px 30px;
                font-weight: 600;
                width: 100%;
                transition: all 0.3s ease;
                margin-bottom: 20px;
                color: white;
            }

            .btn-resend:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(255, 167, 38, 0.3);
                color: white;
            }

            .btn-resend:disabled {
                opacity: 0.6;
                cursor: not-allowed;
                transform: none;
            }

            .back-link {
                color: #667eea;
                text-decoration: none;
                font-size: 14px;
                transition: all 0.3s ease;
                display: block;
                text-align: center;
            }

            .back-link:hover {
                color: #764ba2;
                text-decoration: underline;
            }

            .divider {
                text-align: center;
                margin: 20px 0;
                position: relative;
                color: #6c757d;
            }

            .divider::before {
                content: '';
                position: absolute;
                top: 50%;
                left: 0;
                right: 0;
                height: 1px;
                background: #e9ecef;
            }

            .divider span {
                background: rgba(255, 255, 255, 0.95);
                padding: 0 15px;
            }

            .error-message {
                background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
                color: white;
                padding: 15px;
                border-radius: 10px;
                margin-bottom: 20px;
                text-align: center;
                font-weight: 500;
            }

            .success-message {
                background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
                color: white;
                padding: 15px;
                border-radius: 10px;
                margin-bottom: 20px;
                text-align: center;
                font-weight: 500;
            }

            .timer {
                text-align: center;
                color: #6c757d;
                font-size: 14px;
                margin-bottom: 15px;
            }

            .timer.warning {
                color: #ff6b6b;
            }

            .verification-icon {
                font-size: 60px;
                color: #667eea;
                text-align: center;
                margin-bottom: 20px;
            }

            .hidden {
                display: none;
            }

            .pulse {
                animation: pulse 2s infinite;
            }

            @keyframes pulse {
                0% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.05);
                }
                100% {
                    transform: scale(1);
                }
            }
        </style>
    </head>
    <body>
        <%
            String otpPurpose = (String) session.getAttribute("otpPurpose");
            if (otpPurpose == null) {
                otpPurpose = "register"; // fallback
            }
        %>


        <div class="login-container">
            <div class="login-card">
                <div class="verification-icon">
                    <i class="bi bi-shield-lock-fill"></i>
                </div>

                <h2 class="login-title">Verify Your Account</h2>
                <p class="subtitle">Enter the 6-digit code sent to your email</p>

                <%-- Success message --%>
                <div id="successMessage" class="success-message hidden">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    <span id="successText">Code sent successfully!</span>
                </div>

                <%-- Error message --%>
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                <div class="error-message">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <%= error%>
                </div>
                <%
                    }
                %>

                <form method="post" action="Verify" id="verifyForm">
                    <!-- OTP Input Fields -->
                    <div class="otp-container">
                        <input type="text" class="otp-input" maxlength="1" data-index="0">
                        <input type="text" class="otp-input" maxlength="1" data-index="1">
                        <input type="text" class="otp-input" maxlength="1" data-index="2">
                        <input type="text" class="otp-input" maxlength="1" data-index="3">
                        <input type="text" class="otp-input" maxlength="1" data-index="4">
                        <input type="text" class="otp-input" maxlength="1" data-index="5">
                    </div>

                    <!-- Hidden input for the complete OTP -->
                    <input type="hidden" name="otp" id="otpValue">

                    <!-- Timer -->
                    <div class="timer" id="timer">
                        Code expires in: <span id="countdown">05:00</span>
                    </div>

                    <button type="submit" class="btn btn-verify" id="verifyBtn">
                        <i class="bi bi-shield-check me-2"></i>
                        Verify Code
                    </button>

                    <div class="divider">
                        <span>Need help?</span>
                    </div>

                    <% if ("register".equals(otpPurpose)) { %>
                    <a href="Register" class="back-link">
                        <i class="bi bi-arrow-left me-2"></i>
                        Back to Register
                    </a>
                    <% } else if ("forgot".equals(otpPurpose)) { %>
                    <a href="ForgotPassword" class="back-link">
                        <i class="bi bi-arrow-left me-2"></i>
                        Back to Forgot Password
                    </a>
                    <% }%>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            let expiryTime = 300; // 5 phút
            let expiryInterval;
            let resendCooldown = 60;
            let resendInterval;

            document.addEventListener('DOMContentLoaded', function () {
                console.log("✅ DOMContentLoaded - script is running...");

                const otpInputs = document.querySelectorAll('.otp-input');
                const otpValue = document.getElementById('otpValue');
                const verifyBtn = document.getElementById('verifyBtn');
                const resendBtn = document.getElementById('resendBtn');
                const resendText = document.getElementById('resendText');
                const successMessage = document.getElementById('successMessage');
                const successText = document.getElementById('successText');
                const countdown = document.getElementById('countdown');
                const timer = document.getElementById('timer');

                console.log("🕒 Countdown element:", countdown);
                console.log("⏱️ Timer element:", timer);

                // Xử lý nhập từng ô
                otpInputs.forEach((input, index) => {
                    input.addEventListener('input', function (e) {
                        const value = e.target.value;
                        if (value.length === 1 && /^\d$/.test(value)) {
                            input.classList.add('filled');
                            if (index < otpInputs.length - 1)
                                otpInputs[index + 1].focus();
                        } else if (value.length === 0) {
                            input.classList.remove('filled');
                        }
                        updateOTPValue();
                    });

                    input.addEventListener('keydown', function (e) {
                        if (e.key === 'Backspace' && !e.target.value && index > 0) {
                            otpInputs[index - 1].focus();
                        }

                        if (e.key === 'v' && e.ctrlKey) {
                            e.preventDefault();
                            navigator.clipboard.readText()
                                    .then(text => {
                                        const digits = text.replace(/\D/g, '').slice(0, 6);
                                        fillOTP(digits);
                                    })
                                    .catch(err => {
                                        console.error("❌ Clipboard read failed:", err);
                                        alert("Unable to paste OTP. Please enter manually.");
                                    });
                        }
                    });

                    input.addEventListener('keypress', function (e) {
                        if (!/^\d$/.test(e.key)) {
                            e.preventDefault();
                        }
                    });
                });

                function updateOTPValue() {
                    const otp = Array.from(otpInputs).map(input => input.value).join('');
                    otpValue.value = otp;
                    verifyBtn.disabled = otp.length !== 6;
                }

                function fillOTP(digits) {
                    otpInputs.forEach((input, index) => {
                        if (digits[index]) {
                            input.value = digits[index];
                            input.classList.add('filled');
                        }
                    });
                    updateOTPValue();
                }
                function startExpiryTimer() {
                    expiryTime = 300;
                    clearInterval(expiryInterval);
                    timer.classList.remove('warning');

                    function updateTimer() {
                        const minutes = Math.floor(expiryTime / 60);
                        const seconds = expiryTime % 60;
                        const formatted = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
                                        countdown.textContent = formatted;
                                        console.log("🕓 Countdown cập nhật:", formatted);
                                    }
                                    updateTimer();

                                    expiryInterval = setInterval(() => {
                                        expiryTime--;
                                        updateTimer();

                                        if (expiryTime <= 60)
                                            timer.classList.add('warning');

                                        if (expiryTime <= 0) {
                                            clearInterval(expiryInterval);
                                            countdown.textContent = 'Expired';
                                            verifyBtn.disabled = true;
                                            otpInputs.forEach(input => input.disabled = true);
                                            console.log("⚠️ Countdown hết hạn");
                                        }
                                    }, 1000);
                                }

                                // Xử lý khi nhấn nút xác thực
                                document.getElementById('verifyForm').addEventListener('submit', function (e) {
                                    updateOTPValue();
                                    const otp = otpValue.value;
                                    if (otp.length !== 6) {
                                        e.preventDefault();
                                        alert('Please enter a complete 6-digit code');
                                        return;
                                    }
                                    verifyBtn.innerHTML = '<i class="bi bi-hourglass-split me-2"></i>Verifying...';
                                    verifyBtn.disabled = true;
                                });

                                // Gọi khi trang tải
                                startExpiryTimer();
                                otpInputs[0].focus();
                            });
        </script>
    </body>
</html>