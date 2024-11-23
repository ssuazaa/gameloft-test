package com.susocode.gamelofttest.user.domain.port.output.persistence;

import com.susocode.gamelofttest.user.domain.model.User;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface UserRepository {

  Mono<User> findById(UUID id);

  Mono<User> update(User user);

}
