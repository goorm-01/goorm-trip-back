package com.team1.goorm.domain.dto;

import com.team1.goorm.domain.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    // 필드 정의
    private Long productId;           // API의 contentid에서 변환
    private String productName;       // API의 title
    private Integer price;            // 직접 입력
    private String image;             // API의 Image
    private String image2;            // API의 Image2
    private String description;       // 직접 입력
    private String category;          // 직접 입력


    // ProductDto
    /*
    * TourApiListResponseDto에서 받아오는 데이터 객체를
    * ProductDto로 변환하는 정적 팩토리 메서드
    * */
    public static ProductDto fromApiItem(
            TourApiListResponseDto.Item item, // API에서 받아오는 데이터 객체
            Integer price,  // 가격 - 직접 입력
            String category,  // 카테고리 - 직접 입력
            String description) { // 설명 - 직접 입력
        return ProductDto.builder()
                .productId(Long.parseLong(item.getContentid())) // String으로 받아오는 값을 Long으로 변환
                .productName(item.getTitle()) // title을 productName으로 매핑
                .price(price) // 값을 직접 입력
                .image(item.getFirstimage()) // API에서 받아오는 이미지 URL
                .image2(null) // 두번째 이미지, null로 일단 지정
                .description(null) // 설명, null로 일단 지정
                .category(category) // 카테고리 값을 직접 입력
                .build(); // ProductDto 객체 생성
    }

    /**
     * ProductDto를 Product 엔티티로 변환
     */
    public Product toEntity() {
        return Product.builder()
                .productId(this.productId)
                .productName(this.productName)
                .price(this.price)
                .image(this.image)
                .image2(this.image2)
                .description(this.description)
                .category(this.category)
                .build();
    }

    /**
     * Product 엔티티로부터 ProductDto 생성
     */
    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .image(product.getImage())
                .image2(product.getImage2())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }
}