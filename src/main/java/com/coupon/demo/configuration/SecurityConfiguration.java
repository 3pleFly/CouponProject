package com.coupon.demo.configuration;

import com.coupon.demo.configuration.filter.JwtFilter;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.Scope;
import com.coupon.demo.service.details.AdminDetailsService;
import com.coupon.demo.service.details.CompanyDetailsService;
import com.coupon.demo.service.details.CustomerDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AdminDetailsService adminDetailsService;
    private final CustomerDetailsService customerDetailsService;
    private final CompanyDetailsService companyDetailsService;
    private final JwtFilter jwtFilter;

    /**
     * Overriding AuthenticationManagerBuilder to specify which UserDetailsService to use
     * for fetching the appropriate UserDetails from the database for authentication purposes.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth
                    .userDetailsService(companyDetailsService).passwordEncoder(passwordEncoder())
                    .and()
                    .userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder())
                    .and()
                    .userDetailsService(adminDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Spring Security configuration
     * /public/** is allowed through the servlet without authentication.
     * Other Ant-Matcher's are authentication + authorization based on a JWT.
     * Stateless session creation is used with a JWT filter.
     *
     * addFilterBefore(jwtFilter) adds a JWT-Filter before UsernamePasswordAuthenticationFilter so to handle authentication and authorization
     * through JWTs.
     */
    @Override
    protected void configure(HttpSecurity http) {
        try {

            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/public/**").permitAll()
                    .antMatchers("/companies/**").hasAnyAuthority(Scope.COMPANY.name(),
                    Scope.ADMIN.name())
                    .antMatchers("/customers/**").hasAnyAuthority(Scope.CUSTOMER.name(),
                    Scope.ADMIN.name())
                    .antMatchers("/admin/**").hasAuthority(Scope.ADMIN.name())
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Configuring which passwordEncoder Spring Security will use to encode passwords.
     * Also used for hashing passwords before updating or adding new database info.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Implemented to write custom AccessIsDenied exceptions.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            log.error(e.getMessage());
            response.setStatus(403);
            response.setContentType("application/json");
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, "Access is denied, you don't have authority");
            writeResponse(response, responseDTO);
        };
    }

    /**
     * Implemented to write custom AuthenticationExceptions.
     * Sends a 401(unauthorized) on bad/no credentials
     * Sends a 403 on any other exception
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            if (e.getClass().equals(InsufficientAuthenticationException.class)
                    || e.getClass().equals(BadCredentialsException.class)) {
                log.error(e.getMessage());
                response.setStatus(401);
                response.setContentType("application/json");
                ResponseDTO<String> responseDTO = new ResponseDTO<>(
                        null, false, "Bad Credentials");
                responseDTO.setHttpErrorCode(401);
                writeResponse(response, responseDTO);
            } else {
                log.error(e.getMessage());
                response.setStatus(403);
                response.setContentType("application/json");
                ResponseDTO<String> responseDTO = new ResponseDTO<>(
                        null, false, "Bad authentication");
                responseDTO.setHttpErrorCode(403);
                writeResponse(response, responseDTO);
            }
        };
    }

    /**
     *  Converts the responseDTO to json format to write on the response
     * @param responseDTO to be written on the response
     * @param response the object which the servlet can use to return data to the client.
     */
    private void writeResponse(HttpServletResponse response, ResponseDTO<String> responseDTO) {
        try {
            String jsonResponseDTO = responseDTO.convertToJson();
            response.getWriter().write(jsonResponseDTO);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Used for hashing passwords.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * AuthenticationManager bean for authenticating tokens inside AuthenticationService class.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Allowing all origins, method requests, and 'Authorization' header through my application.
     * @return Configured cors policy.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
