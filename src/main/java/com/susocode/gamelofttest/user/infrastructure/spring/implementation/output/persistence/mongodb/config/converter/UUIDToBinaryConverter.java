package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UUIDToBinaryConverter implements Converter<UUID, Binary> {

  @Override
  public Binary convert(UUID source) {
    ByteBuffer buffer = ByteBuffer.allocate(16);
    buffer.putLong(source.getMostSignificantBits());
    buffer.putLong(source.getLeastSignificantBits());
    return new Binary((byte) 4, buffer.array());
  }

}
