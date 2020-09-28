package com.coupon.demo.configuration;

import com.coupon.demo.model.Role;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.AsmVisitorWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManager manager =
                new InMemoryUserDetailsManager();

        UserDetails user1 = User
                .withUsername("admin")
                .password("admin")
                .authorities(Role.ADMIN.name()).build();

        UserDetails user2 = User
                .withUsername("jake")
                .password("admin")
                .authorities(Role.COMPANY.name()).build();

        manager.createUser(user1);
        manager.createUser(user2);
        System.out.println(user2.getAuthorities());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/default", true);

        http
                .authorizeRequests()
                .mvcMatchers("/admin")
                .access("hasAnyAuthority('ADMIN')")
                .mvcMatchers("/jake")
                .access("hasAnyAuthority('COMPANY')")
                .anyRequest().authenticated();
    }


}
