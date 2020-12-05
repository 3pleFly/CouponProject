package com.coupon.demo.facade;

import com.coupon.demo.dto.CompanyDTO;
import com.coupon.demo.dto.CouponDTO;
import com.coupon.demo.dto.ResponseDTO;
import com.coupon.demo.model.entities.Company;
import com.coupon.demo.model.entities.Coupon;
import com.coupon.demo.exception.*;
import com.coupon.demo.service.dao.CategoryDao;
import com.coupon.demo.service.dao.CompanyDao;
import com.coupon.demo.service.dao.CouponDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CompanyFacade {

    private final CompanyDao companyDao;
    private final CouponDao couponDao;

    public ResponseDTO<Coupon> addCoupon(CouponDTO couponDTO) {
        try {
            Coupon addedCoupon = couponDao.addCoupon(couponDTO);
            return new ResponseDTO<>(addedCoupon, true, "Coupon added");
        } catch (AlreadyExists | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<Coupon> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<Coupon> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<Coupon> updateCoupon(CouponDTO couponDTO) {
        try {
            Coupon updatedCoupon = couponDao.updateCoupon(couponDTO);
            return new ResponseDTO<>(updatedCoupon, true, "Coupon updated");
        } catch (BadUpdate | MissingAttributes | LengthException e) {
            log.warn(e.getMessage());
            ResponseDTO<Coupon> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(409);
            return responseDTO;
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<Coupon> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            ResponseDTO<Coupon> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<String> deleteCoupon(Long id) {
        try {
            couponDao.deleteCoupon(id);
            return new ResponseDTO<>("Coupon deleted!", true, "Coupon deleted");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<String> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<List<CouponDTO>> getCompanyCoupons(Long companyID) {
        try {
            List<CouponDTO> companyCoupons =
                    couponDao.convertToCouponDTO(couponDao.getCompanyCoupons(companyID));
            return new ResponseDTO<>(companyCoupons, true, "Retrieved all company coupons successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<List<CouponDTO>> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<List<CouponDTO>> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<List<CouponDTO>> getCompanyCoupons(String categoryName, Long companyID) {
        try {
            List<CouponDTO> companyCoupons = couponDao.convertToCouponDTO(
                    couponDao.getCompanyCoupons(categoryName, companyID));
            return new ResponseDTO<>(companyCoupons, true, "Retrieved all company coupons successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<List<CouponDTO>> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<List<CouponDTO>> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<List<CouponDTO>> getCompanyCoupons(double maxPrice, Long companyID) {
        try {
            List<CouponDTO> companyCoupons =
                    couponDao.convertToCouponDTO(couponDao.getCompanyCoupons(maxPrice, companyID));
            return new ResponseDTO<>(companyCoupons, true, "Retrieved all company coupons successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<List<CouponDTO>> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<List<CouponDTO>> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }

    public ResponseDTO<CompanyDTO> getCompanyDetails(Long companyID) {
        try {
            Company companyDetails = companyDao.getOneCompany(companyID);
            return new ResponseDTO<>(new CompanyDTO(
                    companyDetails.getId(),
                    companyDetails.getName(),
                    companyDetails.getEmail(),
                    couponDao.convertToCouponDTO(companyDetails.getCoupons())), true, "Retrieved company details successfully");
        } catch (NotFound e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, e.getMessage());
            responseDTO.setHttpErrorCode(404);
            return responseDTO;
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDTO<CompanyDTO> responseDTO = new ResponseDTO<>(null, false, "Internal error");
            responseDTO.setHttpErrorCode(500);
            return responseDTO;
        }
    }
}
