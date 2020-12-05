package com.coupon.demo.service;

import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.model.entities.Company;
import com.coupon.demo.model.entities.Customer;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Slf4j
@AllArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    /**
     * First trying to authenticate the authRequest and extracting the authentication instance.
     * Then, generating a JWT based on the authentication.getAuthorities for the proper scope.
     *
     * @param authRequest The client's username and password.
     * @return a JWT string.
     */
    public ResponseDTO<String> authenticate(AuthRequest authRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));
        return encodeJwtByAuthority(authRequest, authentication);
    }

    private ResponseDTO<String> encodeJwtByAuthority(AuthRequest authRequest, Authentication authentication) {
        String jwt;
        ResponseDTO<String> responseDTO;
        switch (authentication.getAuthorities().toString()) {
            case "[ADMIN]":
                jwt = jwtService.encodeJwt(authRequest, Scope.ADMIN);
                responseDTO = new ResponseDTO<>(jwt, true, "Token generated successfully");
                return responseDTO;
            case "[COMPANY]":
                jwt = jwtService.encodeJwt(authRequest, Scope.COMPANY);
                responseDTO = new ResponseDTO<>(jwt, true, "Token generated successfully");
                return responseDTO;
            case "[CUSTOMER]":
                jwt = jwtService.encodeJwt(authRequest, Scope.CUSTOMER);
                responseDTO = new ResponseDTO<>(jwt, true, "Token generated successfully");
                return responseDTO;
            default:
                log.warn("Cannot find the proper Role/Authority");
                responseDTO = new ResponseDTO<>(null, false, "No authority");
                responseDTO.setHttpErrorCode(401);
                return responseDTO;
        }
    }

    public Long getPrincipalID() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String scope = userDetails.getAuthorities().toString();
            if (scope.equals("[COMPANY]")) {
                Company company = companyRepository.findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new NotFound("Cannot find user-email"));
                return company.getId();
            } else {
                Customer customer = customerRepository.findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new NotFound("Cannot find user-email"));
                return customer.getId();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
