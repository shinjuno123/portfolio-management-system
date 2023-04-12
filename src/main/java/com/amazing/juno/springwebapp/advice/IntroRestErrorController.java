package com.amazing.juno.springwebapp.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class IntroRestErrorController {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleBindErrors(MethodArgumentNotValidException exception){

        List<?> errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        System.out.println("Error List:");
        System.out.println(errorList);

        return ResponseEntity.badRequest().body(errorList);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<?> handleTypeMisMatchErrors(MethodArgumentTypeMismatchException exception){

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put(exception.getName(), exception.getLocalizedMessage());

        return ResponseEntity.badRequest().body(responseBody);
    }

}
