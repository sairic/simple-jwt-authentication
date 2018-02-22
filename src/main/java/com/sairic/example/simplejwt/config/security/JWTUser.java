package com.sairic.example.simplejwt.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

public class JWTUser implements UserDetails {

    private String password;
    private final String username;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> customClaims;

    public JWTUser(String username, String password, Collection<GrantedAuthority> authorities, Map<String, Object> customClaims) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.customClaims = customClaims;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Map<String, Object> getCustomClaims() {
        return customClaims;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
