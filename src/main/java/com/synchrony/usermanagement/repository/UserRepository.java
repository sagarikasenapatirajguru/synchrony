package com.synchrony.usermanagement.repository;

import com.synchrony.usermanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    User findUserByLogin(String login);
}
