package com.amazing.juno.springwebapp.exc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileStorageException extends RuntimeException{
    private String message;
}
