package com.tintachina.service.hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tintachina.api.exceptions.InvalidInputException;
import com.tintachina.api.exceptions.NotFoundException;
import com.tintachina.api.hello.HelloApi;
import com.tintachina.utils.http.HttpErrorInfo;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * @author seonghyeon.seo
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloService implements HelloApi {

  private final ObjectMapper mapper;

  @Override
  public Mono<Map<String, String>> hello(String name) throws InvalidInputException {
    if (StringUtils.isEmpty(name)) {
      throw new NotFoundException("Name is empty");
    }

    if (containsNumber(name)) {
      throw new InvalidInputException("Name contains number");
    }

    Map<String, String> map = Map.of("name", "Hello, " +  name);
    return Mono.just(map);
  }

  private boolean containsNumber(String s) {
    return s.matches(".*\\d.*");
  }

  private Throwable handleException(Throwable ex) {

    if (!(ex instanceof WebClientResponseException)) {
      log.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
      return ex;
    }

    WebClientResponseException wcre = (WebClientResponseException)ex;

    switch (HttpStatus.resolve(wcre.getStatusCode().value())) {

      case NOT_FOUND:
        return new NotFoundException(getErrorMessage(wcre));

      case UNPROCESSABLE_ENTITY:
        return new InvalidInputException(getErrorMessage(wcre));

      default:
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
        log.warn("Error body: {}", wcre.getResponseBodyAsString());
        return ex;
    }
  }

  private String getErrorMessage(WebClientResponseException ex) {
    try {
      return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
    } catch (IOException ioex) {
      return ex.getMessage();
    }
  }
}
