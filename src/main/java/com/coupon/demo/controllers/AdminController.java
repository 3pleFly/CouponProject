package com.coupon.demo.controllers;
import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.entities.Category;
import com.coupon.demo.model.entities.Company;
import com.coupon.demo.model.entities.Customer;
import com.coupon.demo.facade.AdminFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminFacade adminFacade;

    @PostMapping("/companies/add")
    public ResponseEntity<ResponseDTO<CompanyDTO>> addCompany(@RequestBody Company company) {
        ResponseDTO<CompanyDTO> responseDTO = adminFacade.addCompany(company);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @PutMapping("/companies/update")
    public ResponseEntity<ResponseDTO<CompanyDTO>> updateCompany(@RequestBody Company company) {
        ResponseDTO<CompanyDTO> responseDTO = adminFacade.updateCompany(company);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @DeleteMapping("/companies/delete/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteCompany(@PathVariable Long id) {
        ResponseDTO<String> responseDTO = adminFacade.deleteCompany(id);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/companies")
    public ResponseEntity<ResponseDTO<List<CompanyDTO>>> getAllCompanies() {
        ResponseDTO<List<CompanyDTO>> responseDTO = adminFacade.getAllCompanies();
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/companies/company/{id}")
    public ResponseEntity<ResponseDTO<CompanyDTO>> getOneCompany(@PathVariable("id") Long id) {
        ResponseDTO<CompanyDTO> responseDTO = adminFacade.getOneCompany(id);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @PostMapping("/customers/add")
    public ResponseEntity<ResponseDTO<CustomerDTO>> addCustomer(@RequestBody Customer customer) {
        ResponseDTO<CustomerDTO> responseDTO = adminFacade.addCustomer(customer);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }


    @PutMapping("/customers/update")
    public ResponseEntity<ResponseDTO<CustomerDTO>> updateCustomer(@RequestBody Customer customer) {
        ResponseDTO<CustomerDTO> responseDTO = adminFacade.updateCustomer(customer);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @DeleteMapping("/customers/delete/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteCustomer(@PathVariable Long id) {

        ResponseDTO<String> responseDTO = adminFacade.deleteCustomer(id);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<ResponseDTO<List<CustomerDTO>>> getAllCustomer() {
        ResponseDTO<List<CustomerDTO>> responseDTO = adminFacade.getAllCustomers();
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/customers/customer/{id}")
    public ResponseEntity<ResponseDTO<CustomerDTO>> getOneCustomer(@PathVariable("id") Long id) {
        ResponseDTO<CustomerDTO> responseDTO = adminFacade.getOneCustomer(id);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @PostMapping("/categories/add")
    public ResponseEntity<ResponseDTO<Category>> addCategory(@RequestBody Category category) {
        ResponseDTO<Category> responseDTO = adminFacade.addCategory(category);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @PutMapping("/categories/update")
    public ResponseEntity<ResponseDTO<Category>> updateCategory(@RequestBody Category category) {
        ResponseDTO<Category> responseDTO = adminFacade.updateCategory(category);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @DeleteMapping("/categories/delete/{categoryID}")
    public ResponseEntity<ResponseDTO<String>> deleteCategory(@PathVariable Long categoryID) {
        ResponseDTO<String> responseDTO = adminFacade.deleteCategory(categoryID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/categories/category/{categoryID}")
    public ResponseEntity<ResponseDTO<Category>> getOneCategory(@PathVariable Long categoryID) {
        ResponseDTO<Category> responseDTO = adminFacade.getOneCategory(categoryID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO<List<Category>>> getAllCategories() {
        ResponseDTO<List<Category>> responseDTO = adminFacade.getAllCategories();
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }


}



