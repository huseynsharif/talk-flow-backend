package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.RestorePasswordRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){

        DataResult<List<User>> result = this.userService.getAll();

        if(!result.isSuccess()){
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(this.userService.getAll());
    }


    @GetMapping("/verificate-user-with-link")
    public ResponseEntity<?> verificateUserWithLink(@RequestParam("userId") int userId, @RequestParam("token") String token){

        return ResponseEntity.ok(this.userService.verificateUserWithLink(userId, token));

    }

    @PostMapping("/send-forgot-password-email")
    public ResponseEntity<?> forgotPasswordVerification(@RequestParam("email") String email){

        return ResponseEntity.ok(this.userService.sendForgotPasswordEmail(email));

    }

    @PostMapping("/restore-password")
    public ResponseEntity<?> restorePassword(@RequestBody RestorePasswordRequestDTO restoreRequest){

        return ResponseEntity.ok(this.userService.restorePassword(restoreRequest));

    }


}
