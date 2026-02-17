package com.team1.goorm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name= "product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private String image;

    @Column(name = "image_2")
    private String image2;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;
}
