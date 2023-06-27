package com.lightning.support;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeFormatUtil for handling specified time formats.
 */
public final class TimeFormatUtil {

  /**
   * Default zone is "Asia/Shanghai".
   */
  public static final ZoneId ZONE_ID_CST = ZoneId.of("Asia/Shanghai");

  /**
   * Default time format.
   */
  public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
  /**
   * GB_T_7408.
   */
  public static final String GB_T_7408 = "yyyyMMddHHmmss";
  public static final DateTimeFormatter GB_T_7408_FORMATTER = DateTimeFormatter.ofPattern(GB_T_7408);

  /**
   * Convert long time value to default format.
   *
   * @param millis given timestamp value.
   * @return default format timestamp of String.
   */
  public static String timeInCst(long millis) {
    return timeInCst(millis, DEFAULT_FORMATTER);
  }

  /**
   * Convert long time value to according to specified format.
   *
   * @param millis    given timestamp value.
   * @param formatter specified time formatter.
   * @return specified format timestamp of String.
   */
  public static String timeInCst(long millis, DateTimeFormatter formatter) {
    ZonedDateTime cst = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZONE_ID_CST);
    return formatter.format(cst);
  }

  /**
   * Convert datetime to LocalDateTime.
   *
   * @param datetime given datetime.
   * @return LocalDateTime.
   */
  public static LocalDateTime timeInCst(String datetime) {
    return timeInCst(datetime, DEFAULT_FORMATTER);
  }

  /**
   * Convert datetime to LocalDateTime according to specified formatter.
   *
   * @param datetime  given datetime.
   * @param formatter specified datetime formatter.
   * @return LocalDateTime.
   */
  public static LocalDateTime timeInCst(String datetime, DateTimeFormatter formatter) {
    ZonedDateTime cst = ZonedDateTime.parse(datetime, formatter.withZone(ZONE_ID_CST));
    return cst.toLocalDateTime();
  }
}
