package com.tintachina.service.infra.security;

import com.tintachina.service.infra.security.jwt.JwtTokenAuthenticationFilter;
import com.tintachina.service.infra.security.jwt.JwtTokenProvider;
import com.tintachina.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PERMITTED_PATTERNS = {
            "/openapi/**",
            "/reactive-auth/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                  JwtTokenProvider tokenProvider,
                                                  ReactiveAuthenticationManager reactiveAuthenticationManager) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .cors(Customizer.withDefaults())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(PERMITTED_PATTERNS).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository users) {
        return username -> users.findUserByUsername(username)
                .map(u -> User
                        .withUsername(u.getUsername())
                        .password(u.getPassword())
                        .authorities(u.getRoles().toArray(new String[0]))
                        .accountExpired(!u.isActive())
                        .credentialsExpired(!u.isActive())
                        .disabled(!u.isActive())
                        .accountLocked(!u.isActive())
                        .build());
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }
}
