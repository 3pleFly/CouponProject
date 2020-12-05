package com.coupon.demo.controllers;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.entities.Coupon;
import com.coupon.demo.facade.CompanyFacade;
import com.coupon.demo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyFacade companyFacade;
    private final AuthenticationService authenticationService;

    @PostMapping("/addcoupon")
    public ResponseEntity<ResponseDTO<Coupon>> addCoupon(@RequestBody CouponDTO couponDTO) {
        ResponseDTO<Coupon> responseDTO = companyFacade.addCoupon(couponDTO);
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

    @PutMapping("/updatecoupon")
    public ResponseEntity<ResponseDTO<Coupon>> updateCoupon(@RequestBody CouponDTO couponDTO) {
        ResponseDTO<Coupon> responseDTO = companyFacade.updateCoupon(couponDTO);
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

    @DeleteMapping("/deletecoupon/{couponID}")
    public ResponseEntity<ResponseDTO<String>> deleteCoupon(@PathVariable Long couponID) {
        ResponseDTO<String> responseDTO = companyFacade.deleteCoupon(couponID);
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


    @GetMapping("/details/coupons")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCompanyCoupons() {
        Long companyID = authenticationService.getPrincipalID();
        ResponseDTO<List<CouponDTO>> responseDTO = companyFacade.getCompanyCoupons(companyID);
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

    @GetMapping("/details/coupons/category/{categoryName}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCompanyCouponsByMaxPrice(
            @PathVariable String categoryName) {
        Long companyID = authenticationService.getPrincipalID();
        ResponseDTO<List<CouponDTO>> responseDTO =
                companyFacade.getCompanyCoupons(categoryName, companyID);
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

    @GetMapping("/details/coupons/price/{maxprice}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCompanyCouponsByMaxPrice(
            @PathVariable double maxprice) {
        Long companyID = authenticationService.getPrincipalID();
        ResponseDTO<List<CouponDTO>> responseDTO = companyFacade.getCompanyCoupons(maxprice,
                companyID);
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
    public ResponseEntity<ResponseDTO<CompanyDTO>> getCompanyDetails(HttpServletRequest request) {
        Long companyID = authenticationService.getPrincipalID();
        ResponseDTO<CompanyDTO> responseDTO = companyFacade.getCompanyDetails(companyID);
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
}
