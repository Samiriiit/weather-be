//package com.sapient.sde.weather_predictor.controllers;
//
//import com.sapient.sde.weather_predictor.config.SecurityConfig.JwtTokenUtil; // Import the nested class
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//public class UserController {
//
//    // Autowire the JWT Utility defined in your SecurityConfig
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    public static class LoginRequest {
//        public String username;
//        public String password;
//        // Public getters/setters are needed for Jackson/JSON binding
//        public String getUsername() { return username; }
//        public String getPassword() { return password; }
//        public void setUsername(String username) { this.username = username; }
//        public void setPassword(String password) { this.password = password; }
//    }
//
//    public static class AuthResponse {
//        public final String jwt;
//        public AuthResponse(String jwt) { this.jwt = jwt; }
//        public String getJwt() { return jwt; }
//    }
//
//    // -----------------------------------------------------------
//    // 2. PUBLIC ENDPOINT (Login)
//    // -----------------------------------------------------------
//
//    /**
//     * Endpoint for users to log in and receive a JWT.
//     * This path is permitted by: .requestMatchers("/api/auth/**").permitAll()
//     */
//    @PostMapping("/api/auth/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//        List<GrantedAuthority> authorities;
//        String token;
//
//        // Manual/Mock Credential Check (Replaces AuthenticationManager/PasswordEncoder)
//        if ("user".equals(username) && "password".equals(password)) {
//            authorities = List.of(new SimpleGrantedAuthority("USER"));
//        } else if ("admin".equals(username) && "adminpass".equals(password)) {
//            authorities = List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
//        }
//
//        // Generate Token
//        // NOTE: This relies on the JWT Utility having a method that takes username and authorities.
//        token = generateTokenWithClaims(username, authorities);
//
//        return ResponseEntity.ok(new AuthResponse(token));
//    }
//
//    // -----------------------------------------------------------
//    // 3. SECURED ENDPOINT
//    // -----------------------------------------------------------
//
//
//
//    private String generateTokenWithClaims(String username, List<GrantedAuthority> authorities) {
//        // this should be a method in JwtTokenUtil that handles mapping
//        // authorities to a 'roles' claim and signing the token.
//
//        String roles = authorities.stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        // This relies on the JWT Utility having a public method to generate the token
//        // using the signingKey, expiration, username, and claims.
//        // Since we can't define the method here, assume a utility exists.
//
//        return jwtTokenUtil.generateTokenForController(username, roles);
//    }
//}