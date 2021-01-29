package com.steve.BookApi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.model.Book;
import com.steve.BookApi.model.category.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        // TODO CHANGE ME TO GET BOOKS
        ArrayList<Category> categories = new ArrayList<>();

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", 2939)
                .addValue("id2", 2800);

        List<Map<String, Object>> mysqlCategory = template.queryForList(getAllBooksQuery, namedParameters);
        mysqlCategory.forEach(category -> categories.add(mapper.convertValue(category, Category.class)));

        LOG.info(String.format("Obtained all books: count[%d]", categories.size()));
        // TODO CHANGE ME TO GET BOOKS

        return books;
    }
}
