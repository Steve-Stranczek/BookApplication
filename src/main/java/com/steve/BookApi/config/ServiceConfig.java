package com.steve.BookApi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.repository.BookRepository;
import com.steve.BookApi.repository.IBookRepository;
import com.steve.BookApi.repository.BookRepository;
import com.steve.BookApi.service.BookService;
import com.steve.BookApi.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Single-file configuration for all of your services.
 * The brain of your application, determining what should be used where
 * <p>
 * Alternate way could be @Autowired-ing services in classes you need them, instead of in one place
 */
@Configuration
public class ServiceConfig {

    NamedParameterJdbcTemplate mySqlBookTemplate;
    ObjectMapper mapper;

    @Autowired
    ServiceConfig(NamedParameterJdbcTemplate _mySqlBookTemplate, ObjectMapper _mapper) {
        mySqlBookTemplate = _mySqlBookTemplate;
        mapper = _mapper;
    }

    @Bean
    IBookRepository bookRepository() {
        return new BookRepository(
                mapper,
                mySqlBookTemplate
        );
    }

    @Bean
    IBookService bookService(IBookRepository bookRepository) {
        return new BookService(bookRepository);
    }

}