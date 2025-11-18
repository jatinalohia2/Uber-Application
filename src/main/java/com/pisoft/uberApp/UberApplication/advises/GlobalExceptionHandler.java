package com.pisoft.uberApp.UberApplication.advises;

import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFound e) {

        ApiError apiError = ApiError.builder()

                .localDateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("not found")
                .errors(List.of(e.getMessage()))
                .build();

        return BuildResponseHanlder(apiError);
    }

    private ResponseEntity<ApiResponse<?>> BuildResponseHanlder(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError) , apiError.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(MethodArgumentNotValidException e) {


        List<String> error  = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(o -> o.getDefaultMessage())
                .toList();

        ApiError apiError = ApiError.builder()

                .localDateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Invalid Input Exception")
                .errors(error)
                .build();

        return BuildResponseHanlder(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntime(RuntimeException e) {

        ApiError apiError = ApiError.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .errors(List.of("Something went wrong"))
                .build();

        return BuildResponseHanlder(apiError);
    }

}
