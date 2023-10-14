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
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserLoginRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private TemplateService templateService;

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

        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                roles
        );

        user.setRoles(roles);
        User saveUserResult = this.userDAO.save(user);
        EmailVerification newUserVerification = new EmailVerification(user);
        System.out.println(newUserVerification);
        this.emailVerificationDAO.save(newUserVerification);
        System.out.println("1");
        this.emailService.sendVerificationEmail(
                user.getEmail(),
                templateService.userVerificationTemplate(
                        user.getId(),
                        newUserVerification.getToken()
                )
        );

        return new SuccessDataResult<>(saveUserResult, "User successfully added");

    }

    @Override
    public DataResult<User> login(UserLoginRequestDTO loginRequest) {

//        User user = this.userDAO.findUserByUsernameAndPassword(
//                loginRequest.getUsername(),
//                loginRequest.getPassword()
//        );

        User user = this.userDAO.findUserByUsername(loginRequest.getUsername()).orElse(null);
        if (user == null || !user.getPassword().equals(passwordEncoder.encode(loginRequest.getPassword()))) {
            return new ErrorDataResult<>("Username or password is incorrect.");
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

        user.setVerificated(true);
        this.userDAO.save(user);
        return new SuccessResult("Successfully verificated.");
    }


}
