package com.lightning.support.protobuf.builtin.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.ListValue;
import com.google.protobuf.ListValue.Builder;
import com.lightning.support.protobuf.ProtobufDeserializer;
import java.io.IOException;
import java.util.List;

public class ListValueDeserializer extends ProtobufDeserializer<ListValue, Builder> {

  private static final FieldDescriptor VALUES_FIELD = ListValue.getDescriptor()
      .findFieldByName("values");

  public ListValueDeserializer() {
    super(ListValue.class);
  }

  @Override
  protected void populate(
      Builder builder,
      JsonParser parser,
      DeserializationContext context
  ) throws IOException {
    List<Object> values = readArray(builder, VALUES_FIELD, null, parser, context);
    for (Object value : values) {
      builder.addRepeatedField(VALUES_FIELD, value);
    }
  }
}
