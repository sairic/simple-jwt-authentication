package com.sairic.example.simplejwt.config.security;

import com.sairic.example.simplejwt.service.KeyService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private KeyService keyService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, KeyService keyService) {
        this.authenticationManager = authenticationManager;
        this.keyService = keyService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        String username = req.getHeader("X-VC-Auth-Username");
        String password = req.getHeader("X-VC-Auth-Password");
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        String token = keyService.generateJWT( ((User) auth.getPrincipal()).getUsername()  );
        res.addHeader("Authorization", "Bearer " + token);
    }


}
