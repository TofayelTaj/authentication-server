package com.example.authenticationserver.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class CustomOAuthSuccessController {
    @GetMapping("/oauthSuccessLogin")
    public ResponseEntity<Object> oAuthSuccessLogin() throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("https://www.youtube.com"));
        return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
    }
}
