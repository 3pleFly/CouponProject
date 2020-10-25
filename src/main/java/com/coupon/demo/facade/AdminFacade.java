package com.coupon.demo.facade;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.*;
import com.coupon.demo.service.dao.CategoryDao;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CustomerDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseDTO<CompanyDTO> addCompany(Company company) {
        try {
            company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
            Company addedCompany = companyDao.addCompany(company);
            return new ResponseDTO<>(new CompanyDTO(
                    addedCompany.getId(),
                    addedCompany.getName(),
                    addedCompany.getEmail(),
                    CouponDTO.generateCouponDTO(addedCompany.getCoupons()))
                    , true, "Company added");
        } catch (AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage()
            );
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error"
            );
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<CompanyDTO> updateCompany(Company company) {
        try {
            if (company.getPassword() == null) {
                company.setPassword(companyDao.getOneCompany(company.getId()).getPassword());
            } else if (company.getPassword() != null) {
                company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
            }
            Company updatedCompany = companyDao.updateCompany(company);
            return new ResponseDTO<>(new CompanyDTO(
                    updatedCompany.getId(),
                    updatedCompany.getName(),
                    updatedCompany.getEmail(),
                    CouponDTO.generateCouponDTO(updatedCompany.getCoupons()))
                    , true, "Company updated!"
            );
        } catch (BadUpdate | AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage()
            );
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage()
            );
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error"
            );
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    @Transactional
    public ResponseDTO<String> deleteCompany(Long companyId) {
        try {
            companyDao.deleteCompany(companyId);
            return new ResponseDTO<>(
                    null, true, "deleteCompany successful");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage()
            );
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error");
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
                    CouponDTO.generateCouponDTO(company.getCoupons())
            )));
            return new ResponseDTO<>(companyDTOList, true, "getAllCompanies successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(
                    null, false, "Internal error");
        }
    }

    public ResponseDTO<CompanyDTO> getOneCompany(Long companyId) {
        try {
            Company getOneCompany = companyDao.getOneCompany(companyId);
            return new ResponseDTO<>(new CompanyDTO(
                    getOneCompany.getId(),
                    getOneCompany.getName(),
                    getOneCompany.getEmail(),
                    CouponDTO.generateCouponDTO(getOneCompany.getCoupons()))
                    , true, "getOneCompany success");
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
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
            Customer addedCustomer = customerDao.addCustomer(customer);
            return new ResponseDTO<>(new CustomerDTO(
                    addedCustomer.getId(),
                    addedCustomer.getFirstName(),
                    addedCustomer.getLastName(),
                    addedCustomer.getEmail(),
                    CouponDTO.generateCouponDTO(addedCustomer.getCoupons())), true, "addCustomer " +
                    "successful");
        } catch (AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<CustomerDTO> updateCustomer(Customer customer) {
        try {
            if (customer.getPassword() == null) {
                customer.setPassword(customerDao.getOneCustomer(customer.getId()).getPassword());
            } else if (customer.getPassword() != null) {
                customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
            }
            Customer updatedCustomer = customerDao.updateCustomer(customer);
            return new ResponseDTO<>(new CustomerDTO(
                    updatedCustomer.getId(),
                    updatedCustomer.getFirstName(),
                    updatedCustomer.getLastName(),
                    updatedCustomer.getEmail(),
                    CouponDTO.generateCouponDTO(updatedCustomer.getCoupons())),
                    true, "Customer updated!"
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
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error"
            );
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<String> deleteCustomer(Long customerId) {
        try {
            customerDao.deleteCustomer(customerId);
            return new ResponseDTO<>(
                    null, true, "deleteCustomer successful");
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
                    CouponDTO.generateCouponDTO(customer.getCoupons())
            )));
            return new ResponseDTO<>(customerDTOList, true, "getAllCustomers successful");
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
                    CouponDTO.generateCouponDTO(getOneCustomer.getCoupons())), true,
                    "getOneCustomer " +
                            "successful");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Category> addCategory(Category category) {
        try {
            return new ResponseDTO<>(categoryDao.addCategory(category), true, "addCategory " +
                    "successful");
        } catch (AlreadyExists | MissingAttributes e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Category> updateCategory(Category category) {
        try {
            return new ResponseDTO<>(categoryDao.updateCategory(category), true,
                    "updateCategory " +
                            "successful");
        } catch (AlreadyExists | MissingAttributes e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<Category> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<String> deleteCategory(Long categoryID) {
        try {
            categoryDao.deleteCategory(categoryID);
            return new ResponseDTO<>(null, true, "deleteCategory " +
                    "successful");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Category> getOneCategory(Long categoryID) {
        try {
            return new ResponseDTO<>(categoryDao.getOneCategory(categoryID), true,
                    "getOneCategory" +
                            " successful");
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
            return new ResponseDTO<>(categoryDao.getAllCategories(), true, "addCategory " +
                    "successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(
                    null, false, "Internal error");
        }
    }

}
