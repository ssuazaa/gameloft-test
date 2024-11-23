package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.adapter;

import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.domain.port.output.persistence.UserRepository;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.mapper.MongoDBUserMapper;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.repository.ReactiveMongoDBUserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final MongoDBUserMapper mapper;
  private final ReactiveMongoDBUserRepository repository;

  @Override
  public Mono<User> findById(UUID id) {
    return repository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public Mono<User> update(User user) {
    return Mono.just(mapper.toEntity(user))
        .flatMap(repository::save)
        .map(mapper::toDomain);
  }

}
