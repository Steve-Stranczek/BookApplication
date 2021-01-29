package com.steve.BookApi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties you set in the application.yml file under database.book
 */
@Configuration
@ConfigurationProperties("database.book")
@Data
public class MySqlBookProperties {
    private String url;
    private String driver;
    private String username;
    private String password;
    private Integer minIdle;
    private Integer maxIdle;
    private Integer maxActive;
    private Integer initialSize;
    private Integer maxWaitMillis;
    private String validationQuery;
}
