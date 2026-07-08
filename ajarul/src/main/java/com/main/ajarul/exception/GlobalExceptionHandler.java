package com.main.ajarul.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(DeviceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DeviceAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                    new ErrorResponse(
                        409,
                        ex.getMessage(),
                        LocalDateTime.now()
                    )
                );
    }
@ExceptionHandler(UnauthorizedDeviceException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedDeviceException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                    new ErrorResponse(
                        403,
                        ex.getMessage(),
                        LocalDateTime.now()
                    )
                );
            }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(new ErrorResponse(404,ex.getMessage(), LocalDateTime.now()));
    }  

      @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(
            RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        400,
                        ex.getMessage(),
                        LocalDateTime.now()));
    }
     
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    new ErrorResponse(
                        500,
                        ex.getMessage(),
                        LocalDateTime.now()
                    )
                );
    }      

     
}
