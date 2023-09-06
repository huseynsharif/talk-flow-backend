package com.huseynsharif.talkflow.entities.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile_photos")
public class ProfilePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private int id;


    @NotNull
    @NotBlank
    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProfilePhoto(String photoUrl, User user) {
        this.photoUrl = photoUrl;
        this.user = user;
    }
}
