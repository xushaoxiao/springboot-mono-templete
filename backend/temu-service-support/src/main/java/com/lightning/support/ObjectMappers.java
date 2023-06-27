package com.lightning.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Maps;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import com.lightning.support.exception.DataFormatException;
import com.lightning.support.protobuf.ProtobufModule;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;


public final class ObjectMappers {

  private static final ObjectMapper DEFAULT_INSTANCE = new ObjectMapper();
  private static final ObjectMapper YAML_INSTANCE = new ObjectMapper(new YAMLFactory());
  private static final XmlMapper XML_INSTANCE = new XmlMapper();

  static {
    DEFAULT_INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    DEFAULT_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    DEFAULT_INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    DEFAULT_INSTANCE.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

    DEFAULT_INSTANCE.registerModule(new ProtobufModule());

    YAML_INSTANCE.findAndRegisterModules();
    YAML_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    YAML_INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    YAML_INSTANCE.registerModule(new ProtobufModule());

    XML_INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    XML_INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
  }

  private static final ConcurrentMap<Class<? extends MessageOrBuilder>, Method>
      PROTOBUF_DEFAULT_INSTANCE_METHOD_CACHE = Maps.newConcurrentMap();

  public ObjectMappers() {
  }

  public static ObjectMapper get() {
    return DEFAULT_INSTANCE;
  }

  public static ObjectMapper yaml() {
    return YAML_INSTANCE;
  }

  public static ObjectMapper xml() {
    return XML_INSTANCE;
  }

  /**
   * Deserialize <code>json</code> to an object of type <code>clazz</code>.
   *
   * @param json  json string.
   * @param clazz java class.
   * @param <T>   specified class type.
   * @throws DataFormatException throws if occurred exception.
   */
  public static <T> T mustReadValue(@Nullable String json, Class<T> clazz) {
    if (json == null) {
      return null;
    }
    try {
      return DEFAULT_INSTANCE.readValue(json, clazz);
    } catch (IOException e) {
      throw new DataFormatException(e);
    }
  }

  /**
   * Deserialize <code>json</code> to an object, using <code>typeRef</code>
   * to determine class of the object.
   *
   * @param json    json string.
   * @param typeRef java TypeReference.
   * @param <T>     specified class type.
   * @return null or TypeReference with T.
   * @throws DataFormatException throws if occurred exception.
   */
  public static <T> T mustReadValue(@Nullable String json, TypeReference<T> typeRef) {
    if (json == null) {
      return null;
    }
    try {
      return DEFAULT_INSTANCE.readValue(json, typeRef);
    } catch (IOException e) {
      throw new DataFormatException(e);
    }
  }

  /**
   * Deserialize <code>json</code> to an object of type <code>protobuf message</code>.
   *
   * @param json  json string.
   * @param clazz java Class.
   * @param <T>   specified class type.
   * @return null or T.
   * @throws DataFormatException throws if occurred exception.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Message> T mustReadProto(@Nullable String json, Class<T> clazz) {
    if (json == null) {
      return null;
    }
    try {
      Method method = PROTOBUF_DEFAULT_INSTANCE_METHOD_CACHE.get(clazz);
      if (method == null) {
        method = clazz.getMethod("getDefaultInstance");
        PROTOBUF_DEFAULT_INSTANCE_METHOD_CACHE.put(clazz, method);
      }
      Message.Builder b = ((T) method.invoke(null)).toBuilder();
      JsonFormat.parser().ignoringUnknownFields().merge(json, b);
      return (T) (b.build());
    } catch (IOException | SecurityException | ReflectiveOperationException e) {
      throw new DataFormatException(e);
    }
  }

  /**
   * Serialize <code>o</code> to a string.
   *
   * @param o Object instance.
   * @return String value or null.
   * @throws DataFormatException throws if occurred exception.
   */
  public static String mustWriteValue(@Nullable Object o) {
    if (o == null) {
      return null;
    }
    try {
      if (o instanceof MessageOrBuilder) {
        return JsonFormat.printer().omittingInsignificantWhitespace().print((MessageOrBuilder) o);
      } else {
        return DEFAULT_INSTANCE.writeValueAsString(o);
      }
    } catch (IOException e) {
      throw new DataFormatException(e);
    }
  }

  /**
   * Serialize <code>o</code> to a string.
   *
   * @param message Struct instance.
   * @return String value or null.
   * @throws DataFormatException throws if occurred exception.
   */
  public static String mustWriteStruct(@Nullable Struct message) {
    try {
      return DEFAULT_INSTANCE.writeValueAsString(message);
    } catch (IOException e) {
      throw new DataFormatException(e);
    }
  }

  /**
   * Serialize <code>o</code> to a pretty-string.
   *
   * @param o given Object.
   * @return String value or null.
   * @throws DataFormatException throws if occurred exception.
   */
  public static String mustWriteValuePretty(@Nullable Object o) {
    if (o == null) {
      return null;
    }
    try {
      if (o instanceof MessageOrBuilder) {
        return JsonFormat.printer().print((MessageOrBuilder) o);
      } else {
        return DEFAULT_INSTANCE.writerWithDefaultPrettyPrinter().writeValueAsString(o);
      }
    } catch (IOException e) {
      throw new DataFormatException(e);
    }
  }

  /**
   * Deserialize <code>json</code> to an {@link JsonNode} instance.
   *
   * @param json json String.
   * @return JsonNode or null.
   * @throws DataFormatException throws if occurred exception.
   */
  public static JsonNode mustReadTree(@Nullable String json) {
    if (json == null) {
      return null;
    }
    try {
      return DEFAULT_INSTANCE.readTree(json);
    } catch (IOException e) {
      throw new DataFormatException(e);
    }
  }
}
