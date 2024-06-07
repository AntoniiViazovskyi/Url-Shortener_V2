package com.goit.controller;

import com.goit.request.auth.JwtResponse;
import com.goit.request.auth.LoginRequest;
import com.goit.response.CustomErrorResponse;
import com.goit.response.UserResponse;
import com.goit.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "")
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class)) }),
        @ApiResponse(responseCode = "4XX", description = "Login failed",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    ));
        } catch (AuthenticationException e) {
            throw new Exception("Authentication Exception", e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/register")
    @Operation(summary = "Register user")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Registration successful",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
        @ApiResponse(responseCode = "4XX", description = "Registration failed",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws UserAlreadyExistException {
        userService.registerUser(signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getPassword());
        return ResponseEntity.status(201).build();
    }
}