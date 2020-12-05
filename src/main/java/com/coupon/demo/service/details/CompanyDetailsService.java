package com.coupon.demo.service.details;

import com.coupon.demo.model.Scope;
import com.coupon.demo.model.userdetails.CompanyDetails;
import com.coupon.demo.repositories.CompanyRepository;
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
public class CompanyDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(Scope.COMPANY.name()));
        return companyRepository.findByEmail(username)
                .map(company -> new CompanyDetails(
                        company.getEmail(),
                        company.getPassword(), authorities)).orElseThrow(() ->
                        new UsernameNotFoundException("email: " + username + " does not exist"));
    }
}
