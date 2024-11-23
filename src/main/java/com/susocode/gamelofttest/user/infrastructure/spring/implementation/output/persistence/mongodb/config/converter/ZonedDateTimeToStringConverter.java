package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

  @Override
  public String convert(ZonedDateTime source) {
    return source.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

}
