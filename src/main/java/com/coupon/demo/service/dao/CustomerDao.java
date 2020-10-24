package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.BadUpdate;
import com.coupon.demo.exception.NotFound;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerDao {

    private CustomerRepository customerRepository;

    public boolean isCustomerExists(String email, String password) {
        return customerRepository.existsByEmailAndPassword(email, password);
    }

    public Customer addCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new AlreadyExists("Customer email already exists: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFound("Customer by ID: " + id + " does not exist.");
        }
        return customerRepository.findById(id).get();
    }

}
