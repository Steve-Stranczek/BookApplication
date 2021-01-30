package com.steve.BookApi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.BookApi.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.steve.BookApi.repository.SqlConstants.*;

public class BookRepository implements IBookRepository {
    Logger LOG = LoggerFactory.getLogger(BookRepository.class);

    NamedParameterJdbcTemplate template;
    ObjectMapper mapper;

    public BookRepository(ObjectMapper _mapper, NamedParameterJdbcTemplate _template) {
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

    @Override
    @Transactional
    public long updateBook(Book bookToUpdate) {

        Book bookFromDb = lookupBook(bookToUpdate.id);

        if(bookFromDb == null)
        {
            return 0;
        }

        bookToUpdate = trimDuplicatesFromBook(bookToUpdate, bookFromDb);

        if (bookToUpdate.author.name != null)
        {
            bookToUpdate.author.id = lookupAuthor(bookToUpdate.author.name);
            if(bookToUpdate.author.id == 0)
            {
               bookToUpdate.author.id = insertAuthor(bookToUpdate.author.name);
            }
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        template.update(buildUpdateQuery(bookToUpdate), sqlParameterSource);

        return bookToUpdate.id;
    }

    private Book lookupBook(int id)
    {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            var map = template.queryForMap(getBookById, namedParameters);

            Book book = mapper.convertValue(map, Book.class);

            return book;
        }
        catch(Exception e)
        {
            return null;
        }

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

    private String buildUpdateQuery(Book bookToUpdate)
    {
        String query = "UPDATE book SET ";
        if(bookToUpdate.title != null)
        {
            query += " bookTitle = '" + bookToUpdate.title + "', ";
        }

        if(bookToUpdate.pages != 0)
        {
            query += " numPages = " + bookToUpdate.pages + ", ";
        }

        if(bookToUpdate.author.id != 0)
        {
            query += " authorId = " + bookToUpdate.author.id + ", ";
        }

        if(bookToUpdate.genre.id != 0)
        {
            query += " genreId = " + bookToUpdate.genre.id;
        }

        if (query.endsWith(", "))
        {
            query = query.substring(0, query.length() - 2);
        }

        query += " WHERE bookId = " + bookToUpdate.id;

        return query;
    }

    private Book trimDuplicatesFromBook(Book bookToUpdate, Book bookFromDb)
    {
        if(bookToUpdate.title == bookFromDb.title)
        {
            bookToUpdate.title = null;
        }
        if(bookToUpdate.pages == bookFromDb.pages)
        {
            bookToUpdate.pages = 0;
        }
        if(bookToUpdate.genre.id == bookFromDb.genre.id)
        {
            bookToUpdate.genre.id = 0;
        }
        if(bookToUpdate.author.name == bookFromDb.author.name)
        {
            bookToUpdate.author.name = null;
            bookToUpdate.author.id = 0;
        }
        return bookToUpdate;
    }
}
