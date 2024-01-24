## SpringBoot multi module project with WebFlux, Spring Security, JWT Authentication

### Specification
- Java 17
- SpringBoot 3.2.1
- Spring WebFlux (Reactive)
- Spring Security 6.2.1
- JWT Authentication
- PostgreSQL (R2DBC)

### Modules
- api (API details & Swagger documents)
- service (Business logic)
- global-utils (Common utils, etc...)

### Run PostgreSQL
```shell
docker-compose up -d
```

### Run web application
```shell
./gradlew bootRun
```

### OpenAPI UI
http://localhost:8080/openapi/swagger-ui.html
