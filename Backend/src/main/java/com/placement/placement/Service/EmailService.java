package com.placement.placement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private  JavaMailSender mailSender;



    private Long otpValue;


    public Long optGenerator(){
        Random random = new Random();
        otpValue = random.nextLong(1000000);
        return otpValue;
    }


    public void sendOtpEmail(String toEmail, Long otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("emanikandan9750@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your OTP for Login");
        message.setText("Your OTP for login is: " + otp + "\nThis OTP is valid for 3 minutes.");
        mailSender.send(message);
        System.out.println("succes");
    }
}
