package com.goit.response;

import com.goit.auth.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles = new HashSet<>();
}
