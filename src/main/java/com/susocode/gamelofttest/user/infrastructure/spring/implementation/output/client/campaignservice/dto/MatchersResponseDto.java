package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto;

public record MatchersResponseDto(
    LevelMatcherResponseDto level,
    HasMatcherResponseDto has,
    DoesNotHaveMatcherResponseDto doesNotHave
) {

}
