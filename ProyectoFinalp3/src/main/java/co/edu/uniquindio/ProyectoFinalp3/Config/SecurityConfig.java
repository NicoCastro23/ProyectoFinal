package co.edu.uniquindio.ProyectoFinalp3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Importaciones de seguridad
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}