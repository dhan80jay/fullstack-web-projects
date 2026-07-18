package com.dhananjay.hospitalmanagement.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

	public String generateToken(Users user) {
 		return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000*60*10))
				.signWith(getKey())
				.compact();
				
	}

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String extractUserNameFromToken(String authHeader) {
		 Claims claims = Jwts.parser()
				 .verifyWith(getKey())
				 .build()
				 .parseSignedClaims(authHeader)
				 .getPayload();
		 
		 return claims.getSubject();
	}

	public Date extractExpiration(String token) {

	    Claims claims = Jwts.parser()
	            .verifyWith(getKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();

	    return claims.getExpiration();
	}
	
	public boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
 		String userName = extractUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
