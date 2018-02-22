package com.sairic.example.simplejwt.config.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sairic.example.simplejwt.config.security.SecurityEnum.CustomClaim1;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.CustomClaim2;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.Scopes;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private final Object principal;
    Collection authorities;
    Map<String, Object> customClaims = new HashMap<>(2);

    public JWTAuthenticationToken( Jwt jwtToken) {
        super(null);
        super.setAuthenticated(true);

        DefaultClaims claims = (DefaultClaims) jwtToken.getBody();
        String subject = (String) claims.get("sub");
        String customClaimValue1 = (String) claims.get(CustomClaim1.val());
        String customClaimValue2 = (String) claims.get(CustomClaim2.val());
        List<String> scopes = (List<String>) claims.get(Scopes.val());

        customClaims.put(CustomClaim1.val(), customClaimValue1);
        customClaims.put(CustomClaim2.val(), customClaimValue2);

        this.principal = subject;
        this.authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Map<String, Object> getCustomClaims() {
        return customClaims;
    }
}
