package com.infosales.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Resend resend;

    public EmailService(@Value("${RESEND_API_KEY}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void sendSummaryEmail(String toEmail, String summary) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Acme <onboarding@resend.dev>")
                .to(toEmail)
                .subject("Your Sales Analysis Summary")
                .html("<h2>Sales Summary</h2><p>" + summary + "</p>")
                .build();

        try {
            resend.emails().send(params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email via Resend", e);
        }
    }
}