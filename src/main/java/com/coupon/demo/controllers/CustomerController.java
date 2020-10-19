package com.coupon.demo.controllers;

import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.facade.CustomerFacade;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
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
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerFacade customerFacade;
    private final JwtUtil jwtUtil;


    @GetMapping("/customers")
    public ResponseEntity<String> getAllCustomers() {
//        List<Company> companies = adminFacade.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "all customers...");
    }

    @GetMapping("/customer")
    public ResponseEntity<ResponseDTO<Customer>> getCustomerDetails(@RequestHeader("Authorization") String authHeader) {
        Long customerID = jwtUtil.extractID.apply(authHeader);
        Customer customer = customerFacade.getCustomerDetails(customerID);
        ResponseDTO<Customer> responseDTO = new ResponseDTO<Customer>(
                new Customer(
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail()), "Customer",
                true, "getCustomerDetails successful"
                );

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                responseDTO);
    }

}
