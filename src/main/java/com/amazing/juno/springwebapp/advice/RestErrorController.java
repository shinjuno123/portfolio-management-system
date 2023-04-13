package com.amazing.juno.springwebapp.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestErrorController {


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

    @ExceptionHandler(MissingServletRequestPartException.class)
    public @ResponseBody Map<String,String> handleMissingServletRequestPartException(Exception  exception, HttpServletResponse response) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message","missing parameter");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return errorMap;
    }


}
