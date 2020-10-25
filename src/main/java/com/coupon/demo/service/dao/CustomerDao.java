package com.coupon.demo.service.dao;

import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.*;
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

    private final CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        checkNull(customer);
        checkLengthLimit(customer);
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new AlreadyExists("Customer email already exists: " + customer.getEmail());
        }
        customerRepository.findAllByFirstNameAndLastName(
                customer.getFirstName(),
                customer.getLastName()).forEach(customerByName -> {
            if (customerByName.getId().equals(customer.getId())) {
                throw new AlreadyExists("Customer by this name(first and last together) " +
                        "already exists" + customer.getFirstName() + customer.getLastName());
            }
        });
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        checkExists(customer.getId());
        checkLengthLimit(customer);
        customerRepository.findAllByEmail(customer.getEmail()).ifPresent(customers -> {
            customers.forEach(customerBySameEmail -> {
                if (!customerBySameEmail.getId().equals(customer.getId())) {
                    throw new AlreadyExists("There is already a customer with the same email" + customer.getEmail());
                }
            });
        });
        customerRepository.findAllByFirstNameAndLastName(
                customer.getFirstName(),
                customer.getLastName()).forEach(customerByName -> {
            if (!customerByName.getId().equals(customer.getId())) {
                throw new AlreadyExists("Customer by this name(first and last together) " +
                        "already exists" + customer.getFirstName() + customer.getLastName());
            }
        });
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerID) {
        checkExists(customerID);
        customerRepository.deleteById(customerID);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(Long customerID) {
        checkExists(customerID);
        return customerRepository.findById(customerID).get();
    }

    private void checkExists(Long customerID) {
        if (!customerRepository.existsById(customerID)) {
            throw new NotFound("Customer by ID not found: " + customerID);
        }
    }

    private void checkNull(Customer customer) {
        if (customer.getFirstName() == null
                || customer.getLastName() == null
                || customer.getEmail() == null
                || customer.getPassword() == null) {
            throw new MissingAttributes("Customer's email, password, or name cannot be null");
        }
    }

    private void checkLengthLimit(Customer customer) {
        if (customer.getPassword().length() > 200
                || customer.getEmail().length() > 100
                || customer.getFirstName().length() > 30
                || customer.getLastName().length() > 30) {
            throw new LengthException("Customer attributes exceed limit");
        }
    }
}
