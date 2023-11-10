package com.synchrony.usermanagement.service;

import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.models.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto user);

    User findUserByLogin(String login);

    List<User> findAllUser();
}
