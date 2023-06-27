package com.lightning.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  public static class GlobalResponse {
    public final int code;
    public final String message;
    public final String status;
    public final boolean ok;

    private GlobalResponse() {
      this(new Builder());
    }

    private GlobalResponse(Builder builder) {

      this.code = builder.getCode();
      this.message = builder.getMessage();
      this.status = builder.getStatus();
      this.ok = builder.isOk();
    }

    public static Builder newBuilder() {
      return new Builder();
    }

    public static class Builder {
      private int code;
      private String message;
      private String status;
      private boolean ok;

      public GlobalResponse build() {
        return new GlobalResponse(this);
      }

      public int getCode() {
        return code;
      }

      public Builder setCode(int code) {
        this.code = code;
        return this;
      }

      public String getMessage() {
        return message;
      }

      public Builder setMessage(String message) {
        this.message = message;
        return this;
      }

      public String getStatus() {
        return status;
      }

      public Builder setStatus(String status) {
        this.status = status;
        return this;
      }

      public boolean isOk() {
        return ok;
      }

      public Builder setOk(boolean ok) {
        this.ok = ok;
        return this;
      }
    }
  }

  /**
   * Handle BadRequestException.
   *
   * @param e BadRequestException.
   * @return GlobalResponse.
   */
  @ExceptionHandler(value = BadRequestException.class)
  @ResponseBody
  public ResponseEntity<GlobalResponse> badRequestExceptionHandler(BadRequestException e) {
    log.error("BadRequestException", e);
    return ResponseEntity.badRequest().body(GlobalResponse.newBuilder()
        .setOk(false)
        .setCode(400)
        .setStatus(HttpStatus.BAD_REQUEST.name())
        .setMessage(e.getMessage())
        .build());
  }

  /**
   * Handle DataFormatException.
   *
   * @param e DataFormatException.
   * @return GlobalResponse.
   */
  @ExceptionHandler(value = DataFormatException.class)
  @ResponseBody
  public ResponseEntity<GlobalResponse> dataFormatExceptionHandler(DataFormatException e) {
    log.error("DataFormatException", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(GlobalResponse.newBuilder()
            .setOk(false)
            .setCode(500)
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .setMessage(e.getMessage())
            .build());
  }

  /**
   * Handle ResourceNotFoundException.
   *
   * @param e ResourceNotFoundException.
   * @return GlobalResponse.
   */
  @ExceptionHandler(value = ResourceNotFoundException.class)
  @ResponseBody
  public ResponseEntity<GlobalResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
    log.error("ResourceNotFoundException", e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(GlobalResponse.newBuilder()
            .setOk(false)
            .setCode(404)
            .setStatus(HttpStatus.NOT_FOUND.name())
            .setMessage(e.getMessage())
            .build());
  }

  /**
   * Handle RuntimeException.
   *
   * @param e RuntimeException.
   * @return GlobalResponse.
   */
  @ExceptionHandler(value = RuntimeException.class)
  @ResponseBody
  public ResponseEntity<GlobalResponse> runtimeExceptionHandler(RuntimeException e) {
    log.error("RuntimeException", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(GlobalResponse.newBuilder()
            .setOk(false)
            .setCode(500)
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .setMessage(e.getMessage())
            .build());
  }

  /**
   * Handle HttpMediaTypeNotSupportedException.
   *
   * @param e HttpMediaTypeNotSupportedException.
   * @return GlobalResponse.
   */
  @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
  @ResponseBody
  public ResponseEntity<GlobalResponse> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    log.error("HttpMediaTypeNotSupportedException", e);
    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(GlobalResponse.newBuilder()
            .setOk(false)
            .setCode(415)
            .setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name())
            .setMessage(e.getMessage())
            .build());
  }

  /**
   * Handle Exception.
   *
   * @param e Exception.
   * @return GlobalResponse.
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseEntity<GlobalResponse> exceptionHandler(Exception e) {
    log.error("Exception", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(GlobalResponse.newBuilder()
            .setOk(false)
            .setCode(500)
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .setMessage(e.getMessage())
            .build());
  }
}
