package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.core.utilities.emailSendings.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(){
        return ResponseEntity.ok(this.emailService.sendEmail("huseynsharif@gmail.com", "Test", "Salam"));
    }
}
