package com.huseynsharif.talkflow.dataAccess.abstracts;

import com.huseynsharif.talkflow.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
}
