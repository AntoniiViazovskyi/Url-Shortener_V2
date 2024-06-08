package com.goit.auth;


import com.goit.exception.exceptions.generalExceptions.BadJWTException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String userLogin = null;
        String jwt = null;
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                userLogin = jwtUtils.getEmail(jwt);
            } catch (ExpiredJwtException | BadJWTException e ) {
                logger.warn("JWT Expired: {}", e);
            }

        }

        if (userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = null;
            try {
                token = new UsernamePasswordAuthenticationToken(
                                userLogin, null, jwtUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
                        );
            } catch (BadJWTException e) {
                logger.warn("Bad JWT: {}", e);
            }
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}
