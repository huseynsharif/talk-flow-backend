package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.ProfilePhotoService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.entities.concretes.ProfilePhoto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/profilephotos")
@CrossOrigin
public class ProfilePhotoController {

    private ProfilePhotoService profilePhotoService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.profilePhotoService.getAll());
    }

    @PostMapping("/add")
    public DataResult<ProfilePhoto> add(MultipartFile file, int userId) throws IOException {
        return this.profilePhotoService.add(file, userId);
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
