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

import static com.sairic.example.simplejwt.config.security.SecurityEnum.AuthHeader;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.Bearer;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.PasswordHeader;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.UserNameHeader;

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
        String username = req.getHeader(UserNameHeader.val());
        String password = req.getHeader(PasswordHeader.val());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        JWTUser user = (JWTUser) auth.getPrincipal();
        String token = keyService.generateJWT(user);
        res.addHeader(AuthHeader.val(), Bearer.val() + token);
    }


}
