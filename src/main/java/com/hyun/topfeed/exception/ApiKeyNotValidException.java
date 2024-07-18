package com.hyun.topfeed.exception;


public class ApiKeyNotValidException extends RuntimeException {

  public ApiKeyNotValidException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.UNAUTHORIZED_EXCEPTION;
  }
}

