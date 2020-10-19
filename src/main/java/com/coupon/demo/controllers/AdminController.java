package com.coupon.demo.controllers;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.ResponseDTO;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
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


    @PostMapping("/addCompany")
    public ResponseEntity<ResponseDTO> addCompany(@RequestBody Company company) {
        Company addedCompany = adminFacade.addCompany(company);

        ResponseDTO responseDTO = new ResponseDTO(
                new CompanyDTO(addedCompany.getId(), addedCompany.getName(),
                        addedCompany.getEmail()),"Exception", true, "Company added!"
        );

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                responseDTO);
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
    public ResponseEntity<ResponseDTO<List<Company>>> getAllCompanies() {
        List<Company> companies = adminFacade.getAllCompanies();
        ResponseDTO<List<Company>> responseDTO =
                new ResponseDTO<>(
                        companies, "Company", true, "getAllCompanies successful"
                );

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                responseDTO);
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


