package com.example.authenticationserver.oAuth;

import com.example.authenticationserver.entites.JwtResponse;
import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.enums.OAuthProvider;
import com.example.authenticationserver.enums.Role;
import com.example.authenticationserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String name = oAuth2User.getName();
        User user = userRepository.findByEmail(email);
        if(user == null){
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setRole(Role.USER);
            newUser.setProvider(OAuthProvider.GOOGLE);
            userRepository.save(newUser);
        }else{
            user.setName(name);
            user.setProvider(OAuthProvider.GOOGLE);
        }
//        response.addHeader("email", user.getEmail());
//        response.sendRedirect("http://localhost:8081/login");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JwtResponse> responseEntity = restTemplate.exchange("http://localhost:8081/login", HttpMethod.POST, new HttpEntity<>(user), JwtResponse.class);

        response.addHeader("authorization", responseEntity.getBody().getJwtToken());
        response.sendRedirect("http://localhost:8081/dashboard");

        super.onAuthenticationSuccess(request,response, authentication);
    }
}
