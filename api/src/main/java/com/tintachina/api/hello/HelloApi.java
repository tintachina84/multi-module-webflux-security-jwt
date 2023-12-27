package com.tintachina.api.hello;

import com.tintachina.api.exceptions.InvalidInputException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/hello")
public interface HelloApi {

  @GetMapping(
      value = "/{name}",
      produces = "application/json")
  <T> Mono<T> hello(@PathVariable String name) throws InvalidInputException;
}
