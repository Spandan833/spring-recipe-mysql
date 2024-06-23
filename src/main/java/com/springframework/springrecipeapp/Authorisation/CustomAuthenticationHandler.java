package com.springframework.springrecipeapp.Authorisation;

import com.springframework.springrecipeapp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationHandler
        implements AuthenticationSuccessHandler {
    private final UserService userService;

    public CustomAuthenticationHandler(UserService userService) {
        this.userService = userService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        System.out.println("Logged user: " + authentication.getName());

        long userId = userService.findUserByEmail(authentication.getName()).getId();

        response.sendRedirect("/");
    }
}
