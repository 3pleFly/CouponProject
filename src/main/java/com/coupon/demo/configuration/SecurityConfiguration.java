package com.coupon.demo.configuration;

import com.coupon.demo.configuration.filter.JwtFilter;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.Scope;
import com.coupon.demo.service.details.AdminDetailsService;
import com.coupon.demo.service.details.CompanyDetailsService;
import com.coupon.demo.service.details.CustomerDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AdminDetailsService adminDetailsService;
    private final CustomerDetailsService customerDetailsService;
    private final CompanyDetailsService companyDetailsService;
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(companyDetailsService).passwordEncoder(passwordEncoder())
                .and()
                .userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder())
                .and()
                .userDetailsService(adminDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                .antMatchers("/public/**").permitAll()
                .antMatchers("/companies/**").hasAnyRole(Scope.COMPANY.name(), Scope.ADMIN.name())
                .antMatchers("/customers/**").hasAnyRole(Scope.CUSTOMER.name(), Scope.ADMIN.name())
                .antMatchers("/admin/**").hasRole(Scope.ADMIN.name())

                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            log.error(e.getMessage());
            response.setStatus(403);
            response.setContentType("application/json");
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, "Coupon ~Project: Access is denied!");
            try {
                String jsonResponseDTO = responseDTO.convertToJson();
                response.getWriter().write(jsonResponseDTO);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            log.error(e.getMessage());
            response.setStatus(403);
            response.setContentType("application/json");
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, "Coupon ~Project: No Authentication");
            responseDTO.setHttpErrorCode(403);
            try {
                String jsonResponseDTO = responseDTO.convertToJson();
                response.getWriter().write(jsonResponseDTO);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
