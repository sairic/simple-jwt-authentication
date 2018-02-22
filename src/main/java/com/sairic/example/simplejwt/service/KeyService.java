package com.sairic.example.simplejwt.service;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Service
public class KeyService {

    private Key key;

    @PostConstruct
    public void init() {
        key = RsaProvider.generateKeyPair(1024).getPrivate();
    }

    public String generateJWT(String userName) {

        return Jwts.builder()
                .setSubject(userName)
                .claim("customClaim", "customClaimValue")
                .signWith(SignatureAlgorithm.RS512, key)
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .setIssuedAt(new Date())
                .compact();

    }

    public Jwt parseAuthorization(String auth) {
        return Jwts.parser().setSigningKey(key).parse(auth);
    }
}
