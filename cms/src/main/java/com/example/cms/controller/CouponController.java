package com.example.cms.controller;

import com.example.cms.dto.CouponDto;
import com.example.cms.dto.EligibleCouponRespDto;
import com.example.cms.dto.ShoppingCartDto;
import com.example.cms.entity.Coupon;
import com.example.cms.exceptions.CMSCustomException;
import com.example.cms.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/coupons")
public class CouponController {
    @Autowired
    CouponService couponService;

    @GetMapping()
    public List<Coupon> getAllCoupons() {
        try {
            return couponService.getAllCoupons();
        }catch (CMSCustomException ex){
            throw new CMSCustomException(ex.getMessage());
        }
    }
    @PostMapping()
    public List<Coupon> addNewCoupon(@RequestBody List<CouponDto> couponDto){
        try {
            return couponService.saveNewCoupon(couponDto);
        }catch (CMSCustomException ex){
            throw new CMSCustomException(ex.getMessage());
        }
    }
    @PutMapping("/{couponId}")
    public List<Coupon> updateCoupon(@PathVariable Long couponId,@RequestBody List<CouponDto> couponDto){
        try {
            return couponService.updateCoupon(couponDto,couponId);
        }catch (CMSCustomException ex){
            throw new CMSCustomException(ex.getMessage());
        }
    }

    //to scale we can give multiple ids of coupon as of now it supports only one coupon delete at 1 time
    @DeleteMapping("/{couponId}")
    public String deleteCoupon(@PathVariable Long couponId){
        try {
            return couponService.deleteCoupon(couponId);
        }catch (CMSCustomException ex){
            throw new CMSCustomException(ex.getMessage());
        }
    }

    @GetMapping("/{couponId}")
    public Coupon getCoupon(@PathVariable Long couponId) {
        try {
            return couponService.getCoupon(couponId);
        }catch (CMSCustomException ex){
            throw new CMSCustomException(ex.getMessage());
        }
    }

    @PostMapping("/applicable-coupons")
    public Set<EligibleCouponRespDto> getEligibleCoupons(@RequestBody ShoppingCartDto cartDetails){
        return couponService.findEligibleCoupons(cartDetails);
    }


}
