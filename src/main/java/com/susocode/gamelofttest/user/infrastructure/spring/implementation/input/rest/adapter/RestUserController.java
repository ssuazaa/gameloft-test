package com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.adapter;

import com.susocode.gamelofttest.user.domain.port.input.usecase.FindUserUseCase;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.dto.UserResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.mapper.RestUserMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RestUserController {

  private final FindUserUseCase findUserUseCase;
  private final RestUserMapper mapper;

  @GetMapping("/get_client_config/{playerId}")
  public Mono<UserResponseDto> findById(@PathVariable UUID playerId) {
    return findUserUseCase.findById(playerId)
        .map(mapper::toResponseDto);
  }

}
