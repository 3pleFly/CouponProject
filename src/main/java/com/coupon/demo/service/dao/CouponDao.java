package com.coupon.demo.service.dao;

import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.model.entities.Coupon;
import com.coupon.demo.model.entities.Customer;
import com.coupon.demo.exception.*;
import com.coupon.demo.repositories.CategoryRepository;
import com.coupon.demo.repositories.CompanyRepository;
import com.coupon.demo.repositories.CouponRepository;
import com.coupon.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CouponDao {

    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public Coupon addCoupon(CouponDTO couponDTO) {
        checkNull(couponDTO);
        checkCharacterLength(couponDTO);
        Coupon coupon = convertToCoupon(couponDTO);
        couponRepository.findAllByTitle(coupon.getTitle()).forEach(returnedCoupons -> {
            if (coupon.getCompany().equals(returnedCoupons.getCompany())) {
                throw new AlreadyExists("Coupon already exists by the title" + coupon.getTitle());
            }
        });
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(CouponDTO couponDTO) {
        checkNull(couponDTO);
        checkCouponExists(couponDTO.getId());
        checkCharacterLength(couponDTO);
        Coupon coupon = convertToCoupon(couponDTO);
        if (!couponRepository.findById(coupon.getId()).get().getCompany().equals(coupon.getCompany())) {
            throw new BadUpdate("Cannot change coupon's company: " + coupon.getCompany().getId());
        }
        return couponRepository.save(coupon);
    }

    @Transactional
    public void deleteCoupon(Long couponID) {
        checkCouponExists(couponID);
        couponRepository.deleteFromCvCByCouponId(couponID);
        couponRepository.deleteById(couponID);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getOneCoupon(Long id) {
        checkCouponExists(id);
        return couponRepository.findById(id).get();
    }


    public List<Coupon> getCompanyCoupons(Long companyID) {
        checkCompanyExists(companyID);
        return companyRepository.findById(companyID).get().getCoupons();
    }

    public List<Coupon> getCompanyCoupons(String categoryName, Long companyID) {
        checkCompanyExists(companyID);
        return couponRepository.findAllByCompanyAndCategory(
                companyRepository.findById(companyID).get(),
                categoryRepository.findByCategory(categoryName).get()
        );
    }

    public List<Coupon> getCompanyCoupons(double maxPrice, Long companyID) {
        checkCompanyExists(companyID);
        return couponRepository.findAllByCompanyAndMaxPrice(companyRepository.findById(companyID).get(), maxPrice);
    }

    public List<Coupon> getCustomerCoupons(Long customerID) {
        checkCustomerExists(customerID);
        return customerRepository.findById(customerID).get().getCoupons();
    }

    public List<Coupon> getCustomerCoupons(String categoryName, Long customerID) {
        checkCustomerExists(customerID);
        return couponRepository.findAllByCustomerAndCategory(
                customerRepository.findById(customerID).get(),
                categoryRepository.findByCategory(categoryName).get());
    }

    public List<Coupon> getCustomerCoupons(double maxPrice, Long customerID) {
        checkCustomerExists(customerID);
        return couponRepository.findAllByCustomerAndMaxPrice(customerID, maxPrice);
    }

    public void addCouponPurchase(Long couponID, Long customerID) {
        checkCustomerExists(customerID);
        checkCouponExists(couponID);
        Coupon coupon = getOneCoupon(couponID);
        if (coupon.getAmount().equals(0)) {
            throw new CouponNotAvailable("Coupon's available amount is 0");
        }
        if (coupon.getEndDate().isBefore(LocalDate.now())) {
            throw new CouponExpired("Coupon is expired");
        }
        if (customerRepository.findById(customerID).get().getCoupons().contains(coupon)) {
            throw new AlreadyExists("Customer already has this coupon");
        }
        Customer customer = customerRepository
                .findById(customerID).get();
        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount() - 1);
        customerRepository.save(customer);
        couponRepository.save(coupon);
    }

    public void deleteCouponPurchase(Long couponID, Long customerID) {
        checkCustomerExists(customerID);
        checkCouponExists(couponID);
        Customer customer = customerRepository.findById(customerID).get();
        Coupon coupon = couponRepository.findById(couponID).get();
        if (customer.getCoupons().remove(coupon)) {
            coupon.setAmount(coupon.getAmount() + 1);
            customerRepository.save(customer);
            couponRepository.save(coupon);
        } else {
            throw new CouponNotAvailable("Cannot remove coupon since customer doesn't have it");
        }
    }

    public Coupon convertToCoupon(CouponDTO couponDTO) {
        return new Coupon(
                couponDTO.getId(),
                categoryRepository.findByCategory(couponDTO.getCategoryName()).get(),
                companyRepository.findById(couponDTO.getCompanyID()).get(),
                couponDTO.getTitle(),
                couponDTO.getDescription(),
                couponDTO.getStartDate(),
                couponDTO.getEndDate(),
                couponDTO.getAmount(),
                couponDTO.getPrice(),
                couponDTO.getImage());
    }

    public List<CouponDTO> convertToCouponDTO(List<Coupon> couponList) {
        List<CouponDTO> couponDTOs = new ArrayList<>();
        couponList.forEach(coupon -> {
           couponDTOs.add(convertToCouponDTO(coupon));
        });
        return couponDTOs;
    }

    public CouponDTO convertToCouponDTO(Coupon coupon) {
        return new CouponDTO(
                coupon.getId(),
                coupon.getCategory().getCategory(),
                coupon.getCompany().getId(),
                coupon.getTitle(),
                coupon.getDescription(),
                coupon.getStartDate(),
                coupon.getEndDate(),
                coupon.getAmount(),
                coupon.getPrice(),
                coupon.getImage());
    }


    private void checkNull(CouponDTO couponDTO) {
        if (couponDTO.getCategoryName() == null ||
                couponDTO.getCompanyID() == null ||
                couponDTO.getTitle() == null ||
                couponDTO.getStartDate() == null ||
                couponDTO.getEndDate() == null ||
                couponDTO.getAmount() == null ||
                couponDTO.getPrice() == null) {
            throw new MissingAttributes("The coupon is missing some attributes(price or category, title, etc...)");
        }
    }

    private void checkCharacterLength(CouponDTO couponDTO) {
        if (couponDTO.getTitle().length() > 30 || couponDTO.getDescription().length() > 100) {
            throw new LengthException("Coupon title or description exceeds limit");
        }
    }

    private void checkCouponExists(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new NotFound("Coupon by ID is not found: " + id);
        }
    }

    private void checkCustomerExists(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFound("Customer by ID is not found: " + id);
        }
    }

    private void checkCompanyExists(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new NotFound("Company by ID is not found: " + id);
        }
    }

}
