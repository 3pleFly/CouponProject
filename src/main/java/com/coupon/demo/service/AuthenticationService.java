package com.coupon.demo.service;

import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Slf4j
@AllArgsConstructor
@Service
public class LoginAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    /**
     * First trying to authenticate the authRequest and extracting the authentication instance.
     * Then, generating a JWT based on the authentication.getAuthorities for the proper scope.
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

    private ResponseDTO<String> encodeJwtByAuthority(AuthRequest authRequest,
                                                     Authentication authentication) {
        String jwt;
        ResponseDTO<String> responseDTO;
        switch (authentication.getAuthorities().toString()) {
            case "[ADMIN]":
                jwt = jwtService.encodeJwt(authRequest, Scope.ADMIN);
                responseDTO = new ResponseDTO<>(
                        jwt, true, "Token generated successfully");
                return responseDTO;
            case "[COMPANY]":
                jwt = jwtService.encodeJwt(authRequest, Scope.COMPANY);
                responseDTO = new ResponseDTO<>(
                        jwt, true, "Token generated successfully");
                return responseDTO;
            case "[CUSTOMER]":
                jwt = jwtService.encodeJwt(authRequest, Scope.CUSTOMER);
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
