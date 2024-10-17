package rw.transport.vubaride.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsFilter corsFilter;

    public SecurityConfig(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable()) // Enable CORS using the injected CorsFilter or you can use cors
                                              // configuration from HttpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) // Add the CorsFilter before
                                                                                         // authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/api/users/login","/api/users/all","/auth/forgot-password", "/auth/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated() // All other requests require authentication
                );

        return http.build(); // Build the SecurityFilterChain
    }
}
