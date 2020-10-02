package com.coupon.demo.service.details;

import com.coupon.demo.model.Scope;
import com.coupon.demo.model.details.AdminDetails;
import com.coupon.demo.model.details.CompanyDetails;
import com.coupon.demo.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + Scope.ADMIN.name()));
        if (this.username.equals(username)) {
            return new AdminDetails(username, password, authorities);
        } else {
            throw new UsernameNotFoundException(
                    "email: " + username + " does not exist"
            );
        }
    }
}
