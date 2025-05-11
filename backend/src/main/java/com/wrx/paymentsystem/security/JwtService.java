package com.wrx.paymentsystem.security;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final String secretKey = "your-secret-key"; // Replace with your secret key

    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    // Generate a token for the user
    public String generateToken(String username) {
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, key)  // Use SecretKey with signWith
                .compact();
    }

    // Validate the token and get the username
    public String extractUsername(String token) {
        JwtParser parser = Jwts.parser(); // Use Jwts.parser() instead of parserBuilder()
        Claims claims = parser
                .setSigningKey(secretKey.getBytes())  // Use byte array for the signing key
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        JwtParser parser = Jwts.parser(); // Use Jwts.parser() instead of parserBuilder()
        Claims claims = parser
                .setSigningKey(secretKey.getBytes())  // Use byte array for the signing key
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
