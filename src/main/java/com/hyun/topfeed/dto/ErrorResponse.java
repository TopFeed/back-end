package com.hyun.topfeed.dto;

import com.hyun.topfeed.exception.ErrorStatus;
import lombok.Getter;

@Getter
public class ErrorResponse {

  private final ErrorStatus status;

  private final String msg;

  public ErrorResponse(ErrorStatus status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  public static ErrorResponse create(ErrorStatus code, String msg) {
    return new ErrorResponse(code, msg);
  }
}

