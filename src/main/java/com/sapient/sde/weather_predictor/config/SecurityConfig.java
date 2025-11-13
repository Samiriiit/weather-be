//package com.sapient.sde.weather_predictor.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.security.Key;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Value("${app.cors.allowed-origins:http://localhost:50000}")
//    private String allowedOrigins;
//
//    @Value("${jwt.secret:A_Very_Long_And_Secure_Secret_Key_For_JWT_Signing_Must_Be_At_Least_256Bits}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration:18000000}")
//    private long expiration;
//
//
//    @Bean
//    public JwtTokenUtil jwtTokenUtil() {
//        return new JwtTokenUtil(jwtSecret, expiration);
//    }
//
//    // Static inner class
//    public static class JwtTokenUtil {
//        private final Key signingKey;
//        private final long expiration;
//
//        public JwtTokenUtil(String secret, long expiration) {
//            this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
//            this.expiration = expiration;
//        }
//
//        //  to generate the token after a successful login
//        public String generateTokenForController(String username, String rolesString) {
//            Map<String, Object> claims = new HashMap<>();
//            claims.put("roles", rolesString); // The roles claim required by your filter
//
//            return Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(username)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                    .signWith(signingKey, SignatureAlgorithm.HS256)
//                    .compact();
//        }
//
//       //  JwtTokenUtil methods
//
//        public String getUsernameFromToken(String token) {
//            return getClaimFromToken(token, Claims::getSubject);
//        }
//
//        // Example method to extract authorities/roles
//        public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
//            Claims claims = getAllClaimsFromToken(token);
//            String rolesString = claims.get("roles", String.class);
//            if (rolesString != null) {
//                return Arrays.stream(rolesString.split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//            }
//            return Collections.emptyList();
//        }
//
//        public Boolean validateToken(String token) {
//            try {
//                // checks the signature and expiration date
//                Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
//                return true;
//            } catch (Exception e) {
//                // invalid
//                return false;
//            }
//        }
//
//
//        private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//            final Claims claims = getAllClaimsFromToken(token);
//            return claimsResolver.apply(claims);
//        }
//
//        private Claims getAllClaimsFromToken(String token) {
//            return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
//        }
//    }
//
//
//    //  Gatekeeper Logic
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        // JWT utility bean we created
//        JwtTokenUtil tokenUtil = jwtTokenUtil();
//
//        http
//                // 1. CORS Configuration
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//
//                // 2. CSRF & Stateless Policy
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // 3. Authorization Rules
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                        .requestMatchers("/weather-prediction").authenticated()
//                        .requestMatchers("/actuator/**").permitAll() // RBAC Enforcement
//                        .anyRequest().authenticated()
//                )
//
//                // 4. JWT Filter Insertion
//                .addFilterBefore(new JwtAuthenticationFilter(tokenUtil),
//                        UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//
//    // --- STEP 3: The Custom Filter Class (The Gatekeeper Logic) ---
//
//    // Static inner class for the JWT authentication filter
//    private static class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//        private final JwtTokenUtil jwtTokenUtil;
//
//        public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
//            this.jwtTokenUtil = jwtTokenUtil;
//        }
//
//
//        @Override
//        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//                throws ServletException, IOException {
//
//            final String authHeader = request.getHeader("Authorization");
//            String jwt = null;
//            String username = null;
//
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                jwt = authHeader.substring(7);
//                try {
//                    username = jwtTokenUtil.getUsernameFromToken(jwt);
//                } catch (Exception e) {
//                    // Token invalid or expired
//                }
//            }
//
//            // If token is valid and user is not yet authenticated in the context
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                if (jwtTokenUtil.validateToken(jwt)) {
//                    // Extract authorities (roles)
//                    List<GrantedAuthority> authorities = jwtTokenUtil.getAuthoritiesFromToken(jwt);
//
//                    // Create Authentication object (This is the authorization step!)
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    username,
//                                    null,
//                                    authorities);
//
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    // --- STEP 4: CORS Configuration ---
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        List<String> origins = List.of(allowedOrigins.split(","));
//        config.setAllowedOrigins(origins);
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//}