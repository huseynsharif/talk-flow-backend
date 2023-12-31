package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.Result;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.RestorePasswordRequestDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserLoginRequestDTO;

import java.util.List;

public interface UserService {

    public DataResult<List<User>> getAll();

    public DataResult<User> add(UserDTO userDTO);

    public DataResult<User> login(UserLoginRequestDTO userLoginRequestDTO);

    public Result verificateUserWithLink(int userId, String token);

    public DataResult<User> sendForgotPasswordEmail(String email);

    public DataResult<User> restorePassword(RestorePasswordRequestDTO restoreRequest);

}
