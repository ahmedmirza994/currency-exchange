package com.ahmedharis.currencyexchange.exception;

import com.ahmedharis.currencyexchange.dto.ApiResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<List<String>>> handleValidationException(
      MethodArgumentNotValidException ex) {
    List<String> errors =
        ex.getBindingResult().getAllErrors().stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());

    ApiResponse<List<String>> response = new ApiResponse<>("Validation failed", false, errors);
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(ExchangeRateException.class)
  public ResponseEntity<ApiResponse<String>> handleExchangeRateException(ExchangeRateException ex) {
    ApiResponse<String> response = new ApiResponse<>(ex.getMessage(), false, null);
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
    ApiResponse<String> response = new ApiResponse<>(ex.getMessage(), false, null);
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(
      UsernameNotFoundException ex) {
    ApiResponse<String> response = new ApiResponse<>(ex.getMessage(), false, null);
    return ResponseEntity.ok(response);
  }
}
