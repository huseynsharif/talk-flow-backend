package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.ProfilePhoto;

import java.util.List;

public interface ProfilePhotoService {

    public DataResult<List<ProfilePhoto>> getAll();

    public DataResult<ProfilePhoto> add(ProfilePhoto profilePhoto);

    DataResult<ProfilePhoto> getProfilePhotoByUserId(int userId);

}
