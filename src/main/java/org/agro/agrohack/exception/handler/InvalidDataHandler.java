package org.agro.agrohack.exception.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.agro.agrohack.exception.InvalidEmailException;
import org.agro.agrohack.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
public class InvalidDataHandler {
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException invalidEmailException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidEmailException.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidPasswordException.getMessage());
    }
}
