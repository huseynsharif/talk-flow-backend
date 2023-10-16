package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.EmailService;
import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.TemplateService;
import com.huseynsharif.talkflow.dataAccess.abstracts.EmailVerificationDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.UserDAO;
import com.huseynsharif.talkflow.entities.concretes.EmailVerification;
import com.huseynsharif.talkflow.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/emails")
@AllArgsConstructor
@CrossOrigin
public class EmailController {

    private EmailVerificationDAO emailVerificationDAO;
    private UserDAO userDAO;
    private EmailService emailService;
    private TemplateService templateService;

//    @PostMapping("/send-verification-mail")
//    public ResponseEntity<?> sendVerificationMail(){
//        return ResponseEntity.ok(this.emailService.sendEmail(
//                "huseynsharif@gmail.com",
//                "Test",
//                templateService.userVerificationTemplate(89, "12345")));
//    }

    @PostMapping("/salam")
    public ResponseEntity<?> salam(){

//        User user = this.userDAO.findById(92).orElseThrow();
//        EmailVerification e = new EmailVerification();
//        e.setUser(user);
//        e.setToken(UUID.randomUUID().toString());
//        e.setCreatedAt(LocalDateTime.now());
//        System.out.println(user);
//        System.out.println(e);
//        this.emailVerificationDAO.save(e);
        emailService.sendEmail("huseynsharif@gmail.com", "Salam", "Necesen");
        emailService.sendVerificationEmailHtml("Mehdi", "huseynsharif@gmail.com", "linkim");
        return ResponseEntity.ok("Gonderildi");
    }
}
