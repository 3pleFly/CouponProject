package com.coupon.demo.controllers;

import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @GetMapping("/companies")
    public ResponseEntity<String> getAllCompanies() {
//        List<Company> companies = adminFacade.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "all companies...");
    }

    @GetMapping("/try")
    public ResponseEntity<String> meTry() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "all companies...");
    }


}
