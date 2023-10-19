package com.huseynsharif.talkflow.business.concretes;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.EmailService;
import com.huseynsharif.talkflow.core.utilities.emailSendings.abstracts.TemplateService;
import com.huseynsharif.talkflow.core.utilities.results.*;
import com.huseynsharif.talkflow.dataAccess.abstracts.EmailVerificationDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.RoleDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.UserDAO;
import com.huseynsharif.talkflow.entities.concretes.ERole;
import com.huseynsharif.talkflow.entities.concretes.EmailVerification;
import com.huseynsharif.talkflow.entities.concretes.Role;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.RestorePasswordRequestDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserLoginRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private EmailVerificationDAO emailVerificationDAO;
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public DataResult<List<User>> getAll() {
        List<User> users = this.userDAO.findAll();

        if (users.isEmpty()) {
            return new ErrorDataResult<>("Cannot find user.");
        } else {
            return new SuccessDataResult<>(users, "Users listed.");
        }
    }

    @Override
    public DataResult<User> add(UserDTO userDTO) {


        if (!userDTO.getPassword().equals(userDTO.getCpassword())) {
            return new ErrorDataResult<>("Passwords must be same.");
        }


        if (this.userDAO.findUserByUsername(userDTO.getUsername()).isPresent()) {
            return new ErrorDataResult<>("Nickame was already taken.");
        }

        if (this.userDAO.findUserByEmail(userDTO.getEmail()).isPresent()) {
            return new ErrorDataResult<>("Email was already taken.");
        }


        Set<String> strRoles = userDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = this.roleDAO.findRoleByRoleName(ERole.USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = this.roleDAO.findRoleByRoleName(ERole.ADMIN);
                        roles.add(adminRole);
                        break;

                    case "USER":
                        Role userRole = this.roleDAO.findRoleByRoleName(ERole.USER);
                        roles.add(userRole);
                        break;

                }
            });
        }

        User user = new User(userDTO.getUsername(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), roles);

        user.setRoles(roles);
        User saveUserResult = this.userDAO.save(user);

        //Verification
        EmailVerification newUserVerification = new EmailVerification(user);
        this.emailVerificationDAO.save(newUserVerification);
        this.emailService.sendVerificationEmailHtml(user.getUsername(), user.getEmail(), verificationLinkGenerator(user.getId(), newUserVerification.getToken()));

        return new SuccessDataResult<>(saveUserResult, "User successfully added");

    }

    private String verificationLinkGenerator(int userId, String token) {
        return "http://localhost:3000/verificate-user-with-link/" + userId + "/" + token;
    }

    @Override
    public DataResult<User> login(UserLoginRequestDTO loginRequest) {

        User user = this.userDAO.findUserByUsername(loginRequest.getUsername()).orElse(null);
        if (user == null) {
            return new ErrorDataResult<>("Username is incorrect");
        }
        if (!user.isVerificated()) {
            return new ErrorDataResult<>("User has not verified yet.");
        }
        return new SuccessDataResult<>(user, "User successfully finded.");

    }

    @Override
    public Result verificateUserWithLink(int userId, String token) {

        User user = this.userDAO.findById(userId).orElse(null);

        if (user == null) {
            return new ErrorResult("Cannot find user by given id: " + userId);
        }

        EmailVerification emailVerification = this.emailVerificationDAO.findEmailVerificationByToken(token);

        if (emailVerification == null) {
            return new ErrorResult("Cannot find token: " + token);
        }

        if (!Objects.equals(emailVerification.getToken(), token)) {
            return new ErrorResult("Token is incorrect: " + token);
        }

        if (!emailVerification.getCreatedAt().plusMinutes(3).isAfter(LocalDateTime.now())) {
            return new ErrorResult("Token is expired.");
        }

        user.setVerificated(true);
        this.userDAO.save(user);
//        this.emailVerificationDAO.delete(emailVerification);
        return new SuccessResult("Successfully verificated.");
    }

    @Override
    public DataResult<User> sendForgotPasswordEmail(String email) {

        User user = this.userDAO.findUserByEmail(email).orElse(null);
        if (user == null) {
            return new ErrorDataResult<>("Cannot find user with given email: " + email);
        }

        EmailVerification verification = new EmailVerification(user);
        this.emailVerificationDAO.save(verification);
        this.emailService.sendForgotPasswordEmailHtml(
                user.getUsername(),
                user.getEmail(),
                restorePasswordLinkGenerator(user.getId(),
                        verification.getToken()
                ));
        return new SuccessDataResult<>("Email was successfully sent.");
    }

    private String restorePasswordLinkGenerator(int userId, String token) {
        return "http://localhost:3000/new-password/" + userId + "/" + token;
    }

    @Override
    public DataResult<User> restorePassword(RestorePasswordRequestDTO restoreRequest) {

        System.out.println("salam");
        User user = this.userDAO.findById(restoreRequest.getUserId()).orElse(null);

        if (user == null) {
            return new ErrorDataResult<>("Cannot find user by given id: " + restoreRequest.getUserId());
        }

        EmailVerification emailVerification = this.emailVerificationDAO.findEmailVerificationByToken(restoreRequest.getToken());

        if (emailVerification == null) {
            return new ErrorDataResult<>("Cannot find token: " + restoreRequest.getToken());
        }

        if (!Objects.equals(emailVerification.getToken(), restoreRequest.getToken())) {
            return new ErrorDataResult<>("Token is incorrect: " + restoreRequest.getToken());
        }

        if (!emailVerification.getCreatedAt().plusMinutes(3).isAfter(LocalDateTime.now())) {
            return new ErrorDataResult<>("Token is expired.");
        }

        if (!Objects.equals(restoreRequest.getPassword(), restoreRequest.getCpassword())) {
            return new ErrorDataResult<>("Password must be same.");
        }

        user.setPassword(passwordEncoder.encode(restoreRequest.getPassword()));
        this.userDAO.save(user);
        return new SuccessDataResult<>("Password was successfully restored.");
    }
}
