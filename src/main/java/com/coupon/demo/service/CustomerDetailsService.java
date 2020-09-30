package com.coupon.demo.service;

import com.coupon.demo.model.Role;
import com.coupon.demo.model.details.CompanyDetails;
import com.coupon.demo.model.details.CustomerDetails;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private CustomerRepository customerRepository;

    @Autowired
    public void setCompanyRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByEmail(username)
                .map(customer -> new CustomerDetails(
                        customer.getEmail(),
                        customer.getPassword(),
                        Role.CUSTOMER)).orElseThrow(() ->
                        new UsernameNotFoundException(
                                "email: " + username + " does not exist"
                        ));
    }
}
