package com.sairic.example.simplejwt.config.security;

import com.sairic.example.simplejwt.service.KeyService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


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
        String header = req.getHeader("Authorization");


        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        try {
            Jwt token = keyService.parseAuthorization(auth.replace("Bearer ", ""));
            DefaultClaims claims = (DefaultClaims) token.getBody();
            String subject = (String) claims.get("sub");
            String customClaimValue = (String) claims.get("customClaim");
            return new UsernamePasswordAuthenticationToken("sairic", null, new ArrayList<>());
        } catch(JwtException jwte) {
            return null;
        }

    }
}