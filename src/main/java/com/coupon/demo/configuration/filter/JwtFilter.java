package com.coupon.demo.configuration.filter;

import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.exception.BadClaims;
import com.coupon.demo.exception.BadToken;
import com.coupon.demo.service.details.AdminDetailsService;
import com.coupon.demo.service.details.CompanyDetailsService;
import com.coupon.demo.service.details.CustomerDetailsService;
import com.coupon.demo.utils.JwtUtil;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;


@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CompanyDetailsService companyDetailsService;
    private final CustomerDetailsService customerDetailsService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, CompanyDetailsService companyDetailsService,
                     CustomerDetailsService customerDetailsService,
                     AdminDetailsService adminDetailsService) {
        this.jwtUtil = jwtUtil;
        this.companyDetailsService = companyDetailsService;
        this.customerDetailsService = customerDetailsService;
        this.adminDetailsService = adminDetailsService;
    }

    private UserDetails mapToUserDetails(Claims claims) {
        String scope = (String) claims.get("scope");
        String username = claims.getSubject();
        switch (scope) {
            case "customer":
                return customerDetailsService.loadUserByUsername(username);
            case "company":
                return companyDetailsService.loadUserByUsername(username);
            case "admin":
                return adminDetailsService.loadUserByUsername(username);
            default:
                throw new BadClaims("Bad claims, most likely an undefined scope.");
        }
    }

    private boolean validateToken(Claims claims) {
        return jwtUtil.validateToken(claims, Objects.requireNonNull(mapToUserDetails(claims)));
    }

    private UsernamePasswordAuthenticationToken mapToToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

    }

    private Consumer<UsernamePasswordAuthenticationToken> setContextAuthentication(HttpServletRequest request) {
        return token -> {
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);
        };
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            Optional.ofNullable(request.getHeader("Authorization"))
                    .map(header -> header.substring(7))
                    .map(jwtUtil::decodeJwt)
                    .filter(this::validateToken)
                    .map(this::mapToUserDetails)
                    .map(this::mapToToken)
                    .ifPresent(setContextAuthentication(request));

            chain.doFilter(request, response);

        } catch (JwtException | BadClaims e) {
            log.error(e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json");
            try {
                String responseDTO = new ResponseDTO<>(
                        null, false, e.getMessage())
                        .convertToJson();
                response.getWriter().write(responseDTO);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
