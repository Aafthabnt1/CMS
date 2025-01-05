package com.example.cms.controllerTest;

import com.example.cms.controller.CouponController;
import com.example.cms.service.CouponService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CouponControllerTest {
    @InjectMocks
    CouponController couponController;

    @Mock
    CouponService couponService;

    @Test
    void getAllCouponsTest(){
        var expRes=couponController.getAllCoupons();
        Assertions.assertEquals(0,expRes.size());
    }
}
