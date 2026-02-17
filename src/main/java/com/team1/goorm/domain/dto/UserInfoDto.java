package com.team1.goorm.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInfoDto {
    @NotNull
    private String name;
    @NotNull
    private String email;
}