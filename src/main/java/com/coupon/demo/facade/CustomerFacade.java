package com.coupon.demo.facade;

import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Category;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.entities.Customer;
import com.coupon.demo.exception.AlreadyExists;
import com.coupon.demo.exception.CouponExpired;
import com.coupon.demo.exception.CouponNotAvailable;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CouponDao;
import com.coupon.demo.service.dao.CustomerDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerFacade {

    private final CouponDao couponDao;
    private final CustomerDao customerDao;
    private final CompanyFacade companyFacade;

    public ResponseDTO<String> purchaseCoupon(Long couponID, Long customerID) {
        try {
            couponDao.addCouponPurchase(couponID, customerID);
            return new ResponseDTO<>(null, true, "purchaseCoupon successful");
        } catch (CouponNotAvailable | AlreadyExists | CouponExpired e) {
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(
                    null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<List<CouponDTO>> getCustomerCoupons(Long customerID) {
        try {
            List<CouponDTO> couponDTOS =
                    companyFacade.convertToCouponDTO(couponDao.getCustomerCoupons(customerID));
            return new ResponseDTO<>(couponDTOS, true, "getCustomerCoupons successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }

    }

    public ResponseDTO<List<CouponDTO>> getCustomerCoupons(Long categoryID, Long customerID) {
        try {
            List<CouponDTO> couponDTOS =
                    companyFacade.convertToCouponDTO(couponDao.getCustomerCoupons(categoryID,
                            customerID));
            return new ResponseDTO<>(couponDTOS, true, "getCustomerCoupons successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }
    }

    public ResponseDTO<List<CouponDTO>> getCustomerCoupons(double maxPrice, Long customerID) {
        try {
            var couponDTOS =
                    companyFacade.convertToCouponDTO(couponDao.getCustomerCoupons(maxPrice,
                            customerID));
            return new ResponseDTO<>(couponDTOS, true, "getCustomerCoupons successful");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }
    }

    public ResponseDTO<CustomerDTO> getCustomerDetails(Long customerID) {
        try {
            Customer customer = customerDao.getOneCustomer(customerID);
            return new ResponseDTO<>(new CustomerDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    companyFacade.convertToCouponDTO(customer.getCoupons()))
                    , true, "getCustomerCoupons successful");

        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseDTO<>(null, false, "Internal error");
        }

    }

}
