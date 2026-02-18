package com.team1.goorm.repository;

import com.team1.goorm.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c From Cart c JOIN FETCH c.product WHERE c.userId = :userId")
    List<Cart> findAllByUserIdWithProduct(@Param("userId") Long userId);
}

