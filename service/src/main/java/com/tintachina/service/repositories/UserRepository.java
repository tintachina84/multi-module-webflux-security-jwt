package com.tintachina.service.repositories;

import com.tintachina.service.entities.user.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author seonghyeon.seo
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

  Mono<User> findUserByUsername(String username);
  Flux<User> findUsersByUsernameContainingIgnoreCase(String username);
  Mono<User> findUserByEmail(String email);
}
