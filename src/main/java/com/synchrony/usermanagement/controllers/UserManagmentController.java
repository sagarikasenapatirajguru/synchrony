package com.synchrony.usermanagement.controllers;


import com.synchrony.usermanagement.models.UserDto;
import com.synchrony.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class UserManagmentController {
    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") ;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        Optional<UserDto> existing = userService.findUserByLogin(user.getLogin());
        if (existing.isPresent()) {
            result.rejectValue("login", null, "There is already an account registered with that login id");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/profile")
    public String profileDisplay(Authentication authentication,Model model) {
        User authenticateduser = (User) authentication.getPrincipal();
        Optional<UserDto> userDto = userService.findUserByLogin(authenticateduser.getUsername());
        model.addAttribute("user",userDto.get());
        log.info("rendering profile page");
        return "profile";
    }
    @PostMapping("/upload")
    public String uploadImage(Authentication authentication,Model model, @RequestParam("image") MultipartFile file) throws Exception {
        StringBuilder fileNames = new StringBuilder();
        fileNames.append(file.getOriginalFilename());
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY,"target", file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        UserDto userDto = userService.upload(authentication.getName(), fileNames.toString(),file);
        model.addAttribute("user", userDto);
        log.info("Upload successful ,rendering profile page");
        return "profile";
    }
/*    @GetMapping("/authorized/")
    public String redirectUrlServiceForAccessToken(@PathVariable("access_token") String accesstoken) {

        String  recievedToken = accesstoken;
        return "profile";
    }*/


}
