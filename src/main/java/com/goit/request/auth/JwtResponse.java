package com.goit.request.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<>();

    public JwtResponse () {
    }

    public JwtResponse (String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}