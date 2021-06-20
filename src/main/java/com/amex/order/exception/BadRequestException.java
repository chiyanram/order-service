package com.amex.order.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

  @Serial private static final long serialVersionUID = 7672825075721735464L;

  public BadRequestException(final String message) {
    super(message);
  }

  public BadRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
