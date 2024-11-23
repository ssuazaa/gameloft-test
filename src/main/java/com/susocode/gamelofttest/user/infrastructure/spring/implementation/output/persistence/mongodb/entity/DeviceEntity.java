package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.entity;

public record DeviceEntity(
    Integer id,
    String model,
    String carrier,
    String firmware
) {

}
