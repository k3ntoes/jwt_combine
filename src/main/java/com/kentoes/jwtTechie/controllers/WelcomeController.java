package com.kentoes.jwtTechie.controllers;

import com.kentoes.jwtTechie.dto.AuthRequest;
import com.kentoes.jwtTechie.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class WelcomeController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping
    String welcome() {
        return "Welcome to JWT rest api!!!";
    }

    @GetMapping("/public")
    String publicPage() {
        return "This is public page!";
    }

    @PostMapping("/auth")
    ResponseEntity<?> generateToken(@Valid @RequestBody AuthRequest request, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.ok(errors);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(jwtUtil.generateToken(request.getUsername()));
    }

    @GetMapping("/admin")
    String adminPage() {
        return "this is admin page only";
    }

    @GetMapping("/user")
    String userPage() {
        return "this is user page only";
    }
}
