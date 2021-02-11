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
    public Book getBook(String title, String author) {
        int authorId = lookupAuthor(author);

        if(authorId == 0)
        {
            return null;
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bookTitle", title)
                .addValue("authorId", authorId);

        int bookId;

        try {
            bookId = template.queryForObject(getBookIdByTitleandAuthor, namedParameters, Integer.class);
        }
        catch (Exception e) {
            return null;
        }

        return lookupBook(bookId);

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
    public long insertBook(Book book) {

        book.author.id = lookupAuthor(book.author.name);

        if(lookupBookByTitleandAuthorId(book.title, book.author.id) != 0)
        {
            return 0;
        }

        if(book.author.id == 0)
        {
            book.author.id = insertAuthor(book.author.name);
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("authorId", book.author.id)
                .addValue("genreId", book.genre.id)
                .addValue("bookTitle", book.title)
                .addValue("pages", book.pages);

        template.update(insertBook,namedParameters);


        return lookupBookByTitleandAuthorId(book.title, book.author.id);
    }

    @Override
    @Transactional
    public long updateBook(Book book) {

        Book bookFromDb = lookupBook(book.id);

        if(bookFromDb == null)
        {
            return 0;
        }

        book = trimDuplicatesFromBook(book, bookFromDb);

        if (book.author.name != null)
        {
            book.author.id = lookupAuthor(book.author.name);
            if(book.author.id == 0)
            {
                book.author.id = insertAuthor(book.author.name);
            }
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        template.update(buildUpdateQuery(book), sqlParameterSource);

        return book.id;
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
