package com.cherish.mailsender.controller;

import com.cherish.mailsender.scheduler.ScheduledEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

// Added just for testing, before implementing Scheduler
@RestController
@RequestMapping
public class EmailController {
    private final ScheduledEmailService emailService;

    @Autowired
    public EmailController(ScheduledEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public ResponseEntity<String> sendDailyEmail() throws MessagingException, IOException {
        emailService.sendDailyEmailWithAttachment();
        return new ResponseEntity<>("Success",OK);
    }
}
