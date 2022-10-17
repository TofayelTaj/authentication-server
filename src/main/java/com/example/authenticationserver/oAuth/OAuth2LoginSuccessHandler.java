package com.example.authenticationserver.oAuth;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.enums.OAuthProvider;
import com.example.authenticationserver.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();
        User user = userRepository.findByEmail(email);
        if(user == null){
//            new user
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setProvider(OAuthProvider.GOOGLE);
        }else{
//            existing user
            user.setName(name);
            user.setProvider(OAuthProvider.GOOGLE);
        }
    }
}
