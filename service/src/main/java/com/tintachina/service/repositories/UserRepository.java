package com.tintachina.service.repositories;

import com.tintachina.service.entities.user.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author seonghyeon.seo
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

  Mono<User> findByName(String name);
  Flux<User> findUsersByNameContainingIgnoreCase(String name);
  Mono<User> findByEmail(String email);
}
