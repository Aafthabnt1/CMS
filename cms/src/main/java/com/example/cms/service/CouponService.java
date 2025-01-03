package com.example.cms.service;

import com.example.cms.bean.ModelMapperConfig;
import com.example.cms.dto.CouponDto;
import com.example.cms.entity.Coupon;
import com.example.cms.exceptions.CMSCustomException;
import com.example.cms.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;
    @Autowired
    ModelMapperConfig modelMapper;
    public List<Coupon> getAllCoupons() throws CMSCustomException {
        var couponList=couponRepository.findAll();
        if(couponList.isEmpty()){
            throw new CMSCustomException("No Coupons Available");
        }
        return couponList;


    }

    public Coupon getCoupon(Long couponId) throws CMSCustomException {
        var couponInfo=couponRepository.findById(couponId);
        if(couponInfo.isEmpty()){
            throw new CMSCustomException("Invalid Coupon Id");
        }
        return couponInfo.get();
    }

    public List<Coupon> saveNewCoupon(List<CouponDto> couponDto) {
        List<Coupon> coupons=new ArrayList<>();
        couponDto.forEach(couponDto1->coupons.add(modelMapper.modelMapper().map(couponDto1,Coupon.class)));
        return couponRepository.saveAllAndFlush(coupons);

    }

    public List<Coupon> updateCoupon(List<CouponDto> couponDto, Long couponId) {
        List<Coupon> coupons=new ArrayList<>();
        var coupon=couponRepository.findById(couponId);
        if(coupon.isPresent()){
            couponDto.forEach(couponDto1 -> coupons.add(modelMapper.modelMapper().map(couponDto1,Coupon.class)));
            return couponRepository.saveAllAndFlush(coupons);
        }
        throw new CMSCustomException("Invalid Coupon Id");
    }

    public String deleteCoupon(Long couponId) {
        try {
            couponRepository.deleteById(couponId);
            return "Successfully Deleted The Coupon";
        }catch (CMSCustomException ex){
            throw new CMSCustomException("Delete Operation Failed");
        }

    }
}
