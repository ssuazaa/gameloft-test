package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface MongoDBUserMapper {

  User toDomain(UserEntity userEntity);

  UserEntity toEntity(User user);

}
