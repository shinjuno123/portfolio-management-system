package com.amazing.juno.pmsrest.advice;


import com.amazing.juno.pmsrest.entity.ResponseError;
import com.amazing.juno.pmsrest.exc.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class FileExceptionAdvice {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException exc){

        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("message", exc.getMessage());

        List<Map<String,String>> details = new ArrayList<>();
        details.add(errorMessage);
        ResponseError error = new ResponseError(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc){
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("message", exc.getMessage());

        List<Map<String,String>> details = new ArrayList<>();
        details.add(errorMessage);
        ResponseError error = new ResponseError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),details);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(error);
    }
}
