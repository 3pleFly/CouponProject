package com.coupon.demo.controllers;

import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public CompanyController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println(authRequest);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException( "invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }


    @GetMapping("/companies")
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    public ResponseEntity<String> getAllCompanies() {
//        List<Company> companies = adminFacade.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "all companies...");
    }

    @GetMapping("/try")
//    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<String> meTry() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "all companies...");
    }


}
