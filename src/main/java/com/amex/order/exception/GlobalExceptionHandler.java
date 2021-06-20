package com.amex.order.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorInfo handleNotFound(
      final NotFoundException notFoundException, final HttpServletRequest serverHttpRequest) {

    return createHttpErrorInfo(HttpStatus.NOT_FOUND, serverHttpRequest, notFoundException);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorInfo handleBadRequest(
      final BadRequestException badRequestException, final HttpServletRequest serverHttpRequest) {

    return createHttpErrorInfo(HttpStatus.BAD_REQUEST, serverHttpRequest, badRequestException);
  }

  private ErrorInfo createHttpErrorInfo(
      final HttpStatus status, final HttpServletRequest request, final Exception ex) {

    final String path = UrlPathHelper.defaultInstance.getPathWithinApplication(request);
    final String message = ex.getMessage();
    log.debug("Returning HTTP status: {} for path: {}, message: {}", status, path, message);
    return new ErrorInfo(OffsetDateTime.now(), path, status.value(), message);
  }

  public record ErrorInfo(OffsetDateTime timestamp, String path, int status, String message) {}
}
