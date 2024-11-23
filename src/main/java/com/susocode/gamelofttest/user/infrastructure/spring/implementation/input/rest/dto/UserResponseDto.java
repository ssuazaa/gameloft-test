package com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@JsonPropertyOrder
public record UserResponseDto(
    @JsonProperty("player_id") UUID playerId,
    String credential,
    ZonedDateTime created,
    ZonedDateTime modified,
    @JsonProperty("last_session") ZonedDateTime lastSession,
    @JsonProperty("total_spend") int totalSpent,
    @JsonProperty("total_refund") int totalRefund,
    @JsonProperty("total_transactions") int totalTransactions,
    @JsonProperty("last_purchase") ZonedDateTime lastPurchase,
    @JsonProperty("active_campaigns") Set<String> activeCampaigns,
    List<DeviceResponseDto> devices,
    int level,
    int xp,
    @JsonProperty("total_playtime") int totalPlaytime,
    String country,
    String language,
    ZonedDateTime birthdate,
    String gender,
    Map<String, Integer> inventory,
    ClanResponseDto clan,
    @JsonProperty("_customfield") String customField
) {

}
