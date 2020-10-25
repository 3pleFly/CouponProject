package com.coupon.demo.controllers;

import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.CustomerDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.facade.CustomerFacade;
import com.coupon.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerFacade customerFacade;
    private final JwtUtil jwtUtil;

    @PostMapping("/purchase/{couponID}")
    public ResponseEntity<ResponseDTO<String>> purchaseCoupon(
            @PathVariable Long couponID, HttpServletRequest request) {
        Long customerID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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


    @GetMapping("/coupons/")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCustomerCoupons(HttpServletRequest request) {
        Long customerID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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

    @GetMapping("/coupons/category/{categoryID}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCustomerCoupons(HttpServletRequest request,
            @PathVariable("categoryID") Long categoryID) {
        Long customerID = jwtUtil.extractID.apply(request.getHeader("authorization"));
        ResponseDTO<List<CouponDTO>> responseDTO =
                customerFacade.getCustomerCoupons(categoryID, customerID);
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
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCustomerCoupons(HttpServletRequest request,
            @PathVariable("maxprice") double maxprice) {
        Long customerID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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
    public ResponseEntity<ResponseDTO<CustomerDTO>> getCustomerDetails(HttpServletRequest request) {
        Long customerID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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
