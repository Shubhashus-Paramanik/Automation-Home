package com.main.ajarul.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {
 private static final String SECRET="mysecretkeymysecretkeymysecretkey123456";
 private final Key key=Keys.hmacShaKeyFor(SECRET.getBytes());

 public String generateToken(String email){
    return Jwts.builder()
    .subject(email)
    .issuedAt(new Date())
    .expiration(new Date(
        System.currentTimeMillis()+864000000))
        .signWith(key)
        .compact();
 }

 public String extractEmail(String token){
    Claims claims=Jwts.parser()
    .verifyWith((javax.crypto.SecretKey) key)
    .build()
    .parseSignedClaims(token)
    .getPayload();

    return claims.getSubject();

 }

 public boolean isTokenValid(String token){
    try{
        Claims claims=Jwts.parser().verifyWith((javax.crypto.SecretKey) key)
        .build().parseSignedClaims(token)
        .getPayload();

        return claims.getExpiration()
        .after(new Date());
    }catch(Exception e){
        return false;
    }
    
 }
}
