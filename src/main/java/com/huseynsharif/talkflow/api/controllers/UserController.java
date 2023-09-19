package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.security.entities.CustomUserDetails;
import com.huseynsharif.talkflow.core.security.jwt.JwtUtils;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserInfoResponse;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserLoginRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){

        DataResult<List<User>> result = this.userService.getAll();

        if(!result.isSuccess()){
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(this.userService.add(userDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDTO loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateJwtToken(authentication);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        jwtToken,
                        roles));
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
