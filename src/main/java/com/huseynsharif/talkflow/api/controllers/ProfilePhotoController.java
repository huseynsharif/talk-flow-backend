package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.ProfilePhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/photos")
@CrossOrigin
public class ProfilePhotoController {

    private ProfilePhotoService profilePhotoService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.profilePhotoService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(MultipartFile file, int userId) throws IOException {
        return ResponseEntity.ok(this.profilePhotoService.add(file, userId));
    }
}
