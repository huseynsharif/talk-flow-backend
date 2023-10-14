package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.EmailService;
import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
@AllArgsConstructor
@CrossOrigin
public class EmailController {

    private EmailService emailService;
    private TemplateService templateService;

    @PostMapping("/send-verification-mail")
    public ResponseEntity<?> sendVerificationMail(){
        return ResponseEntity.ok(this.emailService.sendEmail(
                "huseynsharif@gmail.com",
                "Test",
                templateService.userVerificationTemplate(71, "12345678")));
    }
}
