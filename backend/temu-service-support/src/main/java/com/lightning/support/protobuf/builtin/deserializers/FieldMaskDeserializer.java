package com.lightning.support.protobuf.builtin.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.protobuf.FieldMask;
import com.google.protobuf.util.FieldMaskUtil;
import java.io.IOException;


public class FieldMaskDeserializer extends StdDeserializer<FieldMask> {

  public FieldMaskDeserializer() {
    super(FieldMask.class);
  }

  @Override
  public FieldMask deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    if (parser.getCurrentToken() == JsonToken.VALUE_STRING) {
      return FieldMaskUtil.fromJsonString(parser.getText());
    }
    context.reportWrongTokenException(FieldMask.class, JsonToken.VALUE_STRING,
        wrongTokenMessage(context));
    // the previous method should have thrown
    throw new AssertionError();
  }

  // TODO share this?
  private static String wrongTokenMessage(DeserializationContext context) {
    return "Can not deserialize instance of com.google.protobuf.FieldMask out of "
        + context.getParser().currentToken() + " token";
  }
}
