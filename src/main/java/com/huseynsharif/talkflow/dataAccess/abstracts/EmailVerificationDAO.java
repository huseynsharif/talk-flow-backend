package com.huseynsharif.talkflow.dataAccess.abstracts;

import com.huseynsharif.talkflow.entities.concretes.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationDAO extends JpaRepository<EmailVerification, Integer> {

    EmailVerification findEmailVerificationByToken(String token);

}
