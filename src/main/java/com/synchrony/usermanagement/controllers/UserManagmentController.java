package com.synchrony.usermanagement.controllers;



import com.synchrony.usermanagement.models.UserDto;
import com.synchrony.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class UserManagmentController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";

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
        return "profile";
    }
    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
       // User authenticateduser = (User) authentication.getPrincipal();
        UserDto userDto = userService.updateImageLinkByLogin("sa",fileNames.toString(),file);
        model.addAttribute("user", userDto);
        return "profile";
    }
    @GetMapping("/authorized/")
    public String redirectUrlServiceForAccessToken(@PathVariable("access_token") String accesstoken) {

        String  recievedToken = accesstoken;
        return "profile";
    }


}
