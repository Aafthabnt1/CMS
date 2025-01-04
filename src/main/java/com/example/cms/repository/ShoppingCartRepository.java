package com.example.cms.repository;

import com.example.cms.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
}
