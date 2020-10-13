package com.coupon.demo.controllers;

import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.dto.Login;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminFacade adminFacade;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;


    @Autowired
    public AdminController(AdminFacade adminFacade,
                           JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.adminFacade = adminFacade;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "invalid username/password");
        }
        return jwtUtil.encodeJwt(authRequest, Scope.ADMIN);
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(@RequestBody Login login) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Login successful!");
    }

    @PostMapping("/addCompany")
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        adminFacade.addCompany(company);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Company added");
    }

    @PutMapping("/updateCompany")
    public ResponseEntity<String> updateCompany(@RequestBody Company company) {
        adminFacade.updateCompany(company);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Company updated.");
    }

    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        adminFacade.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Company deleted.");
    }

    @GetMapping("/companies")
    public ResponseEntity<String> getAllCompanies() {
//        List<Company> companies = adminFacade.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "all companies...");
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getOneCompany(@PathVariable("id") Long id) {
        Company company = adminFacade.getOneCompany(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(company);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer returnedCustomer = adminFacade.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                returnedCustomer);
    }
//
//    @PutMapping("/updateCustomer")
//    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
//
//    }
//
//    @DeleteMapping("/deleteCustomer/{id}")
//    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
//
//    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = adminFacade.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(customers);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable("id") Long id) {
        Customer customer = adminFacade.getOneCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(customer);
    }


//
//    @GetMapping("/adminLogin")
//    public ResponseEntity adminLogin() {
//        Login login = new Login("email@email.com", "Password", ClientType.Administrator);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
//                login);
//    }


}


