package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

  @Override
  public ZonedDateTime convert(String source) {
    return ZonedDateTime.parse(source, DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

}
