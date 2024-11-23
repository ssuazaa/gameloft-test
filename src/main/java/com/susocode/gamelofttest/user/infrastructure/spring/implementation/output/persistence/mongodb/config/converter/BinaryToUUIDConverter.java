package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.persistence.mongodb.config.converter;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BinaryToUUIDConverter implements Converter<Binary, UUID> {

  @Override
  public UUID convert(Binary source) {
    ByteBuffer buffer = ByteBuffer.wrap(source.getData());
    long mostSigBits = buffer.getLong();
    long leastSigBits = buffer.getLong();
    return new UUID(mostSigBits, leastSigBits);
  }

}
