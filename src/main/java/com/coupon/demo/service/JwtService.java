package com.coupon.demo.service;

import com.coupon.demo.exception.BadClaims;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;


@Service
public class JwtService {
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final String SECRET_KEY;

    @Autowired
    public JwtService(CustomerRepository customerRepository, CompanyRepository companyRepository,
                      @Value("${secret}")String SECRET_KEY) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.SECRET_KEY = SECRET_KEY;
    }

    /**
     * This method is called for creating a JWT out of the following:
     * Issuer: The project name("coupon~Project")
     * ID: The user's ID according to the database
     * Subject: The user's email.
     * IssuedAt: Time right now truncated to seconds.
     * Expiration: IssuedAt + 120 days.
     * scope: The principal's authority.
     *
     * Calling findId() to fetch the user's ID from the database.
     * creating secretBytes by parsing SECRET_KEY into bytes using a Base64Binary parser.
     * secretBytes is used to generate a Key object using HS256 hashing algorithm.
     * @param authRequest an Authenticated(by this point) authRequest containing a username and
     *                    password.
     * @param scope pertaining to the principal's authorities.
     * @return a JWT string.
     */
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
                .claim("scope", scope.name())
                .signWith(HS256, key)
                .compact();
    }

    /**
     * Decoding JWT using the SECRET_KEY.
     */
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

    /**
     * @param claims to extract subject from
     * @param userDetails to extract username from.
     * @return boolean if subject & username match.
     */
    public boolean validateClaims(Claims claims, UserDetails userDetails) {
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
