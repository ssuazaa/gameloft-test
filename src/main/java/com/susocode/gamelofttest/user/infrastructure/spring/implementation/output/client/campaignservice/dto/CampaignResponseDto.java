package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public record CampaignResponseDto(
    String game,
    String name,
    double priority,
    MatchersResponseDto matchers,
    @JsonProperty("start_date") ZonedDateTime startDate,
    @JsonProperty("end_date") ZonedDateTime endDate,
    boolean enable,
    @JsonProperty("last_update") ZonedDateTime lastUpdate) {

}
