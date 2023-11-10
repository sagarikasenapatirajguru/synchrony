package com.synchrony.usermanagement.controllers;


import com.synchrony.usermanagement.models.User;
import com.synchrony.usermanagement.models.UserDto;
import com.synchrony.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserManagmentController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user",new UserDto());
        return "register";
    }
    @PostMapping("/register/save")
    public String registration( @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findUserByLogin(user.getLogin());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "users";
    }

}
