package com.coupon.demo.service;

import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.User;
import com.coupon.demo.model.Role;
import com.coupon.demo.model.details.CompanyDetails;
import com.coupon.demo.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompanyDetailsService implements UserDetailsService {

    private CompanyRepository companyRepository;

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return companyRepository.findByEmail(username)
                .map(company -> new CompanyDetails(
                        company.getEmail(),
                        company.getPassword(),
                        Role.COMPANY)).orElseThrow(() ->
                        new UsernameNotFoundException(
                                "email: " + username + " does not exist"
                        ));
    }
}
