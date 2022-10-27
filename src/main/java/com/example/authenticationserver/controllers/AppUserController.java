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
@RequestMapping("/app-user")
public class AppUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authenticatedUser =(CustomUserDetails) authentication.getPrincipal();
        User user = authenticatedUser.getUser();
        // TODO: ২৭/১০/২২  find authenticated user
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
