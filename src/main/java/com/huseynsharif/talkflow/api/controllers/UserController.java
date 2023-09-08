package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private UserService userService;

    @GetMapping("getall")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.userService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(this.userService.add(userDTO));
    }


    @GetMapping("/findUserByEmailAndPassword")
    public ResponseEntity<?> findUserByEmailAndPassword(String email, String password){
        return ResponseEntity.ok(this.userService.findUserByEmailAndPassword(email, password));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exception){

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError :exception.getBindingResult().getFieldErrors()) {

            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            
        }

        ErrorDataResult<Object> errors = new ErrorDataResult<>(validationErrors, "Validation errors.");

        return errors;

    }
}
