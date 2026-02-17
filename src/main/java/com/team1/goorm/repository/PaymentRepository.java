package com.team1.goorm.repository;

import com.team1.goorm.domain.entity.Payment;
import com.team1.goorm.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findTop5ByUserOrderByApprovedAtDesc(User user);
}
