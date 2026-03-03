## 여행 상품 판매 서비스
Spring Boot을 활용하여 여행 상품을 판매하는 웹 서비스의 백엔드 서버입니다. 상품 조회, 장바구니, 주문 및 결제 기능을 RESTful API로 제공합니다.

### 진행 기간
2026.02.09. ~ 2026.03.03

## 프로젝트 목표

- SpringBoot을 활용한 RESTful API 설계 및 구현
- API 명세서를 기반으로 프론트와 백엔드 간의 사전 합의와 기능 설계 흐름 이해
- 클라우드 배포 환경 구성
- 테스트 코드 작성을 통한 기능 검증 및 자동화 테스트

## 사용 기술
- Java
- SpringBoot
- JPA
- PostgreSQL (Supabase)
- JUnit
- Swagger
- Render, Docker

## 파일 구조
```
goorm-trip-back/
└── src/main/java/
    ├── controller/       # HTTP 요청 처리 (API 엔드포인트)
    ├── service/          # 비즈니스 로직
    ├── repository/       # 데이터 접근 (JPA)
    ├── domain/           # Entity 클래스
    └── dto/              # 요청/응답 DTO 클래스
```

## ERD
<img width="708" height="537" alt="ERD" src="https://github.com/user-attachments/assets/43f77928-da25-43b5-b990-0eaa57652e93" />

## 주요 기능

### 상품 (Product)
- 전체 상품 목록 조회
- 카테고리별 상품 조회
- 지역별 상품 조회
- 상품 상세 조회

### 장바구니 (Cart)
- 장바구니 상품 추가
- 장바구니 목록 조회
- 장바구니 상품 삭제

### 주문 / 결제
- 주문 생성
- 결제 처리

## API 명세서
API 명세 전체는 Swagger를 통해 확인할 수 있습니다.

https://goorm-trip-back.onrender.com/swagger-ui/index.html#

- `POST	/api/v1/orders/preview 주문 생성`
- `POST	/api/v1/orders/payment 결제 요청`
- `GET /api/v1/products 전체 상품조회`
- `GET /api/v1/products/{productId} 상품 상세 조회`
- `GET /api/v1/products/category/{category} 카테고리별 상품 조회`
- `GET /api/v1/products/region/{region} 지역별 상품 조회`
- `POST	/api/v1/carts 장바구니 상품 추가`
- `GET /api/v1/carts 장바구니 목록 조회`
- `DELETE	/api/v1/carts/{cartId} 장바구니 상품 삭제`
