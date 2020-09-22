package com.coupon.demo.restservice;

import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.dtobeans.CustomerDTO;
import com.coupon.demo.dtobeans.Login;
import com.coupon.demo.dtobeans.CompanyDTO;
import com.coupon.demo.service.AdminService;
import com.coupon.demo.service.ClientType;
import com.coupon.demo.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/couponproject")
public class AdminServiceController {

    private final LoginManager loginManager;
    private AdminService adminService;


    @Autowired
    public AdminServiceController(AdminService adminService, LoginManager loginManager) {
        this.adminService = adminService;
        this.loginManager = loginManager;
    }


    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(@RequestBody Login login) {
        adminService = (AdminService) loginManager.login(login);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Login successful!");
    }

    @PostMapping("/addCompany")
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        adminService.addCompany(company);

    }

//    @PutMapping("/updateCompany")
//    public ResponseEntity<String> updateCompany(@RequestBody Login login) {
//        adminService = (AdminService) loginManager.login(login);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
//                "Login successful!");
//    }

//    @DeleteMapping("/deleteCompany")
//    public ResponseEntity<String> deleteCompany(@RequestBody Login login) {
//        adminService = (AdminService) loginManager.login(login);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
//                "Login successful!");
//    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = adminService.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(companies);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getOneCompany(@PathVariable("id") Long id) {
        Company company = adminService.getOneCompany(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(company);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer returnedCustomer = adminService.addCustomer(customer);
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
        List<Customer> customers = adminService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(customers);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable("id") Long id) {
        Customer customer = adminService.getOneCustomer(id);
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


