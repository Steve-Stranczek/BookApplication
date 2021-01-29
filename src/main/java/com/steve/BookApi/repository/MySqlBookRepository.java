package com.steve.BookApi.repository;

import ch.qos.logback.core.db.dialect.HSQLDBDialect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.metadata.HsqlTableMetaDataProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.steve.BookApi.repository.SqlConstants.*;

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
    public long deleteBook(long id) {
        boolean isDeleted = false;
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);

        var numRowsAffected = template.update(deleteBookById,namedParameters);

        return numRowsAffected;
    }

    @Override
    @Transactional
    public long insertBook(Book bookToInsert) {

        bookToInsert.author.id = lookupAuthor(bookToInsert.author.name);

        if(bookToInsert.author.id == 0)
        {
            bookToInsert.author.id = insertAuthor(bookToInsert.author.name);
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("authorId", bookToInsert.author.id)
                .addValue("genreId", bookToInsert.genre.id)
                .addValue("bookTitle", bookToInsert.title)
                .addValue("pages", bookToInsert.pages);

        template.update(insertBook,namedParameters);


        return lookupBookByTitleandAuthorId(bookToInsert.title, bookToInsert.author.id);
    }

    private int lookupAuthor(String name)
    {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("authorName", name);
        int authorId;

        try {
             authorId = template.queryForObject(getAuthorIdByName, namedParameters, Integer.class);
        }
        catch(Exception e)
        {
            return 0;
        }

        return authorId;
    }

    private int insertAuthor(String name)
    {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("authorName", name);
          template.update(insertAuthor, namedParameters);
          return lookupAuthor(name);
    }

    private int lookupBookByTitleandAuthorId(String title, int authorId)
    {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bookTitle", title)
                .addValue("authorId", authorId);
        try {
            return template.queryForObject(getBookIdByTitleandAuthor,namedParameters, Integer.class);
        }
        catch(Exception e)
        {
            return 0;
        }
    }
}
