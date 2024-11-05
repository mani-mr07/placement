package com.placement.placement.Service;

import com.placement.placement.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;
import com.placement.placement.Entity.Staff;

@Service
public class OTPService {

    @Autowired
    private StaffRepository staffRepository;

    private final EmailService emailService;
    private final Random random = new Random();

    public OTPService(EmailService emailService) {
        this.emailService = emailService;
    }

    public String generateAndSendOTP(String email) {
        String otp = String.format("%04d", random.nextInt(10000));
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(3);

        // Save OTP and expiry time in database (logic to be implemented)

        // Send OTP via email
        emailService.sendOtpEmail(email, otp);

        return "OTP sent successfully.";
    }

}

