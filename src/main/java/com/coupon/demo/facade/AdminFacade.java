package com.coupon.demo.facade;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.entities.Category;
import com.coupon.demo.model.entities.Company;
import com.coupon.demo.model.entities.Customer;
import com.coupon.demo.exception.*;
import com.coupon.demo.service.dao.CategoryDao;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CouponDao;
import com.coupon.demo.service.dao.CustomerDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class AdminFacade {

    private final CompanyDao companyDao;
    private final CategoryDao categoryDao;
    private final CustomerDao customerDao;
    private final CouponDao couponDao;

    public ResponseDTO<CompanyDTO> addCompany(Company company) {
        try {
            Company addedCompany = companyDao.addCompany(company);
            return new ResponseDTO<>(new CompanyDTO(
                    addedCompany.getId(),
                    addedCompany.getName(),
                    addedCompany.getEmail(),
                    couponDao.convertToCouponDTO(addedCompany.getCoupons())), true, "Company added");
        } catch (AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<CompanyDTO> updateCompany(Company company) {
        try {
            Company updatedCompany = companyDao.updateCompany(company);
            return new ResponseDTO<>(new CompanyDTO(
                    updatedCompany.getId(),
                    updatedCompany.getName(),
                    updatedCompany.getEmail(),
                    couponDao.convertToCouponDTO(updatedCompany.getCoupons())), true, "Company updated"
            );
        } catch (BadUpdate | AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    @Transactional
    public ResponseDTO<String> deleteCompany(Long companyId) {
        try {
            companyDao.deleteCompany(companyId);
            return new ResponseDTO<>(null, true, "Company deleted");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, e.getMessage()
            );
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        try {
            companyDao.getAllCompanies().forEach(company -> companyDTOList.add(new CompanyDTO(
                    company.getId(),
                    company.getName(),
                    company.getEmail(),
                    couponDao.convertToCouponDTO(company.getCoupons())
            )));
            return new ResponseDTO<>(companyDTOList, true, "Retrieved all companies successfully");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }
    }

    public ResponseDTO<CompanyDTO> getOneCompany(Long companyId) {
        try {
            Company getOneCompany = companyDao.getOneCompany(companyId);
            return new ResponseDTO<>(new CompanyDTO(
                    getOneCompany.getId(),
                    getOneCompany.getName(),
                    getOneCompany.getEmail(),
                    couponDao.convertToCouponDTO(getOneCompany.getCoupons())), true, "Retrieved company successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, null);
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<CustomerDTO> addCustomer(Customer customer) {
        try {
            Customer addedCustomer = customerDao.addCustomer(customer);
            return new ResponseDTO<>(new CustomerDTO(
                    addedCustomer.getId(),
                    addedCustomer.getFirstName(),
                    addedCustomer.getLastName(),
                    addedCustomer.getEmail(),
                    couponDao.convertToCouponDTO(addedCustomer.getCoupons())), true, "Customer added");
        } catch (AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<CustomerDTO> updateCustomer(Customer customer) {
        try {
            Customer updatedCustomer = customerDao.updateCustomer(customer);
            return new ResponseDTO<>(new CustomerDTO(
                    updatedCustomer.getId(),
                    updatedCustomer.getFirstName(),
                    updatedCustomer.getLastName(),
                    updatedCustomer.getEmail(),
                    couponDao.convertToCouponDTO(updatedCustomer.getCoupons())),
                    true, "Customer updated"
            );
        } catch (LengthException | AlreadyExists e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<String> deleteCustomer(Long customerId) {
        try {
            customerDao.deleteCustomer(customerId);
            return new ResponseDTO<>(null, true, "Customer deleted");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }

    }

    public ResponseDTO<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        try {
            customerDao.getAllCustomers().forEach(customer -> customerDTOList.add(new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    couponDao.convertToCouponDTO(customer.getCoupons())
            )));
            return new ResponseDTO<>(customerDTOList, true, "Retrieved all customers successfully");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }
    }

    public ResponseDTO<CustomerDTO> getOneCustomer(Long customerId) {
        try {
            Customer getOneCustomer = customerDao.getOneCustomer(customerId);
            return new ResponseDTO<>(new CustomerDTO(
                    getOneCustomer.getId(),
                    getOneCustomer.getFirstName(),
                    getOneCustomer.getLastName(),
                    getOneCustomer.getEmail(),
                    couponDao.convertToCouponDTO(getOneCustomer.getCoupons())), true, "Retrieved customer successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Category> addCategory(Category category) {
        try {
            return new ResponseDTO<>(categoryDao.addCategory(category), true, "Category added");
        } catch (AlreadyExists | MissingAttributes e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Category> updateCategory(Category category) {
        try {
            return new ResponseDTO<>(categoryDao.updateCategory(category), true, "Category updated");
        } catch (AlreadyExists | MissingAttributes e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<String> deleteCategory(Long categoryID) {
        try {
            categoryDao.deleteCategory(categoryID);
            return new ResponseDTO<>(null, true, "Category deleted");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Category> getOneCategory(Long categoryID) {
        try {
            return new ResponseDTO<>(categoryDao.getOneCategory(categoryID), true, "Retrieved category successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<List<Category>> getAllCategories() {
        try {
            return new ResponseDTO<>(categoryDao.getAllCategories(), true, "Category added");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }
    }

    public ResponseDTO<List<CouponDTO>> getAllCoupons() {
        try {
            List<CouponDTO> couponDTOList = couponDao.convertToCouponDTO(couponDao.getAllCoupons());
            return new ResponseDTO<>(couponDTOList, true, "Retrieved all coupons successfully");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }
    }
}
