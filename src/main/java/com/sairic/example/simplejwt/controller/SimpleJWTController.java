package com.sairic.example.simplejwt.controller;

import com.sairic.example.simplejwt.config.security.JWTAuthenticationToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/simple")
public class SimpleJWTController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/mydetails")
    public LoggedInUser getMyDetails() {
        JWTAuthenticationToken token = (JWTAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        List<String> rolesList = authorities.stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        return new LoggedInUser((String) token.getPrincipal(), token.getCustomClaims(), rolesList);
    }

    /**
     * This function is used to test authorization with JWT. For this example,
     * The user should not be able to call this function and get back a 403 Forbidden
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/userdetails")
    public void getAllUsersDetails() {
        // For this example, this function will never be used.
    }
}
