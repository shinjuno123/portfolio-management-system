package com.amazing.juno.springwebapp.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.token")
@Getter
@Setter
public class JWTConstraints {
    private String header;
    private String key;
    private String issuer;
    private String subject;
}
