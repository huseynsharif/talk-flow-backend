package com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.Result;

public interface EmailService {

    Result sendEmail(String to, String subject, String body);

    Result sendVerificationEmail(String to, String body);

    Result sendVerificationEmailHtml(String username, String to, String url);

    Result sendForgotPasswordEmailHtml(String username, String to, String url);

}
