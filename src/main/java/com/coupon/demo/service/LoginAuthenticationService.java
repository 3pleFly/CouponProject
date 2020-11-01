package com.coupon.demo.service;

import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor
@Component
public class LoginAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseDTO<String> authenticate(AuthRequest authRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()));
        return encodeJwtByAuthority(authRequest, authentication);
    }

    private ResponseDTO<String> encodeJwtByAuthority(AuthRequest authRequest,
                                                     Authentication authentication) {
        String jwt;
        ResponseDTO<String> responseDTO;
        switch (authentication.getAuthorities().toString()) {
            case "[ROLE_ADMIN]":
                jwt = jwtUtil.encodeJwt(authRequest, Scope.ADMIN);
                responseDTO = new ResponseDTO<>(
                        jwt, true, "Token generated successfully");
                return responseDTO;
            case "[ROLE_COMPANY]":
                jwt = jwtUtil.encodeJwt(authRequest, Scope.COMPANY);
                responseDTO = new ResponseDTO<>(
                        jwt, true, "Token generated successfully");
                return responseDTO;
            case "[ROLE_CUSTOMER]":
                jwt = jwtUtil.encodeJwt(authRequest, Scope.CUSTOMER);
                responseDTO = new ResponseDTO<>(
                        jwt, true, "Token generated successfully");
                return responseDTO;
            default:
                log.warn("Cannot find the proper Role/Authority");
                responseDTO = new ResponseDTO<>(
                        null, false, "No authority");
                responseDTO.setHttpErrorCode(401);
                return responseDTO;
        }
    }

}
