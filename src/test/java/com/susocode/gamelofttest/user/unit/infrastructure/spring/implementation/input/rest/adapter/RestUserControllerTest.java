package com.susocode.gamelofttest.user.unit.infrastructure.spring.implementation.input.rest.adapter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.susocode.gamelofttest.shared.exception.ObjectNotFoundException;
import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.domain.port.input.usecase.FindUserUseCase;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.adapter.RestUserController;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.dto.UserResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.mapper.RestUserMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class RestUserControllerTest {

  @Mock
  private FindUserUseCase findUserUseCase;

  @Mock
  private RestUserMapper mapper;

  @InjectMocks
  private RestUserController restUserController;

  @Test
  void findById_shouldReturnUserResponseDto_whenUserFound() {
    // Arrange
    var playerId = UUID.randomUUID();
    var user = new User();
    user.setId(playerId);

    var userResponseDto = getMockUserResponseDto(user);

    when(findUserUseCase.findById(playerId)).thenReturn(Mono.just(user));
    when(mapper.toResponseDto(user)).thenReturn(userResponseDto);

    // Act
    var response = restUserController.findById(playerId);

    // Assert
    StepVerifier.create(response)
        .expectNextMatches(dto -> dto.playerId().equals(playerId))
        .verifyComplete();

    verify(findUserUseCase, times(1)).findById(playerId);
    verify(mapper, times(1)).toResponseDto(user);
    verifyNoMoreInteractions(findUserUseCase, mapper);
  }

  @Test
  void findById_shouldReturnObjectNotFound_whenUserNotFound() {
    // Arrange
    var playerId = UUID.randomUUID();

    when(findUserUseCase.findById(playerId)).thenReturn(Mono.error(
        new ObjectNotFoundException("USER_PROFILE_NOT_FOUND",
            String.format("User profile with profile_id '%s' not exists.", playerId))));

    // Act
    var response = restUserController.findById(playerId);

    // Assert
    StepVerifier.create(response)
        .verifyErrorMatches(error -> error.getClass().equals(ObjectNotFoundException.class)
            && ((ObjectNotFoundException) error).getKey().equals("USER_PROFILE_NOT_FOUND"));

    verify(findUserUseCase, times(1)).findById(playerId);
    verifyNoMoreInteractions(findUserUseCase);
    verifyNoInteractions(mapper);
  }

  private UserResponseDto getMockUserResponseDto(User user) {
    return new UserResponseDto(user.getId(),
        null,
        null,
        null,
        null,
        0,
        0,
        0,
        null,
        null,
        null,
        0,
        0,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        null);
  }

}
