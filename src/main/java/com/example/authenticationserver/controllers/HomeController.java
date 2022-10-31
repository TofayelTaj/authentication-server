package com.example.authenticationserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> home(){
        System.out.println("home.........................");
        return new ResponseEntity<>("Hello Home controller",HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<String> test(@AuthenticationPrincipal OAuth2User principle){
        System.out.println(".................................");
        if(principle == null){
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }
        System.out.println("Auth User : " + principle.getName());
        return new ResponseEntity<>(principle.getName(), HttpStatus.ACCEPTED);
    }
}
