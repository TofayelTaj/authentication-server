package com.example.authenticationserver.filters;

import com.example.authenticationserver.entites.User;
import com.example.authenticationserver.security.CustomUserDetails;
import com.example.authenticationserver.services.UserService;
import com.example.authenticationserver.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestAuthFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean isValidRequest = false;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("authorization");
        System.out.println("Reaquest path from filter : " + request.getContextPath());
        String path = request.getHeader("requestPath").substring(1);
        String name = null;
        if (token != null) {
            name = jwtUtil.getUsernameFromToken(token.substring(7));
            User user = userService.findUserByEmail(name);
            UserDetails userDetails = new CustomUserDetails(user);
            boolean isTokenValid = jwtUtil.validateToken(token.substring(7), userDetails);
            if (!isTokenValid) {
                isValidRequest = false;
            } else if (path.startsWith("admin") && user.getRole().toString().equalsIgnoreCase("admin")) {
                isValidRequest = true;
            } else if (path.startsWith("user") && user.getRole().toString().equalsIgnoreCase("user")) {
                isValidRequest = true;
            }
        }
        if (isValidRequest) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}
