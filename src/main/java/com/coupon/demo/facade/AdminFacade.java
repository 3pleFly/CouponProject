package com.coupon.demo.facade;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.BadUpdate;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.service.dao.CategoryDao;
import com.coupon.demo.service.dao.CompanyDao;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseDTO<CompanyDTO> addCompany(Company company) {
        try {
            company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
            Company addedCompany = companyDao.addCompany(company);
            return new ResponseDTO<>(new CompanyDTO(
                    addedCompany.getId(),
                    addedCompany.getName(),
                    addedCompany.getEmail(),
                    addedCompany.getCoupons()), true, "Company added");
        } catch (AlreadyExists e) {
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
                    updatedCompany.getCoupons()), true, "Company updated!"
            );
        } catch (BadUpdate e) {
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

    @Transactional
    public ResponseDTO<String> deleteCompany(Long companyId) {
        try {
            companyDao.deleteCompany(companyId);
            return new ResponseDTO<>(
                    null, true, "deleteCompany successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(
                    null, false, "Internal error");
        }
    }

    public ResponseDTO<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        try {
            companyDao.getAllCompanies().forEach(company -> companyDTOList.add(new CompanyDTO(
                    company.getId(),
                    company.getName(),
                    company.getEmail(),
                    company.getCoupons()
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
                    getOneCompany.getCoupons()), true, "getOneCompany success");
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
                    addedCustomer.getCoupons()), true, "addCustomer successful");
        } catch (AlreadyExists e) {
            log.warn(e.getMessage());
            ResponseDTO<CustomerDTO> responseDTO =
                    new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
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
                    updatedCustomer.getCoupons()),
                    true, "Customer updated!"
            );
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(
                    null, false, "Internal error"
            );
        }
    }

    public ResponseDTO<String> deleteCustomer(Long customerId) {
        try {
            customerDao.deleteCustomer(customerId);
            return new ResponseDTO<>(
                    null, true, "deleteCustomer successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(
                    null, false, "Internal error");
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
                    customer.getCoupons()
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
                    getOneCustomer.getCoupons()), true, "getOneCustomer successful");
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

    public Category addCategory(Category category) {
        try {
            return categoryDao.addCategory(category);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Category> getAllCategories() {
        try {
            return categoryDao.getAllCategories();
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
