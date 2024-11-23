package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto.CampaignResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface ClientCampaignMapper {

  Campaign toDomain(CampaignResponseDto campaignResponseDto);

}
