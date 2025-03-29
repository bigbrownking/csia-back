package org.agro.agrohack.exception.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.agro.agrohack.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
public class PromoteExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException notFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notFoundException.getMessage());
    }
}
