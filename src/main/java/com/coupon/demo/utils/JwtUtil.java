package com.coupon.demo.utils;

import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.model.Scope.*;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.coupon.demo.model.Scope.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

@Service
public class JwtUtil {

    public Function<Claims, Object> extractScope = claims -> {
        if (claims.get("scope") != null) {
            return claims.get("scope");
        }
        throw new RuntimeException("Invalid claims " + claims.get("scope"));
    };
    public Function<Claims, Object> extractName = claims -> {
        if (claims.getSubject() != null) {
            return claims.getSubject();
        }
        throw new RuntimeException("Invalid claims " + claims.get("name"));
    };
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;
    @Value("${secret}")
    private String SECRET_KEY;
    public Function<String, Long> extractID = authHeader -> {
        Claims claims = decodeJwt(authHeader.substring(7));
        if (claims.getId() != null) {
            try {
                return Long.parseLong(claims.getId());
            } catch (Exception e) {
                throw new RuntimeException("Bad claims" + e.getMessage());
            }
        }
        throw new RuntimeException("Invalid claims " + claims.get("id"));
    };

    @Autowired
    public JwtUtil(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    public String encodeJwt(AuthRequest authRequest, Scope scope) {
        Long id = findId(authRequest, scope);

        byte[] secretBytes = parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(secretBytes, HS256.getJcaName());
        Map<String, Object> claims = Map.of(
                "scope", scope.name().toLowerCase(),
                "name", authRequest.getUsername(),
                "id", id
        );

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(120, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setIssuer("coupon~Project ")
                .setId(id.toString())
                .setSubject(authRequest.getUsername())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .claim("scope", scope.name().toLowerCase())
                .signWith(HS256, key)
                .compact();

//        return Jwts.builder()
//                .setClaims(claims)
//                .signWith(HS256, key)
//                .compact();
    }

    public Claims decodeJwt(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token decoding problem: " + e.getMessage());
        }
        return claims;
    }

    public boolean validateToken(Claims claims, UserDetails userDetails) {
        return extractName.apply(claims).equals(userDetails.getUsername());
    }

    private Long findId(AuthRequest authRequest, Scope scope) {
        Long id;
        switch (scope) {
            case COMPANY:
                id = companyRepository.findByEmail(authRequest.getUsername()).get().getId();
                break;
            case CUSTOMER:
                id = customerRepository.findByEmail(authRequest.getUsername()).get().getId();
                break;
            case ADMIN:
                id = 1L;
                break;
            default:
                throw new RuntimeException("Undefined Scope" + scope);
        }
        return id;
    }
}
