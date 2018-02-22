package com.sairic.example.simplejwt.config;

import com.sairic.example.simplejwt.config.security.JWTAuthenticationFilter;
import com.sairic.example.simplejwt.config.security.JWTAuthorizationFilter;
import com.sairic.example.simplejwt.config.security.SimpleJWTUserDetailsService;
import com.sairic.example.simplejwt.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SimpleJWTWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    SimpleJWTUserDetailsService userDetailsService;

    @Autowired
    KeyService keyService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), keyService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), keyService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
