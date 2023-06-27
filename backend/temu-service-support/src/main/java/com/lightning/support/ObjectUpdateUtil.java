package com.lightning.support;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableBoolean;


public final class ObjectUpdateUtil {

  /**
   * For Object value.
   *
   * @param original T value.
   * @param value    T value.
   * @param <T>      T class.
   * @param updated  MutableBoolean.
   * @return expected value.
   */
  public static <T> T updateField(T original, T value, MutableBoolean updated) {
    if (value != null && !value.equals(original)) {
      updated.setValue(true);
      return value;
    } else {
      return original;
    }
  }

  /**
   * For long value.
   *
   * @param original long value.
   * @param value    long value.
   * @param updated  MutableBoolean.
   * @return expected value.
   */
  public static long updateField(long original, long value, MutableBoolean updated) {
    if (value != original && value != 0) {
      updated.setValue(true);
      return value;
    } else {
      return original;
    }
  }

  /**
   * for long specified zeroValue.
   *
   * @param original  original long value.
   * @param value     update long value.
   * @param zeroValue The special value to signal that the intention of the update
   *                  is to change value of the field to 0.  E.g.,
   *                  if <code>original</code> is 1, <code>value</code> is -1,
   *                  <code>zeroValue</code> is -1, then this method would
   *                  return 0, and set <code>updated</code> to true.
   * @param updated   update determine.
   * @return expected value.
   */
  public static long updateField(
      long original, long value, long zeroValue, MutableBoolean updated) {
    if (value == zeroValue) {
      if (original != 0) {
        updated.setValue(true);
      }
      return 0;
    }
    return updateField(original, value, updated);
  }

  /**
   * For int value.
   *
   * @param original int value.
   * @param value    int value.
   * @param updated  MutableBoolean.
   * @return expected value.
   */
  public static int updateField(int original, int value, MutableBoolean updated) {
    if (value != original && value != 0) {
      updated.setValue(true);
      return value;
    } else {
      return original;
    }
  }

  /**
   * for int specified zeroValue.
   *
   * @param original  int value.
   * @param value     int value.
   * @param updated   MutableBoolean.
   * @param zeroValue The special value to signal that the intention of the update
   *                  is to change value of the field to 0.  E.g.,
   *                  if <code>original</code> is 1, <code>value</code> is -1,
   *                  <code>zeroValue</code> is -1, then this method would
   *                  return 0, and set <code>updated</code> to true.
   * @return expected value.
   */
  public static int updateField(
      int original, int value, int zeroValue, MutableBoolean updated) {
    if (value == zeroValue) {
      if (original != 0) {
        updated.setValue(true);
      }
      return 0;
    }
    return updateField(original, value, updated);
  }

  /**
   * For float value.
   *
   * @param original float value.
   * @param value    float value.
   * @param updated  MutableBoolean.
   * @return expected value.
   */
  public static float updateField(
      float original, float value, float zeroValue, MutableBoolean updated) {
    if (value == zeroValue) {
      if (original != 0) {
        updated.setValue(true);
      }
      return 0;
    }
    final float epsilon = (float) 1.0e-4;
    if (Math.abs(original - value) < epsilon || value == 0) {
      return original;
    }
    updated.setTrue();
    return value;
  }

  /**
   * For List value.
   *
   * @param original List value.
   * @param value    List value.
   * @param updated  MutableBoolean.
   * @return expected value.
   */
  public static <T> List<T> updateField(
      List<T> original, List<T> value, MutableBoolean updated) {
    if (value != null) {
      updated.setValue(true);
      return new ArrayList<>(value);
    } else {
      return original;
    }
  }
}
