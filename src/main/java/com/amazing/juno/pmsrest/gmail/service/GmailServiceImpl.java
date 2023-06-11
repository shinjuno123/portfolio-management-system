package com.amazing.juno.pmsrest.gmail.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailServiceImpl implements GmailService{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String myEmail;

    @Override
    @Async
    public void sendMessage(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(myEmail);
        javaMailSender.send(mailMessage);
    }
}
