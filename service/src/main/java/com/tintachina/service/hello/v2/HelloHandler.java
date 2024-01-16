package com.tintachina.service.hello.v2;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author seonghyeon.seo
 */
@Component("helloHandlerV1")
public class HelloHandler {

  public Mono<ServerResponse> sayHello(ServerRequest request) {
    return Mono.just(request.pathVariable("name"))
            .flatMap(name -> ServerResponse.ok().bodyValue("Hello, routed " + name));
  }
}
