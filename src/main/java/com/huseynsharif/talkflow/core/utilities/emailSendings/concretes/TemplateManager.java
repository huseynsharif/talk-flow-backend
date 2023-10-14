package com.huseynsharif.talkflow.core.utilities.emailSendings.concretes;

import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.TemplateService;
import org.springframework.stereotype.Service;

@Service
public class TemplateManager implements TemplateService {

    @Override
    public String greetingTemplate(String username) {
        return "<h1>Salam, "+username+"</h1>";
    }

    @Override
    public String userVerificationTemplate(int userId, String token) {
        return "Salam\n\n" + "http://localhost:8080/api/users/verificate-user-with-link?userId="+userId+"&token=" +token;
    }
}
