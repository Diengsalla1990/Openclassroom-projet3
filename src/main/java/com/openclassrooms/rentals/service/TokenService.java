package com.openclassrooms.rentals.service;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.encoder = jwtEncoder;
        this.decoder = jwtDecoder;
    }

    
    /**
     * Generation du token
     * @param authenticationEmail
     * @return token encoder avec email
     */

    public String generateToken(String authenticationEmail){
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(8, ChronoUnit.HOURS)) //Token expires in 8 hours
                .subject(authenticationEmail)
                //.claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

   
    /**
     * l'extraction de l'email du token
     * @param token
     * @return
     */
    public String getEmailFromToken(String token){
        token = deleteBearerFromToken(token); 
        if (token != null) {
            Jwt claims = this.decoder.decode(token);
            return claims.getSubject();
        } else {
            return "";
        }
    }

    
    /**
     * suppression de "Bearer" du token
     * @param token
     * @return
     */
    public String deleteBearerFromToken(String token) {
        if (token != null) {
            if(token.startsWith("Bearer ")) return token.substring(7);
        }
        return null;
    }
}