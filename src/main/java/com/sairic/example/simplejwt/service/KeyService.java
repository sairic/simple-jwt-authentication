package com.sairic.example.simplejwt.service;

import com.sairic.example.simplejwt.config.security.JWTUser;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sairic.example.simplejwt.config.security.SecurityEnum.Scopes;

@Service
public class KeyService {

    private Key key;

    @PostConstruct
    public void init() {
        // In a real production system, this Key Pair would be read from a secure location.
        key = RsaProvider.generateKeyPair(1024).getPrivate();
    }

    public String generateJWT(JWTUser jwtUser) {
        String userName = jwtUser.getUsername();
        Map<String, Object> customClaims = jwtUser.getCustomClaims();
        Collection<GrantedAuthority> authorities = jwtUser.getAuthorities();

        //Add Authorities to the claims section of the JWT
        List<String> roles = authorities.stream().map(grantedAuthority ->
                grantedAuthority.getAuthority()).collect(Collectors.toList());
        customClaims.put(Scopes.val(), roles);

        return Jwts.builder()
                .setSubject(userName)
                .addClaims(customClaims)
                .signWith(SignatureAlgorithm.RS512, key)
                .setExpiration(new Date(System.currentTimeMillis() + 60000000000L))
                .setIssuedAt(new Date())
                .compact();

    }

    public Jwt parseAuthorization(String auth) {
        return Jwts.parser().setSigningKey(key).parse(auth);
    }
}
