package com.tintachina.service.handlers.user;

import com.tintachina.api.exceptions.UserNotFoundException;
import com.tintachina.service.entities.user.User;
import com.tintachina.service.mapper.UserMapper;
import com.tintachina.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author seonghyeon.seo
 */
@RequiredArgsConstructor
@Component
public class UserHandler {

  private final @NonNull UserRepository userRepository;
  private final @NonNull UserMapper userMapper;

  public Mono<ServerResponse> findUserByName(ServerRequest request) {
    return this.findVerifiedUserByName(request.pathVariable("name"))
        .map(userMapper::entityToDto)
        .flatMap(userDto -> ServerResponse.ok().bodyValue(userDto))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> findUsersByNameLike(ServerRequest request) {
    return this.userRepository.findUsersByUsernameContainingIgnoreCase(request.pathVariable("name"))
        .map(userMapper::entityToDto)
        .collectList()
        .flatMap(userDtos -> ServerResponse.ok().bodyValue(userDtos))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  private Mono<User> findVerifiedUserByName(String name) {
    return userRepository.findUserByUsername(name)
        .switchIfEmpty(Mono.error(new UserNotFoundException(name)));
  }
}
