package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;

import java.util.List;

public interface UserService {

    public DataResult<List<User>> getAll();

    public DataResult<User> add(UserDTO userDTO);

    public DataResult<User> findUserByEmailAndPassword(String email, String password);

}
