package com.coupon.demo.controllers;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Company;
import com.coupon.demo.entities.Coupon;
import com.coupon.demo.facade.CompanyFacade;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.model.Scope;
import com.coupon.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyFacade companyFacade;

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


    @GetMapping("/{companyID}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getAllCoupons(@PathVariable Long companyID) {
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

    @GetMapping("/{companyID}/category/{categoryID}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getAllCouponsByMaxPrice(
            @PathVariable("companyID") Long companyID,
            @PathVariable("categoryID") Long categoryID) {
        ResponseDTO<List<CouponDTO>> responseDTO = companyFacade.getCompanyCoupons(categoryID,
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

    @GetMapping("/{companyID}/price/{maxprice}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getAllCouponByMaxPrice(
            @PathVariable("companyID") Long companyID, @PathVariable("maxprice") double maxprice) {
        System.out.println(maxprice);
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

    @GetMapping("/details/{companyID}")
    public ResponseEntity<ResponseDTO<CompanyDTO>> getCompanyDetails(@PathVariable Long companyID) {
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
