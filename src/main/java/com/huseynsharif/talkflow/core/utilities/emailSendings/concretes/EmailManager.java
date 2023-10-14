package com.huseynsharif.talkflow.core.utilities.emailSendings.concretes;

import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.EmailService;
import com.huseynsharif.talkflow.core.utilities.results.Result;
import com.huseynsharif.talkflow.core.utilities.results.SuccessResult;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailManager implements EmailService {

    private JavaMailSender mailSender;

    @Override
    public Result sendEmail(String to, String subject, String body) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("randomislerucun123@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        return new SuccessResult("Email was sent.");
    }

    @Override
    public Result sendVerificationEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("randomislerucun123@gmail.com");
        message.setTo(to);
        message.setSubject("Verificate your account");
        message.setText(body);
        mailSender.send(message);
        return new SuccessResult("Verification email was sent.");
    }
}
