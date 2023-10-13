package com.huseynsharif.talkflow.core.utilities.emailSendings;

import com.huseynsharif.talkflow.core.utilities.results.Result;

public interface EmailService {

    Result sendEmail(String to, String subject, String body);

}
