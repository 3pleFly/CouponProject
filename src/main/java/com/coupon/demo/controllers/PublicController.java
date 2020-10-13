package com.coupon.demo.controllers;

import com.coupon.demo.dto.Login;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import com.coupon.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @PostMapping("/admin")
    public String authenticateAdmin(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "invalid username/password");
        }
        return jwtUtil.encodeJwt(authRequest, Scope.ADMIN);
    }

    @PostMapping("/company")
    public String authenticateCompany(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "invalid username/password");
        }
        return jwtUtil.encodeJwt(authRequest, Scope.COMPANY);
    }

    @PostMapping("/customer")
    public String authenticateCustomer(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "invalid username/password");
        }
        return jwtUtil.encodeJwt(authRequest, Scope.CUSTOMER);
    }
}


