package com.sairic.example.simplejwt.controller;

import com.sairic.example.simplejwt.config.security.JWTAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hello")
public class SimpleJWTController {

    @GetMapping("/world")
    public LoggedInUser helloWord() {
        JWTAuthenticationToken token = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        List<String> rolesList = authorities.stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        return new LoggedInUser((String) token.getPrincipal(), token.getCustomClaims(), rolesList);
    }
}
