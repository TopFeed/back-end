package com.hyun.topfeed.exception;

public class AlreadyExistException extends RuntimeException {

  public AlreadyExistException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.ALREADY_EXIST_EXCEPTION;
  }
}
