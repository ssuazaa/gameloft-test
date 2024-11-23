package com.susocode.gamelofttest.user.infrastructure.spring.implementation.input.rest.dto;

public record DeviceResponseDto(
    Integer id,
    String model,
    String carrier,
    String firmware) {

}
