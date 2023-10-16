package com.huseynsharif.talkflow.core.utilities.emailSendings.concretes;

import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.EmailService;
import com.huseynsharif.talkflow.core.utilities.results.Result;
import com.huseynsharif.talkflow.core.utilities.results.SuccessResult;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailManager implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public Result sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        return new SuccessResult("Email was sent.");
    }

    @Override
    public Result sendVerificationEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Verificate your account");
        message.setText(body);
        mailSender.send(message);
        return new SuccessResult("Verification email was sent.");
    }

    @Override
    public Result sendVerificationEmailHtml(String username, String to, String url) {
        try {
            Context context = new Context();
            context.setVariables(Map.of(
                    "name",username,
                    "url", url
            ));
            String body = templateEngine.process("verificationemail", context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject("New user verification");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new SuccessResult("Verification Html email was sent.");
    }


}
