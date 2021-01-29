package com.steve.BookApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 * Your sql connection
 */
@Configuration
public class BookTemplateConfig {

    private final MySqlBookProperties props;

    @Autowired
    BookTemplateConfig(MySqlBookProperties _props) {
        props = _props;
    }

    @Bean
    DataSource mySqlBookDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(System.getenv("mySqlBookDB"));
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(System.getenv("mySqlRootPassword"));
        dataSource.setMaxActive(props.getMaxActive());
        dataSource.setMinIdle(props.getMinIdle());
        dataSource.setMaxIdle(props.getMaxIdle());
        dataSource.setInitialSize(props.getInitialSize());
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setMaxWait(props.getMaxWaitMillis());
        dataSource.setValidationQuery(props.getValidationQuery());
        return dataSource;
    }

    @Bean
    NamedParameterJdbcTemplate mySqlBookTemplate(DataSource mySqlBookDataSource) {
        return new NamedParameterJdbcTemplate(mySqlBookDataSource);
    }
}

