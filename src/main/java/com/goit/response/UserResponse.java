package com.goit.response;

import com.goit.auth.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Data
public class UserResponse {

    private Long id;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
