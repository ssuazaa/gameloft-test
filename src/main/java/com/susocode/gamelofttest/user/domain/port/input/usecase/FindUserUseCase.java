package com.susocode.gamelofttest.user.domain.port.input.usecase;

import com.susocode.gamelofttest.user.domain.model.User;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface FindUserUseCase {

  Mono<User> findById(UUID id);

}
