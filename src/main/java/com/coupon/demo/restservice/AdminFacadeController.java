package com.coupon.demo.restservice;

import com.coupon.demo.beans.Company;
import com.coupon.demo.beans.Customer;
import com.coupon.demo.dtobeans.CustomerDTO;
import com.coupon.demo.dtobeans.Login;
import com.coupon.demo.dtobeans.CompanyDTO;
import com.coupon.demo.facade.AdminFacade;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/couponproject")
public class AdminFacadeController {

    private final LoginManager loginManager;
    private AdminFacade adminFacade;


    @Autowired
    public AdminFacadeController(AdminFacade adminFacade, LoginManager loginManager) {
        this.adminFacade = adminFacade;
        this.loginManager = loginManager;
    }


    @PostMapping("/adminLogin")
    public ResponseEntity<String> adminLogin(@RequestBody Login login) {
        adminFacade = (AdminFacade) loginManager.login(login);
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
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = adminFacade.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(companies);
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


