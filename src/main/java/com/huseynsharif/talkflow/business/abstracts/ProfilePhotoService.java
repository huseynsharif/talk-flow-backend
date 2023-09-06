package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.ProfilePhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfilePhotoService {

    public DataResult<List<ProfilePhoto>> getAll();

    public DataResult<ProfilePhoto> add(MultipartFile file, int userId) throws IOException;

    DataResult<ProfilePhoto> getProfilePhotoByUserId(int userId);

}
