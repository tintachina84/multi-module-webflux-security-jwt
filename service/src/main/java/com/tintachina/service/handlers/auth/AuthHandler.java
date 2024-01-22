package com.tintachina.service.handlers.auth;

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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
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

    public Mono<ServerResponse> signin(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SignInRequest.class)
                .flatMap(request -> userRepository.findByEmail(request.getEmail())
                        .flatMap(user -> ServerResponse.badRequest().bodyValue("User already exists."))
                        .switchIfEmpty(Mono.defer(() -> userRepository.save(User.builder()
                                        .name(request.getName())
                                        .email(request.getEmail())
                                        .password(request.getPassword())
                                        .roles(List.of("USER"))
                                        .build()))
                                .map(user -> this.userMapper.entityToDto(user))
                                .flatMap(user -> this.authenticationManager
                                        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()))
                                        .map(this.tokenProvider::createToken))
                                .flatMap(jwt -> {
                                    var tokenBody = Map.of("access_token", jwt);
                                    return ServerResponse.ok().headers(httpHeaders ->
                                            httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                                    ).bodyValue(tokenBody);
                                })
                        )
        );
    }

    public Mono<ServerResponse> signin2(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SignInRequest.class)
                .flatMap(request -> userRepository.findByEmail(request.getEmail())
                        .flatMap(user -> ServerResponse.badRequest().bodyValue("User already exists."))
                        .switchIfEmpty(
                                Mono.defer(() -> userRepository.save(User.builder()
                                                .name(request.getName())
                                                .email(request.getEmail())
                                                .password(request.getPassword())
                                                .build()))
                                        .flatMap(user -> ServerResponse.ok().bodyValue(user))
                        ));
    }

    public Mono<ServerResponse> signin3(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SignInRequest.class)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body is empty.")))
                .flatMap(signInRequest -> this.userRepository.findByEmail(signInRequest.getEmail())
                        .switchIfEmpty(this.userRepository.save(User.builder()
                                .name(signInRequest.getName())
                                .email(signInRequest.getEmail())
                                .password(signInRequest.getPassword())
                                .roles(List.of("USER"))
                                .build()))
                        .flatMap(user -> ServerResponse.ok().bodyValue(user))
                );
    }
}
