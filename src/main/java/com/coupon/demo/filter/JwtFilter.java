package com.coupon.demo.filter;

import com.coupon.demo.service.CompanyDetailsService;
import com.coupon.demo.service.CustomerDetailsService;
import com.coupon.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private CompanyDetailsService companyDetailsService;
    private CustomerDetailsService customerDetailsService;


    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setCompanyDetailsService(CompanyDetailsService companyDetailsService) {
        this.companyDetailsService = companyDetailsService;
    }

    @Autowired
    public void setCustomerDetailsService(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = companyDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

//
//                    UserDetails userDetails = companyDetailsService.loadUserByUsername(username);
//            if (jwtUtil.validateToken(token, userDetails)) {
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
//                        = new UsernamePasswordAuthenticationToken(userDetails, null,
//                        userDetails.getAuthorities());
//
//                usernamePasswordAuthenticationToken.setDetails(new
//                WebAuthenticationDetailsSource()
//                        .buildDetails(httpServletRequest));
//
//                SecurityContextHolder
//                        .getContext()
//                        .setAuthentication(usernamePasswordAuthenticationToken);


//                Authentication authentication = SecurityContextHolder.getContext()
//                .getAuthentication();
//
//                Set<String> roles = authentication.getAuthorities().stream()
//                        .map(r -> r.getAuthority()).collect(Collectors.toSet());
//
//                System.out.println(roles);


    }
}
