package com.sairic.example.simplejwt.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.sairic.example.simplejwt.config.security.SecurityEnum.CustomClaim1;
import static com.sairic.example.simplejwt.config.security.SecurityEnum.CustomClaim2;

@Service
public class SimpleJWTUserDetailsService implements UserDetailsService {

    /**
     * In a real production application, this method would talk to some Authentication Service that validates
     * against a Database, LDAP or some other mechanism. For Demo, we will only use hard coded mock data.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("sairic".equals(username)) {

            //User has some custom data we'd like to add to the JWT
            Map<String, Object> customClaims = new HashMap<String, Object>()
            {{
                put(CustomClaim1.val(), "SomeCustomClaim1");
                put(CustomClaim2.val(), "SomeCustomClaim2");
            }};

            //Lets simulate a call to the database which only has one valid user with Password = Password1
            return new JWTUser("sairic", "$2a$08$PaCV42RGYNafEN7Eysl5aepVyh38DWqB2r90OB4T0CS6ck2.kkEvG",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), customClaims);
        }
        throw new UsernameNotFoundException(username);
    }
}
