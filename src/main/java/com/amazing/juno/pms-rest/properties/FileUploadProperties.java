package com.amazing.juno.springwebapp.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.upload")
@Getter
@Setter
public class FileUploadProperties {
    private String location;
}
