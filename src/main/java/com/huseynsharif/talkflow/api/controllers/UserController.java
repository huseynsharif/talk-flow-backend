package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
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


}
