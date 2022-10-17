package com.example.authenticationserver.controllers;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.enums.OAuthProvider;
import com.example.authenticationserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public ResponseEntity<User> saveNewUser(@RequestBody  User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(OAuthProvider.LOCAL);
        User savedUser = userService.signupUser(user);
        return new ResponseEntity<>(savedUser, savedUser == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

}
