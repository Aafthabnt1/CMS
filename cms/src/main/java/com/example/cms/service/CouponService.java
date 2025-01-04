package com.example.cms.service;

import com.example.cms.bean.ModelMapperConfig;
import com.example.cms.dto.CouponDto;
import com.example.cms.dto.EligibleCouponRespDto;
import com.example.cms.dto.ProductDto;
import com.example.cms.dto.ShoppingCartDto;
import com.example.cms.entity.Coupon;
import com.example.cms.entity.CouponType;
import com.example.cms.exceptions.CMSCustomException;
import com.example.cms.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
        couponDto.forEach(couponDto1->{
            var coupon=modelMapper.modelMapper().map(couponDto1,Coupon.class);
            if(CouponType.Cart_wise.getCouponType().equals(couponDto1.getCouponType())){
                coupon.setCouponType(CouponType.Cart_wise);
            }else if(CouponType.Product_wise.getCouponType().equals(couponDto1.getCouponType())){
                coupon.setCouponType(CouponType.Product_wise);
            }else if(CouponType.BxGy.getCouponType().equals(couponDto1.getCouponType())){
                coupon.setCouponType(CouponType.BxGy);
            }else {
                throw new CMSCustomException("Please set correct coupon types:Cart-wise,Product-wise,BxGy");
            }
            coupons.add(coupon);
        });
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

    public Set<EligibleCouponRespDto> findEligibleCoupons(ShoppingCartDto cartDetails) {
        //first i am checking cart total amount so I will check any cart coupons are avaliable
        List<Coupon> eligibleCoupons=new LinkedList<>();
        findCartWiseCoupons(cartDetails, eligibleCoupons);
        findProductWiseCoupons(cartDetails, eligibleCoupons);
        findBxGy(cartDetails,eligibleCoupons);
        if(eligibleCoupons.isEmpty()){
            throw new CMSCustomException("No Coupons Are Eligible to Apply");
        }
        Set<EligibleCouponRespDto> response=new HashSet<>();
        for (var c:eligibleCoupons){
            response.add(modelMapper.modelMapper().map(c, EligibleCouponRespDto.class));
        }

        return response;
    }

    private void findBxGy(ShoppingCartDto cartDetails, List<Coupon> eligibleCoupons) {
        var bxgyCoupons=couponRepository.findByCouponType(CouponType.BxGy.getCouponType());
        if(!bxgyCoupons.isEmpty()){
            checkCartProductsAreEligibleOrNot(cartDetails,bxgyCoupons,eligibleCoupons);
        }
    }

    private void checkCartProductsAreEligibleOrNot(ShoppingCartDto cartDetails, List<Coupon> bxgyCoupons, List<Coupon> eligibleCoupons) {

        bxgyCoupons.stream().forEach(
                coupon -> {
                    Set<String> buyProductsEligible=new HashSet<>();
                    Set<String> freeProductsEligible=new HashSet<>();
                    var productCodes=coupon.getBxgyProducts().getProductCode();
                    var freeProductCodes=coupon.getBxgyProducts().getGiveFreeProductCode();
                    cartDetails.getProducts().stream().forEach(cartProduct->{
                        var eligibleProduct= productCodes.stream().filter(code->cartProduct.getProductCode().equals(code)).findAny();
                        var freeProduct= freeProductCodes.stream().filter(code->cartProduct.getProductCode().equals(code)).findAny();
                        eligibleProduct.ifPresent(buyProductsEligible::add);
                        freeProduct.ifPresent(freeProductsEligible::add);
                    });
                    if(buyProductsEligible.size()>2 && freeProductsEligible.size()>=1){
                        eligibleCoupons.add(coupon);
                    }
                }

        );

    }

    private void findProductWiseCoupons(ShoppingCartDto cartDetails, List<Coupon> eligibleCoupons) {
        var codes= cartDetails.getProducts().stream().map(ProductDto::getProductCode).collect(Collectors.toCollection(ArrayList::new));
        if(!codes.isEmpty()) {
            var productWiseCoupons = couponRepository.findByCouponType(CouponType.Product_wise.getCouponType());
            if(!productWiseCoupons.isEmpty()){
                productWiseCoupons.stream().forEach(coupon -> {
                    coupon.getProductCodes().stream().forEach(product->{
                        if(codes.contains(product.getProductCode())){
                            eligibleCoupons.add(coupon);
                        }
                    });

                });
            }
        }else{
            throw new CMSCustomException("Cart Must Contain At-Least 1 Product");
        }
    }

    private void findCartWiseCoupons(ShoppingCartDto cartDetails, List<Coupon> eligibleCoupons) {

        var cartWiseCoupons=couponRepository.findByCouponTypeAndThresholdAmountGreaterThanEqual(CouponType.Cart_wise.getCouponType(), calculateCartPrice(cartDetails));
        if(!cartWiseCoupons.isEmpty()){
            eligibleCoupons.addAll(cartWiseCoupons);
        }
    }

    private BigDecimal calculateCartPrice(ShoppingCartDto cartDetails) {
        List<ProductDto> products = cartDetails.getProducts();

        BigDecimal finalAmount = products.stream()
                .filter(product -> product.getProductQuantity() != 0 && product.getProductPrice().compareTo(BigDecimal.ONE) != 0) // Apply filter first
                .map(product -> product.getProductPrice().multiply(BigDecimal.valueOf(product.getProductQuantity()))) // Multiply price by quantity
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (finalAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new CMSCustomException("Please check the price and quantity");
        }
        return finalAmount;
    }
}
