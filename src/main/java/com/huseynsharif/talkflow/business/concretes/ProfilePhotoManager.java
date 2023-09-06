package com.huseynsharif.talkflow.business.concretes;

import com.cloudinary.utils.ObjectUtils;
import com.huseynsharif.talkflow.business.abstracts.ProfilePhotoService;
import com.huseynsharif.talkflow.core.configs.CloudinaryConfig;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.core.utilities.results.SuccessDataResult;
import com.huseynsharif.talkflow.dataAccess.abstracts.ProfilePhotoDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.UserDAO;
import com.huseynsharif.talkflow.entities.concretes.ProfilePhoto;
import com.huseynsharif.talkflow.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProfilePhotoManager implements ProfilePhotoService {

    private ProfilePhotoDAO profilePhotoDAO;
    private UserDAO userDAO;
    private CloudinaryConfig cloudinaryConfig;

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
    public DataResult<ProfilePhoto> add(MultipartFile file, int userId) throws IOException {

        Map uploadResult = cloudinaryConfig
                .cloudinary()
                .uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());

        User user = this.userDAO.findById(userId).orElseThrow();

        ProfilePhoto profilePhoto = new ProfilePhoto(String.valueOf(uploadResult.get("url")) , user);

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
