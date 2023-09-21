package com.huseynsharif.talkflow.dataAccess.abstracts;

import com.huseynsharif.talkflow.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

    User findUserByEmailAndPassword(String email, String password);

    User findUserByUsernameAndPassword(String username, String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

}
