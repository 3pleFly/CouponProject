package com.coupon.demo.utils;

import com.coupon.demo.exception.BadClaims;
import com.coupon.demo.exception.BadToken;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.model.Scope.*;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
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
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final String SECRET_KEY;

    public Function<String, Long> extractID = authHeader -> {
        try {
            Claims claims = decodeJwt(authHeader.substring(7));
            return Long.parseLong(claims.getId());
        } catch (Exception e) {
            throw new BadClaims("Invalid claims ID");
        }
    };

    @Autowired
    public JwtUtil(CustomerRepository customerRepository, CompanyRepository companyRepository,
                   @Value("${secret}")String SECRET_KEY) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.SECRET_KEY = SECRET_KEY;
    }

    public String encodeJwt(AuthRequest authRequest, Scope scope) {
        Long id = findId(authRequest, scope);

        byte[] secretBytes = parseBase64Binary(SECRET_KEY);
        Key key = new SecretKeySpec(secretBytes, HS256.getJcaName());
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(120, ChronoUnit.DAYS);
        return Jwts.builder()
                .setIssuer("coupon~Project ")
                .setId(id.toString())
                .setSubject(authRequest.getUsername())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .claim("scope", scope.name().toLowerCase())
                .signWith(HS256, key)
                .compact();
    }

    public Claims decodeJwt(String jwt) {
        try {
            Claims claims = null;
            claims = Jwts.parser()
                    .setSigningKey(parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token is expired");

        } catch (MalformedJwtException e) {
            throw new JwtException("Token is malformed");

        } catch (SignatureException e) {
            throw new JwtException("Wrong Jwt signature");

        } catch (PrematureJwtException e) {
            throw new JwtException("Token has not been activated yet");

        } catch (UnsupportedJwtException e) {
            throw new JwtException("Token is unsupported");

        } catch (JwtException e) {
            throw new JwtException("Bad Jwt");
        }
    }

    public boolean validateToken(Claims claims, UserDetails userDetails) {
        return claims.getSubject().equals(userDetails.getUsername());
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
                    id = null;
            }
        return id;
    }
}
