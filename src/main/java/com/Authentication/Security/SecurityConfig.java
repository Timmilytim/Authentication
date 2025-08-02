    // Security configuration class for Spring Boot
package com.Authentication.Security;

import com.Authentication.Service.MyAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a configuration class
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {

    @Autowired
    private MyAppUserService appUserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserService; // Returns the custom user details service
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder for the provider
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Defines the security filter chain bean
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection for simplicity
            // Configures form-based login
            .formLogin(httpForm -> {
                 httpForm.loginPage("/req/login").permitAll(); // Custom login page, accessible to everyone
                httpForm.defaultSuccessUrl("/index", true); // Redirects to home page after successful login
                })
                    // Configures authorization rules
                    .authorizeHttpRequests(registry ->{
                        registry.requestMatchers("/req/**", "/css/**", "/js/**").permitAll(); // Signup page
                        // accessible to everyone
                        registry.anyRequest().authenticated(); // All other requests require authentication
                    })
                    .build(); // Builds the security filter chain
    }
}