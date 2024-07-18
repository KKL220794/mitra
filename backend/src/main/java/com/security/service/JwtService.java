package com.security.service;

import com.security.model.User;
import com.security.repo.TokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private String SECRET_KEY = "7083df6078d40621ea218646968f981812026231a3772835c6390873f062c801";

    private final TokenRepo tokenRepo;

    public JwtService(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public User extractUser(String token){
        return tokenRepo.findByToken(token).map(t->t.getUser()).orElse(null);
    }

    public boolean isValid(String token, UserDetails user){
        final String username = extractUsername(token);

        boolean isvalidToken = tokenRepo.findByToken(token).map(t->!t.isIs_logged_out()).orElse(false);

        return username.equals(user.getUsername()) && !isTokenExpired(token) && isvalidToken;
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){
        String token = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(KeyBytes);
    }
}
