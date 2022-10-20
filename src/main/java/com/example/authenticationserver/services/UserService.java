package com.example.authenticationserver.services;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.enums.OAuthProvider;
import com.example.authenticationserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signupUser(User user){
        return userRepository.save(user);
    }

    public User signupOAuthUser(OAuthProvider provider){
        User user = new User();
        user.setProvider(provider);
        return userRepository.save(user);
    }

    public User findUserByEmail(String email){
       return userRepository.findByEmail(email);
    }

}
