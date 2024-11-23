package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config;

import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter.BinaryToUUIDConverter;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter.StringToZonedDateTimeConverter;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter.UUIDToBinaryConverter;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter.ZonedDateTimeToStringConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoDBConfig {

  @Bean
  public MongoCustomConversions customConversions() {
    List<Object> converters = new ArrayList<>();
    converters.add(new BinaryToUUIDConverter());
    converters.add(new UUIDToBinaryConverter());
    converters.add(new ZonedDateTimeToStringConverter());
    converters.add(new StringToZonedDateTimeConverter());
    return new MongoCustomConversions(converters);
  }

}
