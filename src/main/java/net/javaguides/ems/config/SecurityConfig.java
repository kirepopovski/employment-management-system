package net.javaguides.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // CORS Configuration (adjust the allowed origins for your frontend)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // Security Filter Chain Configuration (Spring Security 6.x)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Enable CORS
                .csrf(csrf -> csrf.disable())  // Disable CSRF (for stateless APIs or JWT)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/login", "/api/register", "/api/**", "/api/*", "/*", "/**")
                        .permitAll()  //open access
                        .anyRequest()  // Secure all other requests
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page URL
                        .permitAll()  // Allow everyone to access login page
                        .failureUrl("/login?error=BadCredentials")  // Redirect if login fails
                        .defaultSuccessUrl("/home", true)  // Redirect to home on successful login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Logout URL
                        .clearAuthentication(true)  // Clear authentication after logout
                        .invalidateHttpSession(true)  // Invalidate session on logout
                        .deleteCookies("JSESSIONID")  // Delete cookies after logout
                        .logoutSuccessUrl("/login")  // Redirect to login page after successful logout
                );

        return http.build();
    }

    // Authentication Manager to authenticate users
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);  // Set user details service for authentication
        return authenticationManagerBuilder.build();
    }

}
