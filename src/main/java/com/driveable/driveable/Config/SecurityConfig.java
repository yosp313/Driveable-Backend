package com.driveable.driveable.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.driveable.driveable.Filters.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthFilter jwtAuthenticationFilter;

  public SecurityConfig(
      JwtAuthFilter jwtAuthenticationFilter,
      AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(c -> {
          c.disable();
        })
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("api/v1/admin-dashboard/**").hasRole("ADMIN")
            .anyRequest().fullyAuthenticated() // Secure all other endpoints
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session policy
        )
        .authenticationProvider(authenticationProvider) // Custom authentication provider
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before
                                                                                               // username/password auth

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of("http://localhost:8005", "http://localhost:8081"));
    configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
    configuration.setAllowedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
