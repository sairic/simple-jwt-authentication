/**
 * This is only for demo purposes. In actual production code,
 * it would communicate with some backend service to validate credentials
 */

package com.sairic.example.simplejwt.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SimpleJWTUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("sairic".equals(username)) {

            //Lets simulate a call to the database which only has one valid user with Password = Password1
            return new User("sairic", "$2a$08$PaCV42RGYNafEN7Eysl5aepVyh38DWqB2r90OB4T0CS6ck2.kkEvG", new ArrayList<>());
        }
        throw new UsernameNotFoundException(username);
    }
}
