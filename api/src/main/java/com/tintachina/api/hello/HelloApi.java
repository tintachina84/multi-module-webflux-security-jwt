package com.tintachina.api.hello;

import com.tintachina.api.exceptions.InvalidInputException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * @author seonghyeon.seo
 */
@Tag(name = "HelloApi", description = "REST API for test.")
@RequestMapping("/hello")
public interface HelloApi {

  @Operation(
      summary = "${api.hello.get-hello.description}",
      description = "${api.hello.get-hello.notes}")
  @SecurityRequirement(name = "Bearer Authentication")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
      @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
      @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
      @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
  })
  @GetMapping(
      value = "/{name}",
      produces = "application/json")
  <T> Mono<T> hello(@PathVariable String name) throws InvalidInputException;
}
