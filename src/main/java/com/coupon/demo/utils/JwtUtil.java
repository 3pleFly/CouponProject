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
import java.util.Map;
import java.util.function.Function;

import static com.coupon.demo.model.Scope.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

@Service
public class JwtUtil {

    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public JwtUtil(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Value("${secret}")
    private String SECRET_KEY;

    public Function<Claims, Object> extractScope = claims -> {
        if (claims.get("scope") != null) {
            return claims.get("scope");
        }
        throw new RuntimeException("Invalid claims " + claims.get("scope"));
    };

    public Function<Claims, Object> extractName = claims -> {
        if (claims.get("name") != null) {
            return claims.get("name");
        }
        throw new RuntimeException("Invalid claims " + claims.get("name"));
    };

    public Function<String, Integer> extractID = authHeader -> {
        Claims claims = decodeJwt(authHeader.substring(7));
        if (claims.get("id") != null) {
            try {
                return (Integer) claims.get("id");
            } catch (Exception e) {
                throw new RuntimeException("Bad claims" + e.getMessage());
            }
        }
        throw new RuntimeException("Invalid claims " + claims.get("id"));
    };

  public String encodeJwt(AuthRequest authRequest, Scope scope) {
      Long id = findId(authRequest, scope);

      byte[] secretBytes = parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(secretBytes, HS256.getJcaName());
        Map<String, Object> claims = Map.of(
                "scope", scope.name().toLowerCase(),
                "name", authRequest.getUsername(),
                "id", id
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
