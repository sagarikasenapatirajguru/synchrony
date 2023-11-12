package com.synchrony.usermanagement.service;

import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.models.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto user);

    Optional<UserDto> findUserByLogin(String login);

    List<UserDto> findAllUser();
    UserDto updateImageLinkByLogin(String login, String link, MultipartFile file);
}
