package com.amazing.juno.pmsrest.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.mail")
@Getter
@Setter
public class ClientMailProp {
    private String subject;
    private String body;
}
