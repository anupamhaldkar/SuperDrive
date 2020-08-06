package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
public class SignUp {
    private UserService userService;

    @GetMapping
    public String getSignup() {
        return "signup";
    }

    @PostMapping
    public String signingUser(@ModelAttribute User user, Model model){
        String error = null;
        if(!userService.isUsernameAvailable(user.getUsername())){
            error = "Error: Username already Exists";
        }
        if(error == null){
             if(userService.createUser(user)<0)
                 error="Error creating User, try again";
        }
        if(error == null)
            model.addAttribute("signupSuccess",true);
        else
            model.addAttribute("error",error);
        return "signup";
    }

}
