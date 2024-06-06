package com.goit.auth;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
    }
}
