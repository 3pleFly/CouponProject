package com.coupon.demo.service.details;

import com.coupon.demo.model.Scope;
import com.coupon.demo.model.userdetails.CustomerDetails;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(Scope.CUSTOMER.name()));

        return customerRepository.findByEmail(username)
                .map(customer -> new CustomerDetails(
                        customer.getEmail(),
                        customer.getPassword(), authorities)).orElseThrow(() ->
                        new UsernameNotFoundException("email: " + username + " does not exist"));
    }
}
