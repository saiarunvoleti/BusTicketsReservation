package com.example.bus.security;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.bus.utils.UtilClass;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;


@Configuration
public class JwtUtil {
	
	private static final String SECRET_KEY = "f28ef29ff32e48bad5f32a4b6762a45db49e9665506e7d89947d81fcb24f6ab5";

    @SuppressWarnings("deprecation")
	public String generateToken(String email, String usertype) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", usertype);

        byte[] decodedKey = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        Key hmacKey = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());
        
        System.out.println(decodedKey.length);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    @SuppressWarnings("deprecation")
    public String getRoleFromToken(String token) {
    	byte[] decodedKey = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    	Key hmacKey = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
    
    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
}
