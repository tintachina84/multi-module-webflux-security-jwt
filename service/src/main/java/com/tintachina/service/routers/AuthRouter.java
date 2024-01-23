package com.tintachina.service.routers;

import com.tintachina.service.dtos.user.UserDto;
import com.tintachina.service.entities.auth.AuthenticationRequest;
import com.tintachina.service.entities.auth.SignInRequest;
import com.tintachina.service.handlers.auth.AuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Component
public class AuthRouter {

    @Bean
    @RouterOperation(operation = @Operation(operationId = "signin", summary = "Service SignIn.", tags = { "SignIn" },
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = SignInRequest.class),
                    examples = {
                        @ExampleObject(name = "signin", value = "{\"username\":\"test@domain.com\",\"password\":\"mypassword\",\"email\":\"test@domain.com\"}")
                    })),
            responses = { @ApiResponse(responseCode = "200", description = "SignIn success.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "User already exists."),
                    @ApiResponse(responseCode = "404", description = "Name not found") }))
    public RouterFunction<?> signin(AuthHandler authHandler) {
        return route()
                .POST("/reactive-auth/signin", authHandler::signin)
                .build();
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "login", summary = "Service LogIn.", tags = { "LogIn" },
            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AuthenticationRequest.class),
                    examples = {
                            @ExampleObject(name = "signin", value = "{\"username\":\"test@domain.com\",\"password\":\"mypassword\"}")
                    })),
            responses = { @ApiResponse(responseCode = "200", description = "SignIn success.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Passwod wrong."),
                    @ApiResponse(responseCode = "404", description = "User not found.") }))
    public RouterFunction<?> login(AuthHandler authHandler) {
        return route()
                .POST("/reactive-auth/login", authHandler::login)
                .build();
    }
}
