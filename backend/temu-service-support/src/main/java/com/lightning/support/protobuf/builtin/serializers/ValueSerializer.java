package com.lightning.support.protobuf.builtin.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Value;
import com.lightning.support.protobuf.ProtobufSerializer;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;


public class ValueSerializer extends ProtobufSerializer<Value> {

  public ValueSerializer() {
    super(Value.class);
  }

  @Override
  public void serialize(
      Value value,
      JsonGenerator generator,
      SerializerProvider serializerProvider
  ) throws IOException {
    Map<FieldDescriptor, Object> fields = value.getAllFields();
    if (fields.isEmpty()) {
      generator.writeNull();
    } else {
      // should only have 1 entry
      for (Entry<FieldDescriptor, Object> entry : fields.entrySet()) {
        if (value.getKindCase() == Value.KindCase.NUMBER_VALUE) {
          // must be Double
          Double v = (Double) entry.getValue();
          // If the double point number is 0, it is converted to an integer.
          if (v.longValue() - v == 0) {
            writeValue(entry.getKey(), v.longValue(), generator, serializerProvider, true);
          } else {
            writeValue(entry.getKey(), entry.getValue(), generator, serializerProvider);
          }
        } else {
          writeValue(entry.getKey(), entry.getValue(), generator, serializerProvider);
        }
      }
    }
  }
}
