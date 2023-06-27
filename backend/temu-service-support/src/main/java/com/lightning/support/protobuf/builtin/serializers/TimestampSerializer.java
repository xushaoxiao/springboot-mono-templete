package com.lightning.support.protobuf.builtin.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import com.lightning.support.protobuf.ProtobufSerializer;
import java.io.IOException;

public class TimestampSerializer extends ProtobufSerializer<Timestamp> {

  public TimestampSerializer() {
    super(Timestamp.class);
  }

  @Override
  public void serialize(
      Timestamp timestamp,
      JsonGenerator generator,
      SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeString(Timestamps.toString(timestamp));
  }
}
