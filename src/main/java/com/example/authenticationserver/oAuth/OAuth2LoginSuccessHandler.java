package com.example.authenticationserver.oAuth;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.enums.OAuthProvider;
import com.example.authenticationserver.enums.Role;
import com.example.authenticationserver.repositories.UserRepository;
import com.example.authenticationserver.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService customUserDetails;
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

        final UserDetails userDetails = customUserDetails.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        response.setHeader("authorization", token);
        response.sendRedirect("http://localhost:8081/oauthLogin/?token=" + token);
        super.onAuthenticationSuccess(request,response, authentication);
    }
}
