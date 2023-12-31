package com.lightning.support.protobuf.builtin.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.MessageOrBuilder;
import com.lightning.support.protobuf.ProtobufSerializer;
import java.io.IOException;

public class WrappedPrimitiveSerializer<T extends MessageOrBuilder> extends ProtobufSerializer<T> {

  public WrappedPrimitiveSerializer(Class<T> wrapperType) {
    super(wrapperType);
  }

  @Override
  public void serialize(
      MessageOrBuilder message,
      JsonGenerator generator,
      SerializerProvider serializerProvider
  ) throws IOException {
    FieldDescriptor field = message.getDescriptorForType().findFieldByName("value");
    Object value = message.getField(field);
    writeValue(field, value, generator, serializerProvider);
  }
}
