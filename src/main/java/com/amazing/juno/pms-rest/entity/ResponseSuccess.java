package com.amazing.juno.springwebapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSuccess {
    private LocalDateTime timeStamp;

    private Integer status;

    private String message;


}
