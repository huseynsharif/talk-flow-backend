package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.User;

import java.util.List;

public interface UserService {

    public DataResult<List<User>> getAll();

    public DataResult<User> add(User user);

}
