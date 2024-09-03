package com.example.MyProject.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(UserDetails userDetails) {
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        String role = userDetails.getAuthorities().stream()
	                        .map(GrantedAuthority::getAuthority)
	                        .findFirst()
	                        .orElse("ROLE_USER"); // Default role if no roles found

	        String token = JWT.create()
	                .withIssuer("auth-api")
	                .withSubject(userDetails.getUsername()) // Consider using `getUsername()` or `getEmail()`
	                .withClaim("roles", List.of(role))  // Include user roles or other claims if needed
	                .withExpiresAt(generateExpirationDate())
	                .sign(algorithm);

	        return token;
	    } catch (JWTCreationException exception) {
	        throw new RuntimeException("Error while generating token", exception);
	    }
	}



	    public String validateToken(String token) {
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(secret);
	            return JWT.require(algorithm)
	                    .withIssuer("auth-api")
	                    .build()
	                    .verify(token)
	                    .getSubject();
	        } catch (JWTVerificationException exception) {
	            return "";
	        }
	    }

	    private Instant generateExpirationDate() {
	        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	    }
}
