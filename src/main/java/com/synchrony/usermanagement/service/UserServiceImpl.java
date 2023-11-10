package com.synchrony.usermanagement.service;

import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.models.UserDto;
import com.synchrony.usermanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
    }

    @Override
    public void saveUser(UserDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmailId(dto.getEmailId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setProfession(dto.getProfession());
        userRepository.save(user);
    }


    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
