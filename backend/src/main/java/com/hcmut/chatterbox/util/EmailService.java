package com.hcmut.chatterbox.util;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendOtpEmail(String toEmail, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Your Chatterbox OTP");
        helper.setFrom("heinsbergnguyen@gmail.com");

        String htmlContent = "<h3>Welcome to Chatterbox!</h3>" +
                             "<p>Your OTP for account verification is: <b>" + otp + "</b></p>" +
                             "<p>This code is valid for 5 minutes.</p>" +
                             "<p>If you didn't request this, please ignore this email.</p>";
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }
}