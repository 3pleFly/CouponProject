package com.coupon.demo.restservice;

import com.coupon.demo.beans.Customer;
import com.coupon.demo.dtobeans.CustomerDTO;
import com.coupon.demo.dtobeans.Login;
import com.coupon.demo.dtobeans.CompanyDTO;
import com.coupon.demo.service.AdminService;
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
    public ResponseEntity adminLogin(@RequestBody Login login) {
        adminService = (AdminService) loginManager.login(login.getEmail(), login.getPassword(),
                login.getClientType());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Login successful!");
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = new ArrayList<>();
        adminService.getAllCompanies().forEach((company -> {
            CompanyDTO companyDTO = new CompanyDTO(company.getId(), company.getName(),
                    company.getEmail(), company.getPassword());
            companies.add(companyDTO);
        }));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(companies);
    }

    @GetMapping("/customers")
    public ResponseEntity getAllCustomer() {
        List<CustomerDTO> customers = new ArrayList<>();
        adminService.getAllCustomers().forEach((customer -> {
            CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getPassword());
            customers.add(customerDTO);
        }));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(customers);

    }


    @PostMapping("/addCustomer")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        Customer returnedCustomer = adminService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                returnedCustomer);
    }


//
//    @GetMapping("/adminLogin")
//    public ResponseEntity adminLogin() {
//        Login login = new Login("email@email.com", "Password", ClientType.Administrator);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
//                login);
//    }


}


