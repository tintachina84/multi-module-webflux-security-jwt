package com.tintachina.service.routers;

import com.tintachina.service.handlers.auth.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Component
public class AuthRouter {

    @Bean
    public RouterFunction<?> signin(AuthHandler authHandler) {
        return route()
                .POST("/reactive-auth/signin", authHandler::signin3)
                .build();
    }
}
