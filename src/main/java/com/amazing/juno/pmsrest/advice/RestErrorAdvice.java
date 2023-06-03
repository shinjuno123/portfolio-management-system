package com.amazing.juno.pmsrest.advice;
import com.amazing.juno.pmsrest.entity.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestErrorAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleBindErrors(MethodArgumentNotValidException exception){

        List<Map<String,String>> errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();



        return ResponseEntity.badRequest().body(ResponseError.builder()
                .messages(errorList)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<?> handleTypeMisMatchErrors(MethodArgumentTypeMismatchException exception){

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(exception.getName(), exception.getMessage());

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(errorMap);

        return ResponseEntity.badRequest().body(ResponseError.builder()
                .messages(messages)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    ResponseEntity<?> handleMissingServletRequestPartException(MissingServletRequestPartException  exception) {

        Map<String,String> errorMap = new HashMap<>();
        errorMap.put(exception.getRequestPartName(),exception.getLocalizedMessage());

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(errorMap);

        return ResponseEntity.badRequest().body(ResponseError.builder()
                .messages(messages)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> handleDataViolationException(DataIntegrityViolationException exception){

        Map<String,String> errorMap = new HashMap<>();
        String cause = exception.getCause().getCause().getMessage();

        if(cause == null || cause.isBlank() || cause.isEmpty()){
            return ResponseEntity.badRequest().body("");
        }


        errorMap.put("message",exception.getCause().getCause().getMessage().split(":")[0]);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(errorMap);

        return ResponseEntity.badRequest().body(ResponseError.builder()
                .messages(messages)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> handleJsonParsingError(HttpMessageNotReadableException exception){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message","Data in your body is not readable!");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(errorMap);

        return ResponseEntity.badRequest().body(ResponseError.builder()
                .messages(messages)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(MalformedURLException.class)
    ResponseEntity<?> handleMalformedURLException(MalformedURLException exception){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("message","You have wrong URL form");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(errorMap);

        return ResponseEntity.badRequest().body(ResponseError.builder()
                .messages(messages)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }




}
