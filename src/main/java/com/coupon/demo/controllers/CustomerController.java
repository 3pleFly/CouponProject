package com.coupon.demo.controllers;

import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.facade.CustomerFacade;
import com.coupon.demo.service.AuthenticationService;
import com.coupon.demo.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerFacade customerFacade;
    private final AuthenticationService authenticationService;

    @PostMapping("/purchase/{couponID}")
    public ResponseEntity<ResponseDTO<String>> purchaseCoupon(
            @PathVariable Long couponID) {
        Long customerID = authenticationService.getPrincipalID();
        ResponseDTO<String> responseDTO = customerFacade.purchaseCoupon(couponID, customerID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @PostMapping("/remove/{couponID}")
    public ResponseEntity<ResponseDTO<String>> removePurchase(
            @PathVariable Long couponID) {
        Long customerID = authenticationService.getPrincipalID();
        ResponseDTO<String> responseDTO = customerFacade.removePurchase(couponID, customerID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }


    @GetMapping("/coupons")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCustomerCoupons() {
        Long customerID = authenticationService.getPrincipalID();
        ResponseDTO<List<CouponDTO>> responseDTO =
                customerFacade.getCustomerCoupons(customerID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/coupons/category/{categoryName}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCustomerCoupons(
            @PathVariable String categoryName) {
        Long customerID = authenticationService.getPrincipalID();
        ResponseDTO<List<CouponDTO>> responseDTO =
                customerFacade.getCustomerCoupons(categoryName, customerID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/coupons/price/{maxprice}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCustomerCoupons(
            @PathVariable("maxprice") double maxprice) {
        Long customerID = authenticationService.getPrincipalID();
        ResponseDTO<List<CouponDTO>> responseDTO =
                customerFacade.getCustomerCoupons(maxprice, customerID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }


    @GetMapping("/details")
    public ResponseEntity<ResponseDTO<CustomerDTO>> getCustomerDetails() {
        Long customerID = authenticationService.getPrincipalID();
        ResponseDTO<CustomerDTO> responseDTO = customerFacade.getCustomerDetails(customerID);
        if (responseDTO.isSuccess()) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }
}
