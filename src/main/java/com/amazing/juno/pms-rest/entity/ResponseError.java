package com.amazing.juno.springwebapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class ResponseError {

    private LocalDateTime timeStamp;

    private Integer status;

    private List<Map<String,String>> messages;

}
