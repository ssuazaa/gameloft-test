package com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface RestUserMapper {

  @Mapping(target = "playerId", source = "id")
  UserResponseDto toResponseDto(User user);

}
