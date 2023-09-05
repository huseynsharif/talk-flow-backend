package com.huseynsharif.talkflow.business.concretes;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.core.utilities.results.SuccessDataResult;
import com.huseynsharif.talkflow.dataAccess.abstracts.UserDAO;
import com.huseynsharif.talkflow.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private UserDAO userDAO;


    @Override
    public DataResult<List<User>> getAll() {

        List<User> users = this.userDAO.findAll();

        if(users.isEmpty()){
            return new ErrorDataResult<>("Cannot find user.");
        }
        else {
            return new SuccessDataResult<>(users, "Users listed.");
        }
    }

    @Override
    public DataResult<User> add(User user) {

            return new SuccessDataResult<>(this.userDAO.save(user), "User successfully added");

    }
}
