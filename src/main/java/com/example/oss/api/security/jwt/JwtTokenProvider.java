package com.example.oss.api.security.jwt;

import com.example.oss.api.models.User;
import com.example.oss.api.services.User.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String secret;

    private final UserService userService;
    private JwtParser jwtParser;

    @PostConstruct
    private void init() {
        this.jwtParser = Jwts.parser().setSigningKey(secret);
    }

    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) throws AuthException {
        User user = extractUser(token);
        if (user == null) throw new AuthException("user.email.not.found");
        return user.getEmail().equals(extractUsername(token));
    }

    public User extractUser(String token) {
        return userService.loadUserByUsername(extractUsername(token));
    }

    public String extractUsername(String token) {
        return parseJwtClaims(token).getSubject();
    }
}
