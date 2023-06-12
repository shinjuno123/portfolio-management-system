package com.amazing.juno.pmsrest.service.gmail;


import jakarta.mail.MessagingException;

import java.io.IOException;

public interface GmailService {

    void sendMessage(String toEmail, String subject, String message);
}