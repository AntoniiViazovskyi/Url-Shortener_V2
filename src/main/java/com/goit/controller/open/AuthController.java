package com.goit.controller.open;


import com.goit.dto.CreateUserDto;
import com.goit.dto.JwtResponseDto;
import com.goit.dto.LoginUserDto;
import com.goit.service.UserService;
import com.goit.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/open/auth")
@Validated
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Test");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid LoginUserDto loginUserDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
        );
        String jwt = jwtUtils.generateToken(userService.findByEmail(loginUserDto.getEmail()));
        return ResponseEntity.ok(new JwtResponseDto(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        String jwt = jwtUtils.generateToken(userService.findByEmail(createUserDto.getEmail()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponseDto(jwt));
    }
}
