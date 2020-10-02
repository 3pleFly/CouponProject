package com.coupon.demo.filter;

import com.coupon.demo.model.Scope;
import com.coupon.demo.service.details.AdminDetailsService;
import com.coupon.demo.service.details.CompanyDetailsService;
import com.coupon.demo.service.details.CustomerDetailsService;
import com.coupon.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CompanyDetailsService companyDetailsService;
    private final CustomerDetailsService customerDetailsService;
    private final AdminDetailsService adminDetailsService;


    private UserDetails mapToUserDetails(Claims claims) {
        Object scope = jwtUtil.extractScope.apply(claims);
        Object username = jwtUtil.extractName.apply(claims);
        switch (scope.toString()) {
            case "customer":
                return customerDetailsService.loadUserByUsername(username.toString());
            case "company":
                return companyDetailsService.loadUserByUsername(username.toString());
            case "admin":
                return adminDetailsService.loadUserByUsername(username.toString());
            default:
                throw new RuntimeException("Undefined scope");
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

    private Consumer<UsernamePasswordAuthenticationToken> setContextAuth(HttpServletRequest request) {
      return authToken -> {
            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        };
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader("Authorization"))
                .map(header -> header.substring(7))
                .map(jwtUtil::decodeJwt)
                .filter(this::validateToken)
                .map(this::mapToUserDetails)
                .map(this::mapToToken)
                .ifPresent(setContextAuth(request));
        chain.doFilter(request, response);
    }


}
