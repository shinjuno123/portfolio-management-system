package com.amazing.juno.pmsrest.exc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileNotFoundException extends RuntimeException{
    private String message;
}
