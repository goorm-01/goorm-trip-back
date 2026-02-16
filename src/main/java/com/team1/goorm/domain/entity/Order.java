package com.team1.goorm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status; // READY, DONE, CANCELLED 3가지 타입만 가능

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    /** 주문 완료 처리 */
    public void markAsDone() {
        this.status = OrderStatus.DONE;
    }
}
