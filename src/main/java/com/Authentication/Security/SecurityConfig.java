    // Security configuration class for Spring Boot
package com.Authentication.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a configuration class
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    // Defines the security filter chain bean
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        // Configures form-based login
        .formLogin(httpForm -> {
             httpForm
                .loginPage("/login").permitAll(); // Custom login page, accessible to everyone
            })
                // Configures authorization rules
                .authorizeHttpRequests(registry ->{
                    registry.requestMatchers("/req/signup","css/**", ".js/**").permitAll(); // Signup page accessible to everyone
                    registry.anyRequest().authenticated(); // All other requests require authentication
                })
                .build(); // Builds the security filter chain
    }
}