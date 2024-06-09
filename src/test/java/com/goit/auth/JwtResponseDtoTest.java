package com.goit.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        String token = "exampleToken";

        JwtResponseDto jwtResponseDto = new JwtResponseDto(token);

        Assertions.assertEquals(token, jwtResponseDto.getToken());
    }

    @Test
    void testGetterSetter() {
        String token = "exampleToken";
        JwtResponseDto jwtResponseDto = new JwtResponseDto();

        jwtResponseDto.setToken(token);

        Assertions.assertEquals(token, jwtResponseDto.getToken());
    }
}