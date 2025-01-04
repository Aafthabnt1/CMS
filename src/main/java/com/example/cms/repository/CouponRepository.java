package com.example.cms.repository;

import com.example.cms.entity.Coupon;
import com.example.cms.entity.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    @Query(value = "SELECT * FROM COUPONS WHERE  COUPON_TYPE= :couponType AND THRESHOLD_AMOUNT <= :totalPrice",nativeQuery = true)
    List<Coupon> findByCouponTypeAndThresholdAmountGreaterThanEqual(@Param("couponType") String couponType,@Param("totalPrice") BigDecimal totalPrice);

    //json value is not supported in h2 db trying new approach
//    @Query(value = "SELECT * FROM COUPONS WHERE JSON_VALUE(PRODUCT_WISE_PRODUCT_ID_DETAILS, '$.productCode') IN :productCodes  COUPON_TYPE= :couponType ",nativeQuery = true)
//    List<Coupon> findByCouponsForProductType(@Param("productCodes") List<String> productCodes,@Param("couponType") String couponType);

    @Query(value = "SELECT * FROM COUPONS WHERE COUPON_TYPE= :couponType ",nativeQuery = true)
    List<Coupon> findByCouponType(@Param("couponType") String couponType);
}
