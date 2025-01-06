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
                    var productCodes=coupon.getBxgyProducts().getProductCode();
                    var freeProductCodes=coupon.getBxgyProducts().getGiveFreeProductCode();
                    cartDetails.getProducts().stream().forEach(cartProduct->{
                        var eligibleProduct= productCodes.stream().filter(code->cartProduct.getProductCode().equals(code)).findAny();
                        eligibleProduct.ifPresent(buyProductsEligible::add);
                    });
                    if(buyProductsEligible.size()>2 ){
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

    public ShoppingCartDto getCouponDiscount(ShoppingCartDto cartDetails, Long couponId) {
        var coupon=couponRepository.findById(couponId);
        if(coupon.isPresent()){
           return calculateDiscountPrice(cartDetails,coupon);
        }else{
            return calculateWithOutDiscount(cartDetails);
        }

    }

    private ShoppingCartDto calculateWithOutDiscount(ShoppingCartDto cartDetails) {
        cartDetails.setTotalPrice(calculateCartPrice(cartDetails));
        return cartDetails;
    }

    private ShoppingCartDto calculateDiscountPrice(ShoppingCartDto cartDetails, Optional<Coupon> coupon) {
        var cartTotalPrice=calculateCartPrice(cartDetails);
        if(Objects.isNull(getCouponTypes(coupon.get()))){
            calculateWithOutDiscount(cartDetails);
        }else{
            if(coupon.get().getCouponType()==CouponType.Cart_wise){
                cartDetails.setTotalPrice(cartTotalPrice);
                if(cartDetails.getTotalPrice().compareTo(coupon.get().getThresholdAmount())>0) {
                    cartDetails.setTotalDiscountPrice(calculateDiscount(cartDetails.getTotalPrice(), coupon.get().getDiscountPercentage()));
                    cartDetails.setFinalPrice(calculateFinalAmount(cartDetails.getTotalPrice(), cartDetails.getTotalDiscountPrice()));
                }
            }else if(coupon.get().getCouponType()==CouponType.Product_wise){
                cartDetails.setTotalPrice(cartTotalPrice);
                cartDetails.setFinalPrice(applyProductWiseCoupon(coupon.get(),  cartDetails.getProducts()));
                cartDetails.setTotalDiscountPrice(cartDetails.getTotalPrice().subtract(cartDetails.getFinalPrice()));
            }else if(coupon.get().getCouponType()==CouponType.BxGy){
                   // boolean a=findByTwoGetOneProductIsEligibleOrNot(coupon.get().getBxgyProducts(),cartDetails.getProducts(),coupon.get().getRepetitionLimit(),cartDetails.getFreeProducts());
                //TODO:commented the code
                //TODO:need to add the logic when bxgy catagory coupon are used
            }
        }
        return cartDetails;
    }

//    private boolean findByTwoGetOneProductIsEligibleOrNot(BXGYProducts bxgyProducts, List<ProductDto> products, int repetitionLimit,List<ProductDto> freeProducts) {
//        var purchaseOffer=bxgyProducts.getProductCode();
//        var freeOffer=bxgyProducts.getGiveFreeProductCode();
//        List<String>getCountOfAllProductInCart=new ArrayList<>();
//        for(var product:products){
//            getCountOfAllProductInCart.add(product.getProductCode());
//        }
//        var filterUniqueProductsCount=getCountOfAllProductInCart.stream().distinct().count();
//        var matchingProductCount=products.stream().filter(product ->purchaseOffer.contains(product.getProductCode())).count();
//        var freeProductGiveAwayCount=matchingProductCount/2;
//        if(freeProductGiveAwayCount>0 && freeProductGiveAwayCount<freeOffer.size()){
//            checkAnyFreeProductsAreEligibleInCarts(products,repetitionLimit,filterUniqueProductsCount,freeOffer);
//        }
//
//
//
//
//
//
//        if(freeProductGiveAwayCount>0 && freeProductGiveAwayCount<freeOffer.size()){
//            //assuming the free product is part of cart then we add extra quantity else the product is different we will fetch that product from product DB and add in our Product list
//            var getFreeProductFromCart=products.stream().filter(product -> freeOffer.contains(product.getProductCode())).toList();
//            if(freeProductGiveAwayCount>repetitionLimit) {//if the get product reaches more than repetition limit restrict to repetition limit.
//               if(!getFreeProductFromCart.isEmpty() && getFreeProductFromCart.size()<filterUniqueProductsCount){
//                   for(int i=0;i<getFreeProductFromCart.size();i++){
//                       int addQuantity=1;
//                       freeProducts.add(getFreeProductFromCart.get(i));
//
//                       freeProducts.get(i).setProductQuantity(freeProducts.get(i).getProductQuantity()+addQuantity);
//                   }
//               }else if(getFreeProductFromCart.size()>filterUniqueProductsCount ){
//                   if(filterUniqueProductsCount>repetitionLimit){
//                       for(int i=0;i<repetitionLimit;i++){
//                           getFreeProductFromCart.get(i).setProductQuantity(getFreeProductFromCart.get(i).getProductQuantity()+1);
//                       }
//                   }else{
//                       for(int i=0;i<repetitionLimit;i++){
//                           getFreeProductFromCart.get(i).setProductQuantity(getFreeProductFromCart.get(i).getProductQuantity()+1);
//                       }
//                   }
//               }
//           }else if(getFreeProductFromCart.size()==0){//cart item do not have free product so we need to get take coups free product id and set it in cart product list
//
//            }else if(getFreeProductFromCart.size()!=0){
//                getFreeProductFromCart.get(0).setProductQuantity((int) (getFreeProductFromCart.get(0).getProductQuantity()+freeProductGiveAwayCount));
//            }
//        }
//    }

    private void checkAnyFreeProductsAreEligibleInCarts(List<ProductDto> products, int repetitionLimit, long filterUniqueProductsCount, List<String> freeOffer) {
        var getFreeProductFromCart=products.stream().filter(product -> freeOffer.contains(product.getProductCode())).toList();

    }

    private BigDecimal calculateFinalAmount(BigDecimal totalPrice, BigDecimal totalDiscountPrice) {
        return totalPrice.subtract(totalDiscountPrice);
    }

    private BigDecimal calculateDiscount(BigDecimal totalPrice,BigDecimal discountPercentage) {
        return totalPrice.multiply(BigDecimal.valueOf(100).subtract(discountPercentage)).multiply(BigDecimal.valueOf(0.01));
    }

    private BigDecimal applyProductWiseCoupon(Coupon coupon, List<ProductDto> products) {
        BigDecimal totalProductPrice = BigDecimal.ZERO;
        List<BigDecimal>collectProductPrice=new ArrayList<>();

        if(!products.isEmpty()){
            products.forEach(product ->{

                    if(coupon.getProductCodes().stream().anyMatch(couponProductCode->couponProductCode.getProductCode().equals(product.getProductCode()))){
                        var productPrice=calculateProductPriceWithQuantity(product.getProductPrice(),product.getProductQuantity());
                        product.setCouponDiscountPrice(String.valueOf(productPrice.multiply(coupon.getDiscountPercentage()).multiply(BigDecimal.valueOf(0.01))));
                        product.setCouponUsed(coupon.getCouponCode());
                        collectProductPrice.add(calculateDiscount(productPrice,coupon.getDiscountPercentage()));
                    }else{
                        collectProductPrice.add(calculateProductPriceWithQuantity(product.getProductPrice(),product.getProductQuantity()));
                    }
            } );
        }

        return collectProductPrice.stream().reduce(BigDecimal::add).get();
    }

    private BigDecimal calculateProductPriceWithQuantity(BigDecimal productPrice, int productQuantity) {
        return productPrice.multiply(BigDecimal.valueOf(productQuantity));
    }


    private CouponType getCouponTypes(Coupon coupon) {
        if(coupon.getCouponType()==CouponType.Cart_wise){
            return CouponType.Cart_wise;
        }else if(coupon.getCouponType()==CouponType.Product_wise){
            return CouponType.Product_wise;
        } else if (coupon.getCouponType()==CouponType.BxGy) {
            return CouponType.BxGy;
        }else {
            return null;
        }
    }
}
