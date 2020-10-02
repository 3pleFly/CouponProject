package com.coupon.demo.service.details;

import com.coupon.demo.model.Scope;
import com.coupon.demo.model.details.CustomerDetails;
import com.coupon.demo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private CustomerRepository customerRepository;

    @Autowired
    public void setCompanyRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+Scope.CUSTOMER.name()));

        return customerRepository.findByEmail(username)
                .map(customer -> new CustomerDetails(
                        customer.getEmail(),
                        customer.getPassword(), authorities)).orElseThrow(() ->
                        new UsernameNotFoundException(
                                "email: " + username + " does not exist"
                        ));
    }
}
