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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyFacade companyFacade;
    private final JwtUtil jwtUtil;

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
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCompanyCoupons(HttpServletRequest request) {
        Long companyID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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

    @GetMapping("/details/coupons/category/{categoryID}")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getCompanyCouponsByMaxPrice(HttpServletRequest request,
            @PathVariable Long categoryID) {
        Long companyID = jwtUtil.extractID.apply(request.getHeader("authorization"));
        ResponseDTO<List<CouponDTO>> responseDTO =
                companyFacade.getCompanyCoupons(categoryID, companyID);
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
            HttpServletRequest request, @PathVariable double maxprice) {
        Long companyID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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
        Long companyID = jwtUtil.extractID.apply(request.getHeader("authorization"));
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
