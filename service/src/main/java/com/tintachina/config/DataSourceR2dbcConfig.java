package com.tintachina.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

/**
 * @author seonghyeon.seo
 */
@Configuration
public class DataSourceR2dbcConfig {
  @Bean
  ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    ResourceDatabasePopulator resource =
        new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
    initializer.setDatabasePopulator(resource);
    return initializer;
  }
}
