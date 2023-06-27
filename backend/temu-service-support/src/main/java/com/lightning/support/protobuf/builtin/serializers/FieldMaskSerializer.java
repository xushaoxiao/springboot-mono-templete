package com.lightning.support.protobuf.builtin.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.protobuf.FieldMask;
import com.google.protobuf.util.FieldMaskUtil;
import com.lightning.support.protobuf.ProtobufSerializer;
import java.io.IOException;

public class FieldMaskSerializer extends ProtobufSerializer<FieldMask> {

  public FieldMaskSerializer() {
    super(FieldMask.class);
  }

  @Override
  public void serialize(
      FieldMask fieldMask,
      JsonGenerator generator,
      SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeString(FieldMaskUtil.toJsonString(fieldMask));
  }
}
