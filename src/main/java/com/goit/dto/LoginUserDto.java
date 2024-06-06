package com.goit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min = 4)
    public String password;
}
