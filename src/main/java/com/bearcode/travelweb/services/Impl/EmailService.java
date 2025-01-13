package com.bearcode.travelweb.services.Impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Async
    public void sendBookingConfirmation(String toEmail, String subject, String body, String qrImagePath) {
        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(toEmail);
//            message.setSubject(subject);
//            message.setText(body);
//            message.setFrom("tuantupham255@gmail.com");  // Email gửi đi
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            // email gui di
            helper.setFrom("tuantupham255@gmail.com");

            // Them tep file dinh kem anh
            File qrImageFile = new File(qrImagePath); // Đường dẫn ảnh QR
            if (qrImageFile.exists()) {
                helper.addAttachment("QR Code để thanh toán", qrImageFile);
            }
            javaMailSender.send(message);  // Gửi email
        } catch (Exception e) {
            System.out.println("Lỗi gửi email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Không thể gửi email: " + e.getMessage());
        }
    }
}