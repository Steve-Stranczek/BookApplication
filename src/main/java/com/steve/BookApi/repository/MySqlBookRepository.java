package com.steve.BookApi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.steve.BookApi.repository.SqlConstants.getAllBooksQuery;

public class MySqlBookRepository implements BookRepository {
    Logger LOG = LoggerFactory.getLogger(MySqlBookRepository.class);

    NamedParameterJdbcTemplate template;
    ObjectMapper mapper;

    public MySqlBookRepository(ObjectMapper _mapper, NamedParameterJdbcTemplate _template) {
        mapper = _mapper;
        template = _template;
    }

    @Override
    public List<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        SqlParameterSource namedParameters = new MapSqlParameterSource();

        List<Map<String, Object>> mysqlCategory = template.queryForList(getAllBooksQuery, namedParameters);

        mysqlCategory.forEach(book -> books.add(mapper.convertValue(book, Book.class)));
        LOG.info(String.format("Obtained all books: count[%d]", books.size()));

        return books;
    }

    @Override
    public void deleteBook() {

    }
}
