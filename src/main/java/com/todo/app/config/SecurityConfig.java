package com.todo.app.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.todo.app.exceptions.UserNotConfirmedExeption;
import com.todo.app.model.service.UserDetailsServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/users/register").permitAll();
                request.requestMatchers("/users/login").permitAll();
                request.requestMatchers("/users/confirm").permitAll();
                request.requestMatchers("/users/confirmEmail").permitAll();
                request.requestMatchers("/output.css").permitAll();
                request.requestMatchers("/fonts/**").permitAll();
                request.requestMatchers("/public/**").permitAll();
                request.anyRequest().authenticated();
            })
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(Customizer.withDefaults())
            .formLogin(form -> {
                form.loginPage("/users/login")
                .loginProcessingUrl("/users/login")
                .defaultSuccessUrl("/tasks")
                .failureHandler(customAuthenticationFailureHandler())
                .permitAll();
            })
            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());

        return httpSecurity.build();
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                if (exception instanceof InternalAuthenticationServiceException &&
                    exception.getCause() instanceof UserNotConfirmedExeption) {
                    response.sendRedirect("/users/login?accountNotConfirmed");
                } else {
                    response.sendRedirect("/users/login?error");
                }
            }
        };
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
