package ru.itk.app.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class WebExceptionHandler {
  private static final String DEFAULT_MESSAGE = "Внутренняя ошибка сервиса";

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleInvalidJson(HttpMessageNotReadableException exception, ServletWebRequest webRequest) {
    ResponseEntity<Object> response = handleException("Невалидный JSON", HttpStatus.BAD_REQUEST, webRequest);
    log.error(exception.getMostSpecificCause().getMessage());
    return response;
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception, ServletWebRequest webRequest) {
    ResponseEntity<Object> response = handleException("Ошибка сохранения данных", HttpStatus.BAD_REQUEST, webRequest);
    log.error(exception.getMostSpecificCause().getMessage());
    return response;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, ServletWebRequest webRequest) {
    return handleException(exception, webRequest);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleRuntimeException(RuntimeException exception, ServletWebRequest webRequest) {
    log.error(exception.getMessage(), exception);
    return handleException(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, ServletWebRequest webRequest) {
    Map<String, List<String>> errors = exception.getBindingResult().getAllErrors().stream()
      .collect(Collectors.groupingBy(
        error -> (error instanceof FieldError fieldError) ? fieldError.getField() : error.getObjectName(),
        LinkedHashMap::new,
        Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList())
      ));

    return handleException(errors.toString(), HttpStatus.BAD_REQUEST, webRequest);
  }

  public ResponseEntity<Object> handleException(Exception exception, ServletWebRequest webRequest) {
    return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
  }

  public ResponseEntity<Object> handleException(Exception exception, HttpStatus status, WebRequest request) {
    return handleException(exception.getMessage(), status, request);
  }

  public ResponseEntity<Object> handleException(String message, HttpStatus status, WebRequest request) {
    log.error(message);
    ErrorDto errorDto = buildErrorDto(message, status, request);
    return ResponseEntity.status(status).body(errorDto);
  }

  private ErrorDto buildErrorDto(String message, HttpStatus status, WebRequest request) {
    return ErrorDto.builder()
      .message(message)
      .status(status.value())
      .details(ErrorDto.Details.builder()
        .url(request.getDescription(false))
        .build())
      .build();
  }

}