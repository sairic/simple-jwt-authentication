package com.sairic.example.simplejwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class SimpleJwtApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testJWTEncryption() throws Exception {
		KeyPair kp = RsaProvider.generateKeyPair(1024);
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		Map<String, Object> claims = new HashMap<>(3);
		claims.put("iss", "joe");
		claims.put("exp", System.currentTimeMillis() + 100);

		String jwt = Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.RS512, privateKey)
				.setExpiration(new Date(System.currentTimeMillis() + 30000))
				.setIssuedAt(new Date())
				.compact();
		Jwt token = Jwts.parser().setSigningKey(privateKey).parse(jwt);
		System.out.println("DONE");
	}

}
