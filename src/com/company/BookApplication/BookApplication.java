package com.company.BookApplication;
import com.company.BookApplication.model.Book;
import com.company.BookApplication.model.Genre;
import com.company.BookApplication.repository.BookRepository;
import com.company.BookApplication.repository.MySqlBookRepository;

import java.sql.SQLException;
import java.util.List;

public class BookApplication {

    public static void main(String[] args) throws SQLException {
        BookRepository myBookRepository = new MySqlBookRepository();

        List<Book> books = myBookRepository.getAllBooks();
        List<Genre> genres = myBookRepository.getAllGenres();
    }
}
