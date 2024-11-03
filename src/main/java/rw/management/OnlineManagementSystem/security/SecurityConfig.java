package rw.management.OnlineManagementSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues())) // Enable CORS
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/signup",
                                "/api/users/login",
                                "/api/users/all",
                                "/auth/forgot-password",
                                "/auth/reset-password",
                                "/api/bookings/**",
                                "/api/users/delete/**",
                                "/api/courses/**", // Corrected path to include leading slash
                                "/auth/**",
                                "/code/**",
                                "/public/**"
                        ).permitAll() // Allow unauthenticated access to these paths
                        .anyRequest().authenticated() // All other requests require authentication
                );

        return http.build();
    }
}
