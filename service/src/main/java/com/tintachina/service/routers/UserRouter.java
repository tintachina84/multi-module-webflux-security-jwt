package com.tintachina.service.routers;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.tintachina.service.dtos.user.UserDto;
import com.tintachina.service.handlers.user.UserHandler;
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
@Component
public class UserRouter {

  @Bean
  @RouterOperation(operation = @Operation(operationId = "getUserByName", summary = "Get user information by name.", tags = { "User" },
      security = { @SecurityRequirement(name = "Bearer Authentication") },
      parameters = { @Parameter(in = ParameterIn.PATH, name = "name", description = "Input user's name.") },
      responses = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserDto.class))),
          @ApiResponse(responseCode = "400", description = "Invalid Name supplied"),
          @ApiResponse(responseCode = "404", description = "Name not found") }))
  public RouterFunction<?> routeUser(UserHandler userHandler) {
    return route()
        .GET("/user/{name}", userHandler::findUserByName)
        .GET("/users/{name}", userHandler::findUsersByNameLike)
        .build();
  }
}
