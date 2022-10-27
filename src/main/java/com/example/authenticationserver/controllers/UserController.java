package com.example.authenticationserver.controllers;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.enums.OAuthProvider;
import com.example.authenticationserver.services.UserService;
import com.example.authenticationserver.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/user")
    public ResponseEntity<String> saveNewUser(@RequestBody  User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(OAuthProvider.LOCAL);
        try {
            User savedUser = userService.signupUser(user);
            return new ResponseEntity(savedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Duplicate", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("userName");
        return ResponseEntity.ok("logout success");
    }
}
