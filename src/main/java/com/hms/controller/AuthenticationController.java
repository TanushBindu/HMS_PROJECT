package com.hms.controller;

import com.hms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/authenticate")
    public String login() {
        return "login";
    }

    @PostMapping("/perform_login")
    public void loginRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Spring Security handles authentication automatically
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()) {
            String role = userService.getRole(auth.getName());
            switch(role) {
                case "ADMIN":
                    response.sendRedirect("/dashboard");
                    break;
                case "DOCTOR":
                    response.sendRedirect("/dashboard");
                    break;
                case "PATIENT":
                    response.sendRedirect("/dashboard");
                    break;
                default:
                    response.sendRedirect("/login?error");
            }
        } else {
            response.sendRedirect("/login?error");
        }
    }
}
