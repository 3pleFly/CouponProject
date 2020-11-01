package com.coupon.demo.service.details;

import com.coupon.demo.model.Scope;
import com.coupon.demo.model.details.AdminDetails;
import com.coupon.demo.model.details.CompanyDetails;
import com.coupon.demo.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
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

    private String username;
    private String password;

    @Autowired
    public AdminDetailsService(@Value("${admin.username}")String username,
                               @Value("${admin.password}")String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + Scope.ADMIN.name()));
        if (this.username.equals(username)) {
            return new AdminDetails(username, password, authorities);
        } else {
            throw new UsernameNotFoundException(
                    "username: " + username + " does not exist"
            );
        }
    }
}
