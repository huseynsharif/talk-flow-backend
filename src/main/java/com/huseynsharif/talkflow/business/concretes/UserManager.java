package com.huseynsharif.talkflow.business.concretes;

import com.huseynsharif.talkflow.business.abstracts.UserService;
import com.huseynsharif.talkflow.core.adapters.mappers.ModelMapperService;
import com.huseynsharif.talkflow.core.security.entities.CustomUserDetails;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.core.utilities.results.SuccessDataResult;
import com.huseynsharif.talkflow.dataAccess.abstracts.RoleDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.UserDAO;
import com.huseynsharif.talkflow.entities.concretes.ERole;
import com.huseynsharif.talkflow.entities.concretes.Role;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private ModelMapperService modelMapperService;
    private AuthenticationManager authenticationManager;



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
    public DataResult<User> add(UserDTO userDTO) {


        if (!userDTO.getPassword().equals(userDTO.getCpassword())){
            return new ErrorDataResult<>("Passwords must be same.");
        }


        if (this.userDAO.findUserByNickname(userDTO.getNickname()).isPresent()){
            return new ErrorDataResult<>("Nickame already taken.");
        }

        if (this.userDAO.findUserByEmail(userDTO.getEmail()).isPresent()){
            return new ErrorDataResult<>("Email already taken.");
        }



        Set<String> strRoles = userDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles==null){
            Role userRole = this.roleDAO.findRoleByRoleName(ERole.USER);
        }
        else {
            strRoles.forEach(role -> {
                switch (role){
                    case "ADMIN" :
                        Role adminRole = this.roleDAO.findRoleByRoleName(ERole.ADMIN);
                        roles.add(adminRole);
                        break;

                    case "USER" :
                        Role userRole = this.roleDAO.findRoleByRoleName(ERole.USER);
                        roles.add(userRole);
                        break;

                }
            });


        }


        User user = modelMapperService.getModelMapper().map(userDTO, User.class);

        user.setRoles(roles);

        return new SuccessDataResult<>(this.userDAO.save(user), "User successfully added");

    }

    @Override
    public DataResult<User> login(String email, String password) {

        User user = this.userDAO.findUserByEmailAndPassword(email, password);

        if (user==null){
            return new ErrorDataResult<>("Email or password is incorrect.");
        }

        return new SuccessDataResult<>(user);

    }


}
