package com.delpipi.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Service
public class JwtTokenService {

    @Value("${jwt-secret-key}")
    private String jwtSecretKey;

    @Value("${jwt-expired-time}")
    private long jwtExpiredTime;

    //Specify the desired HMAC-SHA algorithm
    SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    //Generate a secure key for the chosen algorithm
    byte[] keyBytes = Keys.secretKeyFor(algorithm).getEncoded();
    
    private Key key(){
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token ){
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isExpiredToken(String token){
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String username){
        return Jwts.builder().setClaims(claims).setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiredTime))
        .signWith(key()).compact();
    }

    public String generateToken(UserDetails UserDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, UserDetails.getUsername());
    }
    
    public Boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpiredToken(token));
    }
}
