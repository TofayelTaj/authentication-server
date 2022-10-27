package com.example.authenticationserver.controllers;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.security.CustomUserDetails;
import com.example.authenticationserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User authenticatedUser = principal.getUser();
        return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
    }
}
