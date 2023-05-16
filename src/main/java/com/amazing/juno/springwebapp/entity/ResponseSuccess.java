package com.amazing.juno.springwebapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ResponseSuccess {
    private LocalDateTime timeStamp;

    private Integer status;

    private String message;


}
