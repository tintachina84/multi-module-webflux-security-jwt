package com.tintachina.service.handlers.auth;

import com.tintachina.api.exceptions.NotFoundException;
import com.tintachina.service.entities.auth.AuthenticationRequest;
import com.tintachina.service.entities.auth.SignInRequest;
import com.tintachina.service.entities.user.User;
import com.tintachina.service.infra.security.jwt.JwtTokenProvider;
import com.tintachina.service.mapper.UserMapper;
import com.tintachina.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthHandler {

    private final @NonNull UserRepository userRepository;
    private final @NonNull UserMapper userMapper;
    private final @NonNull JwtTokenProvider tokenProvider;
    private final @NonNull ReactiveAuthenticationManager authenticationManager;
    private final @NonNull PasswordEncoder passwordEncoder;

    public Mono<ServerResponse> signin(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SignInRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body is empty.")))
                .flatMap(signInRequest -> this.userRepository.findUserByUsername(signInRequest.getUsername())
                        .flatMap(user -> Mono.error(new IllegalArgumentException("User already exists.")))
                        .switchIfEmpty(this.userRepository.save(User.builder()
                                .username(signInRequest.getUsername())
                                .email(signInRequest.getUsername())
                                .password(this.passwordEncoder.encode(signInRequest.getPassword()))
                                .roles(List.of("USER"))
                                .active(true)
                                .build()))
                )
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(AuthenticationRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body is empty.")))
                .flatMap(request -> this.userRepository.findUserByUsername(request.username())
                        .switchIfEmpty(Mono.error(new NotFoundException("User doesn't exists.")))
                        .flatMap(user -> this.authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(request.username(), request.password()))
                                .map(this.tokenProvider::createToken))
                ).flatMap(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    var tokenBody = Map.of("access_token", jwt);
                    return ServerResponse.ok().headers(headers -> headers.addAll(httpHeaders)).bodyValue(tokenBody);
                });
    }
}
