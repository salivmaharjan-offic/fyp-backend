package com.hardware.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);

        message.setSubject(
                "Hardware Store Verification Code"
        );

        message.setText(
                "Your verification code is: "
                        + otp +
                        "\n\nExpires in 5 minutes."
        );

        mailSender.send(message);
    }
}
