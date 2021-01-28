package com.company.BookApplication.repository;

import com.company.BookApplication.model.Book;
import com.company.BookApplication.model.Genre;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();
    List<Genre> getAllGenres();
    void deleteBook();
    void updateBook();
    void insertBook();
}
