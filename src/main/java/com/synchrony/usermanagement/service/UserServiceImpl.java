package com.synchrony.usermanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synchrony.usermanagement.models.ImgurResponse;
import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.models.UserDto;
import com.synchrony.usermanagement.models.UserImages;
import com.synchrony.usermanagement.repository.UserImagesRepository;
import com.synchrony.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ImgurApiService imgurApiService;
    final private UserRepository userRepository;
    final private UserImagesRepository userImagesRepository;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserImagesRepository userImagesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userImagesRepository = userImagesRepository;
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
    public UserDto upload(String login, String link, MultipartFile file) throws Exception {
        HttpResponse<String> response = imgurApiService.uploadToImgur(file.getBytes());
        if(response != null && response.statusCode() != 200){
            throw new Exception("Uploading failed");
        }
        String json = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        ImgurResponse root = objectMapper.readValue(json, ImgurResponse.class);
        User user = userRepository.findUserByLogin(login);
        userRepository.updateimageLinkByLogin(login,root.getData().getLink());
        //Add entry in user_images table
        UserImages userImages = new UserImages();
        userImages.setImageLink(root.getData().getLink());
        userImages.setDeleteHash(root.getData().getDeletehash());
        userImages.setImageId(root.getData().getId());
        userImages.setLogin(login);
        userImagesRepository.saveAndFlush(userImages);

    /*    //Get API call
        HttpResponse<String> getResponse = imgurApiService.getUploadedImage(root.getData().getId());
        ImgurResponse obj = objectMapper.readValue(getResponse.body(), ImgurResponse.class);

        //Delete API call
        HttpResponse<String> deleteResponse = imgurApiService.getUploadedImage(root.getData().getId());
        ImgurResponse respObj = objectMapper.readValue(deleteResponse.body(), ImgurResponse.class);
        if(respObj.getStatus() == 200) {
            userImagesRepository.deleteByDeleteHash(root.getData().deletehash);
        }*/
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
