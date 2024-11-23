package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.entity.UserEntity;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.repository.ReactiveMongoDBUserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;

@Profile("!test")
@Configuration
@Slf4j
public class DataInitializer {

  @Bean
  CommandLineRunner initData(ReactiveMongoDBUserRepository userRepository) {
    return args -> {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());

      var typeReference = new TypeReference<List<UserEntity>>() {
      };
      var inputStream = getClass().getResourceAsStream("/db/mongodb/data.json");

      try {
        var users = mapper.readValue(inputStream, typeReference);
        Flux.fromIterable(users)
            .flatMap(userRepository::save)
            .subscribe(user -> log.info("Saved user: {}", user.id()));
      } catch (Exception e) {
        log.error("Error loading data: {}", e.getMessage());
      }
    };
  }
}
