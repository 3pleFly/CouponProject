package com.coupon.demo;

import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@AllArgsConstructor
@SpringBootTest
class AdminTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void deleteComp(){
        companyRepository.deleteById(1L);
    }

}
