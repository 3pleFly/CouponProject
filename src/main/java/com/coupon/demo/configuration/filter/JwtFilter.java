package com.coupon.demo.configuration.filter;

import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.exception.BadScope;
import com.coupon.demo.model.Scope;
import com.coupon.demo.service.JwtService;
import com.coupon.demo.service.details.AdminDetailsService;
import com.coupon.demo.service.details.CompanyDetailsService;
import com.coupon.demo.service.details.CustomerDetailsService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;


@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CompanyDetailsService companyDetailsService;
    private final CustomerDetailsService customerDetailsService;
    private final AdminDetailsService adminDetailsService;

    @Autowired
    public JwtFilter(JwtService jwtService, CompanyDetailsService companyDetailsService,
                     CustomerDetailsService customerDetailsService,
                     AdminDetailsService adminDetailsService) {
        this.jwtService = jwtService;
        this.companyDetailsService = companyDetailsService;
        this.customerDetailsService = customerDetailsService;
        this.adminDetailsService = adminDetailsService;
    }

    /**
     * Using the claims we extract the 'scope' and 'subject' attribute
     * Use a switch(scope) to call an appropriate userDetailsService(adminDetailsService,
     * companyDetailsService or customerDetailsService).
     * return the loadUserByUsername method, giving it the extracted aforementioned 'subject'.
     *
     * @param claims which were extract from the JWT.
     * @return an implemented userDetails that could be AdminDetails/CustomerDetails/CompanyDetails.
     */
    private UserDetails mapToUserDetails(Claims claims) {
        String claimsScope = (String) claims.get("scope");
        Scope scope = Scope.valueOf(claimsScope.toUpperCase());
        String username = claims.getSubject();
        switch (scope) {
            case CUSTOMER:
                return customerDetailsService.loadUserByUsername(username);
            case COMPANY:
                return companyDetailsService.loadUserByUsername(username);
            case ADMIN:
                return adminDetailsService.loadUserByUsername(username);
            default:
                throw new BadScope("Undefined scope.");
        }
    }

    /**
     * Creating and returning a new instance of UsernamePasswordAuthenticationToken with the
     * userDetails and userDetails.getAuthorities.
     *
     * @param userDetails the loaded userDetails.
     */
    private UsernamePasswordAuthenticationToken mapToToken(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    /**
     * Informing Spring Security that the UsernamePasswordAuthenticationToken is authenticated.
     */
    private void setContextAuthentication(UsernamePasswordAuthenticationToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * Using Optional.ofNullable to extract "Authorization" header, if null the function chain is
     * ignored, chain.doFilter is called.
     * Uses jwtService to decode and validate the JWT and extract the Claims object.
     * Uses mapToUserDetails() to create an appropriate UserDetails implementation and then
     * continues
     * to mapToToken() to create a UsernameAndPasswordAuthenticationToken, lastly we enter
     * setContextAuthentication() to include the token into the SecurityContext.
     *
     * Caught exceptions are handled by writing the appropriate error on the HttpResponseServlet.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            if (request.getHeader("Authorization") != null) {
                String jwt = request.getHeader("Authorization").substring(7);
                Claims claims = jwtService.decodeJwt(jwt);
                UserDetails userDetails = mapToUserDetails(claims);
                if (jwtService.validateClaims(claims, userDetails)) {
                    UsernamePasswordAuthenticationToken token = mapToToken(userDetails);
                    setContextAuthentication(token);
                }
            }
            chain.doFilter(request, response);
        } catch (JwtException | BadScope e) {
            log.error(e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json");
            try {
                String responseDTO = new ResponseDTO<>(null, false, e.getMessage())
                        .convertToJson();
                response.getWriter().write(responseDTO);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
