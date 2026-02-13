package com.team1.goorm.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Region {
    SEOUL("1", "서울"),
    INCHEON("2", "인천"),
    DAEJEON("3", "대전"),
    DAEGU("4", "대구"),
    GWANGJU("5", "광주"),
    BUSAN("6", "부산"),
    ULSAN("7", "울산"),
    SEJONG("8", "세종"),
    GYEONGGI("31", "경기"),
    GANGWON("32", "강원"),
    CHUNGBUK("33", "충북"),
    CHUNGNAM("34", "충남"),
    JEONBUK("35", "전북"),
    JEONNAM("36", "전남"),
    GYEONGBUK("37", "경북"),
    GYEONGNAM("38", "경남"),
    JEJU("39", "제주");

    private final String code;
    private final String description;

    // 코드로 Enum을 찾는 편의 메서드
    public static Region getByCode(String code) {
        return Arrays.stream(Region.values())
                .filter(r -> r.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 코드가 없습니다: " + code));
    }
}
