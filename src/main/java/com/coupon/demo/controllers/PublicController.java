package com.coupon.demo.controllers;

import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.entities.Category;
import com.coupon.demo.facade.AdminFacade;
import com.coupon.demo.model.AuthRequest;
import com.coupon.demo.service.LoginAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private final AdminFacade adminFacade;
    private final LoginAuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<String>> authenticate(@RequestBody AuthRequest authRequest) {

        ResponseDTO<String> responseDTO = authenticationService.authenticate(authRequest);
        if (responseDTO.isSuccess()) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(responseDTO.getHttpErrorCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO<List<Category>>> getAllCategories() {
        ResponseDTO<List<Category>> responseDTO = adminFacade.getAllCategories();
        if (responseDTO.isSuccess()) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }

    @GetMapping("/coupons")
    public ResponseEntity<ResponseDTO<List<CouponDTO>>> getAllCoupons() {
        ResponseDTO<List<CouponDTO>> responseDTO = adminFacade.getAllCoupons();
        if (responseDTO.isSuccess()) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity
                    .status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseDTO);
        }
    }



}


