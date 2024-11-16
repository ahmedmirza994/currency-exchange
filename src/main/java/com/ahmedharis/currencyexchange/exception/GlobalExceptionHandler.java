package com.ahmedharis.currencyexchange.exception;

import com.ahmedharis.currencyexchange.dto.ApiResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
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
}
