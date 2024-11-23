package com.susocode.gamelofttest.shared.infrastructure.spring.config.errorhandler;

import com.susocode.gamelofttest.shared.exception.BaseException;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(value = {BaseException.class})
  public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
    var error = new ErrorResponseDto(ex.getKey(), ex.getMessage(), LocalDateTime.now());
    return ResponseEntity
        .status(ex.getStatusCode())
        .body(error);
  }

}
