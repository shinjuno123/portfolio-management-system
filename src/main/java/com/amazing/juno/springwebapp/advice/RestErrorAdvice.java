package com.amazing.juno.springwebapp.advice;

import com.amazing.juno.springwebapp.exc.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestErrorAdvice {


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
        responseBody.put(exception.getName(), exception.getMessage());

        return ResponseEntity.badRequest().body(responseBody);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    ResponseEntity<Map<String,String>> handleMissingServletRequestPartException(MissingServletRequestPartException  exception) {

        Map<String,String> errorMap = new HashMap<>();

        errorMap.put(exception.getRequestPartName(),exception.getLocalizedMessage());

        return ResponseEntity.badRequest().body(errorMap);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> handleDataViolationException(DataIntegrityViolationException exception){

        Map<String,String> errorMap = new HashMap<>();
        String cause = exception.getCause().getCause().getMessage();

        if(cause == null || cause.isBlank() || cause.isEmpty()){
            return ResponseEntity.badRequest().body("");
        }

        errorMap.put("message",exception.getCause().getCause().getMessage().split(":")[0]);

        return ResponseEntity.badRequest().body(errorMap);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> handleJsonParsingError(HttpMessageNotReadableException exception){
        Map<String,String> errorMap = new HashMap<>();

        errorMap.put("message","Data in your body is not readable!");

        return ResponseEntity.badRequest().body(errorMap);
    }





}
