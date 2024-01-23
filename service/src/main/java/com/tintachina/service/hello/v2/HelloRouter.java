package com.tintachina.service.hello.v2;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

/**
 * @author seonghyeon.seo
 */
@RequiredArgsConstructor
@Component("helloRouterV1")
public class HelloRouter {

  @Bean
  @RouterOperation(operation = @Operation(operationId = "sayHello", summary = "Get name and say hello.", tags = { "SayHello" },
      security = { @SecurityRequirement(name = "Bearer Authentication") },
      parameters = { @Parameter(in = ParameterIn.PATH, name = "name", description = "Say Hello") },
      responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class))),
          @ApiResponse(responseCode = "400", description = "Invalid Name supplied"),
          @ApiResponse(responseCode = "404", description = "Name not found") }))
  public RouterFunction<?> routeHello(HelloHandler helloHandler) {
    return route()
            .GET("/hello/v1/{name}", helloHandler::sayHello)
            .build();
  }
}
