package com.susocode.gamelofttest.shared.infrastructure.spring.config.errorhandler;

import java.time.LocalDateTime;

public record ErrorResponseDto(
    String key,
    String message,
    LocalDateTime dateTime) {

}
