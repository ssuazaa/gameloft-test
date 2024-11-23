package com.susocode.gamelofttest.user.inte.infrastructure.spring.input.rest.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.dto.UserResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.entity.UserEntity;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.repository.ReactiveMongoDBUserRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mongo")
@AutoConfigureWebTestClient
class RestUserControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ReactiveMongoDBUserRepository userRepository;

  @BeforeEach
  void setUp() {
    var mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    var typeReference = new TypeReference<List<UserEntity>>() {
    };
    var inputStream = getClass().getResourceAsStream("/db/mongodb/data-test.json");

    if (Objects.isNull(inputStream)) {
      throw new RuntimeException("Test data file not found: /db/mongodb/data-test.json");
    }

    try {
      var users = mapper.readValue(inputStream, typeReference);
      userRepository.deleteAll()
          .thenMany(Flux.fromIterable(users))
          .flatMap(userRepository::save)
          .blockLast();
    } catch (Exception e) {
      throw new RuntimeException("Error loading data: %s", e);
    }
  }

  @Test
  void findById_shouldReturnUserResponseDtoWithNonEmptyActiveCampaigns_whenUserFound() {
    // Arrange
    var playerId = UUID.fromString("97983be2-98b7-11e7-90cf-082e5f28d835");

    // Act & Assert
    webTestClient.get()
        .uri("/get_client_config/{playerId}", playerId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(UserResponseDto.class)
        .value(dto -> {
          assertThat(dto.playerId()).isEqualTo(playerId);
          assertThat(dto.activeCampaigns()).isNotEmpty();
        });
  }

  @Test
  void findById_shouldReturnUserResponseDtoWithEmptyActiveCampaigns_whenUserFound() {
    // Arrange
    var playerId = UUID.fromString("97983be2-98b7-11e7-90cf-082e5f28d834");

    // Act & Assert
    webTestClient.get()
        .uri("/get_client_config/{playerId}", playerId)
        .exchange()
        .expectStatus().isOk()
        .expectBody(UserResponseDto.class)
        .value(dto -> {
          assertThat(dto.playerId()).isEqualTo(playerId);
          assertThat(dto.activeCampaigns()).isEmpty();
        });
  }

  @Test
  void findById_shouldReturn404_whenUserNotFound() {
    // Arrange
    var nonExistentId = UUID.randomUUID();

    // Act & Assert
    webTestClient.get()
        .uri("/get_client_config/{playerId}", nonExistentId)
        .exchange()
        .expectStatus().isNotFound();
  }

}
