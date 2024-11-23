package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.repository;

import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveMongoDBUserRepository extends ReactiveMongoRepository<UserEntity, UUID> {

}
