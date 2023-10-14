package com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts;

public interface TemplateService {

    String greetingTemplate(String username);

    String userVerificationTemplate(int userId, String token);

}
