package com.amex.order.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceConfiguration {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

    return jacksonObjectMapperBuilder ->
        jacksonObjectMapperBuilder
            .modules(new Jdk8Module(), new GuavaModule(), new JavaTimeModule())
            .failOnUnknownProperties(false)
            .serializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
