package com.undefined14.pre.advice;

import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
      log.error("# handle Exception", e);

      final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

      return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }
}
