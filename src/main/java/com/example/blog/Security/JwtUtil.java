package com.example.blog.Security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long Expiration_time= 1000*60*60*10;

    //Generate token using email
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+Expiration_time))
                .signWith(key)
                .compact();
    }
    //Extract email using token
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Boolean isTokenValid(String token){
        try{ Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
                return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
