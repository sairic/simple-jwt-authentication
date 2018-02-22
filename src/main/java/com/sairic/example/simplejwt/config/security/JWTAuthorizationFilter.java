package com.sairic.example.simplejwt.config.security;

import com.sairic.example.simplejwt.service.KeyService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sairic.example.simplejwt.config.security.SecurityEnum.AuthHeader;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.Bearer;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private KeyService keyService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, KeyService keyService) {
        super(authManager);
        this.keyService = keyService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(AuthHeader.val());


        if (header == null || !header.startsWith(Bearer.val())) {
            chain.doFilter(req, res);
            return;
        }
        JWTAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private JWTAuthenticationToken getAuthentication(HttpServletRequest request) {
        String auth = request.getHeader(AuthHeader.val());
        try {
            Jwt token = keyService.parseAuthorization(auth.replace(Bearer.val(), ""));
            return new JWTAuthenticationToken(token);
        } catch(JwtException jwte) {
            return null;
        }

    }
}