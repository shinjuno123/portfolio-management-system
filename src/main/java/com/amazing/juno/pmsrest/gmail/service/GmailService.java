package com.amazing.juno.pmsrest.gmail.service;


import jakarta.mail.MessagingException;

import java.io.IOException;

public interface GmailService {

    void sendMessage(String toEmail, String subject, String message);
}