package com.lightning.support.protobuf.builtin.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import com.lightning.support.protobuf.ProtobufSerializer;
import java.io.IOException;


public class ListValueSerializer extends ProtobufSerializer<ListValue> {

  private static final FieldDescriptor VALUES_FIELD = ListValue.getDescriptor()
      .findFieldByName("values");

  public ListValueSerializer() {
    super(ListValue.class);
  }

  @Override
  public void serialize(
      ListValue listValue,
      JsonGenerator generator,
      SerializerProvider serializerProvider
  ) throws IOException {
    generator.writeStartArray();
    for (Value value : listValue.getValuesList()) {
      writeValue(VALUES_FIELD, value, generator, serializerProvider);
    }
    generator.writeEndArray();
  }
}
