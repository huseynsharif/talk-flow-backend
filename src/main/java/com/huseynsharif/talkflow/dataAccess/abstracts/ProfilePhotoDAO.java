package com.huseynsharif.talkflow.dataAccess.abstracts;

import com.huseynsharif.talkflow.entities.concretes.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePhotoDAO extends JpaRepository<ProfilePhoto, Integer> {

    ProfilePhoto getProfilePhotoByUserId(int userId);

}
