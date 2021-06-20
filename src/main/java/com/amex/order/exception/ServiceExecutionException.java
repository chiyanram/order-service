package com.amex.order.exception;

import java.io.Serial;

public class ServiceExecutionException extends RuntimeException {
  @Serial private static final long serialVersionUID = -7634934507865895166L;

  public ServiceExecutionException(final String message) {
    super(message);
  }

  public ServiceExecutionException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
