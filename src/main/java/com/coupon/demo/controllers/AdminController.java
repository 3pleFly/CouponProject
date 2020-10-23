package com.coupon.demo.controllers;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.dto.Login;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.BadUpdate;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminFacade adminFacade;


    @PostMapping("/addcompany")
    public ResponseEntity<ResponseDTO<CompanyDTO>> addCompany(@RequestBody Company company) {
        try {
            Company addedCompany = adminFacade.addCompany(company);
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    new CompanyDTO(
                            addedCompany.getId(),
                            addedCompany.getName(),
                            addedCompany.getEmail(),
                            addedCompany.getCoupons()),
                    "Company", true, "Company added!"
            );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (AlreadyExists e) {
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, e.getClass().getSimpleName(), false, e.getMessage()
            );
            return ResponseEntity
                    .status(409)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }

    }

    @PutMapping("/updatecompany")
    public ResponseEntity<ResponseDTO<CompanyDTO>> updateCompany(@RequestBody Company company) {
        try {
            Company updatedCompany = adminFacade.updateCompany(company);
            ResponseDTO<CompanyDTO> responseDTO =
                    new ResponseDTO<>(new CompanyDTO(
                            updatedCompany.getId(),
                            updatedCompany.getName(),
                            updatedCompany.getEmail(),
                            updatedCompany.getCoupons()),
                            "Company", true, "Company updated!"
                    );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (BadUpdate e) {
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, e.getClass().getSimpleName(), false, e.getMessage()
            );
            return ResponseEntity
                    .status(409)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @DeleteMapping("/deletecompany/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteCompany(@PathVariable Long id) {
        try {
            adminFacade.deleteCompany(id);
            ResponseDTO<String> responseDTO =
                    new ResponseDTO<>(
                            "Company Deleted", "Company", true, "deleteCompany successful!"
                    );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, e.getClass().getSimpleName(), false, e.getMessage()
            );
            return ResponseEntity
                    .status(409)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/companies")
    public ResponseEntity<ResponseDTO<List<CompanyDTO>>> getAllCompanies() {
        try {
            List<CompanyDTO> companiesDTO = new ArrayList<>();
            adminFacade.getAllCompanies().forEach(company -> {
                companiesDTO.add(new CompanyDTO(
                        company.getId(),
                        company.getName(),
                        company.getEmail(),
                        company.getCoupons()));
            });
            ResponseDTO<List<CompanyDTO>> responseDTO =
                    new ResponseDTO<>(
                            companiesDTO, "Company", true, "getAllCompanies successful!"
                    );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<List<CompanyDTO>> responseDTO =
                    new ResponseDTO<>(
                            null, e.getClass().getSimpleName(), false, e.getMessage()
                    );
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<ResponseDTO<CompanyDTO>> getOneCompany(@PathVariable("id") Long id) {
        try {
            Company company = adminFacade.getOneCompany(id);
            ResponseDTO<CompanyDTO> responseDTO =
                    new ResponseDTO<>(new CompanyDTO(
                            company.getId(),
                            company.getName(),
                            company.getEmail(),
                            company.getCoupons()),
                            "Company", true, "getOneCompany Successful"
                    );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (NotFound e) {
            ResponseDTO<CompanyDTO> responseDTO =
                    new ResponseDTO<>(
                            null, e.getClass().getSimpleName(), false, e.getMessage()
                    );
            return ResponseEntity
                    .status(404)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @PostMapping("/addcustomer")
    public ResponseEntity<ResponseDTO<CustomerDTO>> addCustomer(@RequestBody Customer customer) {
        try {
            Customer addedCustomer = adminFacade.addCustomer(customer);
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(new CustomerDTO(
                            addedCustomer.getId(),
                            addedCustomer.getFirstName(),
                            addedCustomer.getLastName(),
                            addedCustomer.getEmail(),
                            addedCustomer.getCoupons()),
                            "Customer", true, "Customer added!"
                    );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (AlreadyExists e) {
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(
                            null, e.getClass().getSimpleName(), false, e.getMessage()
                    );
            return ResponseEntity
                    .status(409)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }


    @PutMapping("/updatecustomer")
    public ResponseEntity<ResponseDTO<CustomerDTO>> updateCustomer(@RequestBody Customer customer) {
        try {
            Customer updatedCustomer = adminFacade.updateCustomer(customer);
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(new CustomerDTO(
                            updatedCustomer.getId(),
                            updatedCustomer.getFirstName(),
                            updatedCustomer.getLastName(),
                            updatedCustomer.getEmail(),
                            updatedCustomer.getCoupons()),
                            "Customer", true, "Customer updated!"
                    );
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } catch (RuntimeException e) {
            System.out.println("CAUGHT: "  + e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(
                            null, e.getClass().getSimpleName(), false, e.getMessage()
                    );
            return ResponseEntity
                    .status(409)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteCustomer(@PathVariable Long id) {
        adminFacade.deleteCustomer(id);
        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(
                        "Customer Deleted", "Customer", true, "deleteCustomer successful!"
                );
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }

    @GetMapping("/customers")
    public ResponseEntity<ResponseDTO<List<CustomerDTO>>> getAllCustomer() {
        List<CustomerDTO> customersDTO = new ArrayList<>();
        adminFacade.getAllCustomers().forEach(customer -> {
            customersDTO.add(new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getCoupons()));
        });
        ResponseDTO<List<CustomerDTO>> responseDTO =
                new ResponseDTO<>(
                        customersDTO, "Customer", true, "getAllCustomers successful!"
                );
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<ResponseDTO<CustomerDTO>> getOneCustomer(@PathVariable("id") Long id) {
        Customer customer = adminFacade.getOneCustomer(id);
        ResponseDTO<CustomerDTO> responseDTO =
                new ResponseDTO<>(new CustomerDTO(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getCoupons()),
                        "Customer", true, "getOneCustomer successful"
                );
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO<List<Category>>> getAllCategories() {
        List<Category> allCategories = adminFacade.getAllCategories();
        ResponseDTO<List<Category>> responseDTO =
                new ResponseDTO<>(
                        allCategories, "Category", true, "getAllCategories successful!"
                );
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }
}


