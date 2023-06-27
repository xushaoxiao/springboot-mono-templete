package com.lightning.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * DataFormatException An runtime exception defined for the data format.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataFormatException extends RuntimeException {
  private static final long serialVersionUID = -1L;

  public DataFormatException(String message) {
    super(message);
  }

  public DataFormatException(Throwable throwable) {
    super(throwable);
  }

  public DataFormatException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
