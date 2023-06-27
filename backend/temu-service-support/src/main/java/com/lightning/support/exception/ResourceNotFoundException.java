package com.lightning.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  static final long serialVersionUID = -1242599979055084673L;

  public ResourceNotFoundException(String message) {
    super("Resource Not Found: " + message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super("Resource Not Found: " + message, cause);
  }
}
