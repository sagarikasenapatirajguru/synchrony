package com.synchrony.usermanagement.service;

import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoggedInUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    public LoggedInUserService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getLogin(),
                    user.getPassword(),
                    Collections.emptyList());
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}
