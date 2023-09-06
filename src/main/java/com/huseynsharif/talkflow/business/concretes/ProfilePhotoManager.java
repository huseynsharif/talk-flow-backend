package com.huseynsharif.talkflow.business.concretes;

import com.huseynsharif.talkflow.business.abstracts.ProfilePhotoService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.core.utilities.results.SuccessDataResult;
import com.huseynsharif.talkflow.dataAccess.abstracts.ProfilePhotoDAO;
import com.huseynsharif.talkflow.entities.concretes.ProfilePhoto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfilePhotoManager implements ProfilePhotoService {

    private ProfilePhotoDAO profilePhotoDAO;

    @Override
    public DataResult<List<ProfilePhoto>> getAll() {


        List<ProfilePhoto> photos = this.profilePhotoDAO.findAll();

        if(photos.isEmpty()){
            return new ErrorDataResult<>("Cannot find photo.");
        }
        else {
            return new SuccessDataResult<>(photos, "Photos listed.");
        }

    }

    @Override
    public DataResult<ProfilePhoto> add(ProfilePhoto profilePhoto) {
        return new SuccessDataResult<>(this.profilePhotoDAO.save(profilePhoto), "Profile Photo successfully added");
    }

    @Override
    public DataResult<ProfilePhoto> getProfilePhotoByUserId(int userId) {

        ProfilePhoto profilePhoto = this.profilePhotoDAO.getProfilePhotoByUserId(userId);

        if (profilePhoto==null){
            return new ErrorDataResult<>("Cannot find profile photo with given id.");
        }
        else {
            return new SuccessDataResult<>(profilePhoto, "Successfully finded.");
        }
    }
}
