package com.coupon.demo.utils;

import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.model.Scope.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Map;
import java.util.function.Function;

import static com.coupon.demo.model.Scope.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

@Service
public class JwtUtil {

    @Value("${secret}")
    private String SECRET_KEY;

    public Function<Claims, Object> extractScope = claims -> {
        if (claims.get("scope") != null) {
            return claims.get("scope");
        }
        throw new RuntimeException("Invalid claims");
    };

    public Function<Claims, Object> extractName = claims -> {
        if (claims.get("name") != null) {
            return claims.get("name");
        }
        throw new RuntimeException("Invalid claims");
    };

  public String encodeJwt(AuthRequest authRequest, Scope scope) {
        byte[] secretBytes = parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(secretBytes, HS256.getJcaName());
        Map<String, Object> claims = Map.of(
                "scope", scope.name().toLowerCase(),
                "name", authRequest.getUsername()
        );
        return Jwts.builder()
                .setClaims(claims)
                .signWith(HS256, key)
                .compact();
    }

    public Claims decodeJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean validateToken(Claims claims, UserDetails userDetails) {
        return extractName.apply(claims).equals(userDetails.getUsername());
    }
}
