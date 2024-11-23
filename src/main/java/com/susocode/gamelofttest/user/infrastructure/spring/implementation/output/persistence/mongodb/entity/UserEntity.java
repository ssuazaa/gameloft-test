package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public record UserEntity(
    @Id UUID id,
    String credential,
    ZonedDateTime created,
    ZonedDateTime modified,
    @Field("last_session") ZonedDateTime lastSession,
    @Field("total_spent") Integer totalSpent,
    @Field("total_refund") Integer totalRefund,
    @Field("total_transactions") Integer totalTransactions,
    @Field("last_purchase") ZonedDateTime lastPurchase,
    @Field("active_campaigns") Set<String> activeCampaigns,
    List<DeviceEntity> devices,
    Integer level,
    Integer xp,
    @Field("total_playtime") Integer totalPlaytime,
    String country,
    String language,
    ZonedDateTime birthdate,
    String gender,
    Map<String, Integer> inventory,
    ClanEntity clan,
    @Field("_customfield") String customField
) {

}
