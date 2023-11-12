package com.synchrony.usermanagement.service;

import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.models.UserDto;
import com.synchrony.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ImgurApiService imgurApiService;
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
    public Optional<UserDto> findUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        if (user!=null) {
            return Optional.of(convertUserToDTO(user));
        }
        return Optional.empty();
    }

    @Override
    public List<UserDto> findAllUser() {
        List<User> user= userRepository.findAll();
        return user.stream().map(u -> convertUserToDTO(u)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateImageLinkByLogin(String login, String link, MultipartFile file) {
        imgurApiService.postUploadedImage("https://api.imgur.com/3/upload",file);
        userRepository.updateimageLinkByLogin(login,link);
        User user = userRepository.findUserByLogin(login);
        return convertUserToDTO(user);
    }

    private UserDto convertUserToDTO(User user){

        UserDto userDto = new UserDto();
        userDto.setEmailId(user.getEmailId());
        userDto.setLogin(user.getLogin());
        userDto.setLastName(user.getLastName());
        userDto.setProfession(user.getProfession());
        userDto.setFirstName(user.getFirstName());
        userDto.setImageLink(user.getImageLink());
        return userDto;
    }
}
